package com.example.courseapp.presentation.teacher

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.courseapp.R
import com.example.courseapp.Screens
import com.example.courseapp.common.Constants
import com.example.courseapp.common.UserVerificationModel
import com.example.courseapp.data.remote.teacher_post_dto.AddCourseFields
import com.example.courseapp.domain.model.TeacherModel
import com.example.courseapp.navigation.SharedViewModel
import com.example.courseapp.presentation.teacher.common_item.deleteCourse
import com.example.courseapp.presentation.ui.theme.CourseAppTheme
import kotlinx.coroutines.delay

@Composable
fun TeacherHome(
    navController: NavHostController,
    userVerificationModel: UserVerificationModel = hiltViewModel(),
    teacherModel: TeacherModel = hiltViewModel(),
    sharedViewModel: SharedViewModel
) {

    val verificationId by userVerificationModel.verificationIdFlow.collectAsStateWithLifecycle(
        initialValue = null
    )
    // Use LaunchedEffect to navigate only once when the verificationId changes
    LaunchedEffect(true) {
        delay(5000L)
        if (verificationId.isNullOrEmpty() || verificationId == "null") {
            navController.navigate(Constants.VERIFICATION_ROUTE)
        } else teacherModel.fetchProfileInfo()

    }

    var isLoading by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(key1 = teacherModel) {
        teacherModel.fetchProfileInfo()
        delay(1000)
        teacherModel.fetchAllCourses()
        isLoading = false
    }


    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val profileInfo by teacherModel.profileInfo.collectAsStateWithLifecycle()

    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    val courses by teacherModel.courses.collectAsStateWithLifecycle(emptyList())
    if (!courses.isNullOrEmpty()) isLoading = false

    var selectedCourseId by remember { mutableStateOf(-1) }


    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        ProfileHeader(
            context = context,
            name = profileInfo?.teacherName ?: "User",
            profilePic = profileInfo?.teacherProfile,
            onCreateCourseNavigate = {
                sharedViewModel.updateCourseFields(null)
                navController.navigate(Screens.AddCourseScreen.route) }
        ) {
            navController.navigate(Screens.TeacherProfileScreen.route)
        }
    }) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
                    .padding(it),
            ) {
                item {

                    Text(
                        text = "My Courses",
                        style = MaterialTheme.typography.h5.copy(
                            color = MaterialTheme.colors.primaryVariant,
                            fontWeight = FontWeight.SemiBold,
                            fontStyle = FontStyle.Italic
                        ),
                        modifier = Modifier
                            .padding(vertical = 20.dp, horizontal = 10.dp)
                    )
                }

                items(courses) { course ->
                    CourseItem(
                        context = context,
                        text = course.courseName,
                        image = course.coursePhoto,
                        onUpdateCourse = {
                            sharedViewModel.updateCourseFields(
                                AddCourseFields(
                                    courseId = course.courseId,
                                    category_id = course.courseId,
                                    description = course.courseDescription,
                                    photo = course.coursePhoto.toUri(),
                                    price = "45",
                                    title = course.courseName
                                )
                            )
                            navController.navigate(Screens.AddCourseScreen.route)
                        },
                        onLongPress = {
                            selectedCourseId = course.courseId
                            showDeleteDialog = true
                        }
                    ) {
                        Log.d("AddCourse", "TeacherHome: ${course.courseId}")
                        navController.navigate(Screens.SectionsScreen.passCourseId(course.courseId))
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    DeleteDialog(
                        courseId = selectedCourseId,
                        showDialog = showDeleteDialog,
                        onShowDialog = { showDeleteDialog = it }) {
                        isLoading = true
                        deleteCourse(
                            lifecycleOwner = lifecycleOwner,
                            teacherModel = teacherModel,
                            courseId = it,
                            onFailed = { isLoading = false }
                        ) {
                            isLoading = false
                            teacherModel.fetchAllCourses()
                        }
                    }
                }
            }

            if (isLoading) {
                CircularProgressIndicator(color = MaterialTheme.colors.primaryVariant)
            }
        }
    }

}

@Composable
fun ProfileHeader(
    name:String,
    profilePic:String?,
    context: Context,
    onCreateCourseNavigate: () -> Unit,
    onProfileNavigate: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.primaryVariant)
            .height(70.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(context = context)
                .data(
                    profilePic?.toUri() ?:  R.drawable.ic_profile
                )
                .crossfade(true)
                .transformations(CircleCropTransformation())
                .build(),
            placeholder = painterResource(id = R.drawable.ic_profile),
            contentScale = ContentScale.Crop
        )

        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(horizontal = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painter,
                contentDescription = "profile_header",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(55.dp)
//                    .clip(shape = CircleShape)
//                    .wrapContentHeight()
//                    .wrapContentWidth()
                    .padding(horizontal = 5.dp)
                    .clickable {
                        onProfileNavigate()
                    }
            )


            Text(
                text = name,
                style = MaterialTheme.typography.body1.copy(
                    color = MaterialTheme.colors.onPrimary,
                    fontWeight = FontWeight.SemiBold
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
            )
        }

        Text(
            text = "Create Course",
            style = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.onPrimary,
                fontWeight = FontWeight.ExtraBold,
            ),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
        IconButton(onClick = {
            onCreateCourseNavigate()
        }) {
            Icon(
                imageVector = Icons.Default.AddBox,
                contentDescription = "Create course",
                tint = MaterialTheme.colors.onPrimary
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CourseItem(
    context: Context,
    text: String = "",
    image: String? = null,
    onUpdateCourse: () -> Unit,
    onLongPress: () -> Unit,
    onNavigate: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
            .padding(vertical = 5.dp, horizontal = 5.dp)
            .combinedClickable(onLongClick = onLongPress, onClick = onNavigate)
    ) {

        Divider(
            modifier = Modifier.align(Alignment.TopCenter),
            color = MaterialTheme.colors.primaryVariant
        )
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(context = context)
                .data(
                    image ?: R.drawable.ic_course
                )
                .crossfade(1000)
                // .size(coil.size.Size.ORIGINAL)
                .build(), placeholder = painterResource(id = R.drawable.ic_course)
        )


        Image(
            painter = painter,
            contentDescription = "course",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 5.dp)
                .align(Alignment.TopCenter)
                .padding(10.dp)
        )
        IconButton(onClick = {
            onUpdateCourse()
        }, modifier = Modifier.align(Alignment.TopEnd)) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = "edit course")
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .align(Alignment.BottomCenter)
                .background(Color.Black.copy(ContentAlpha.medium))
                .padding(horizontal = 20.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "0 students")

            Text(
                text = text,
                style = MaterialTheme.typography.body2.copy(
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            )

            Text(text = "0 Likes")
        }


    }
}

@Composable
fun DeleteDialog(
    courseId: Int,
    showDialog: Boolean,
    onShowDialog: (Boolean) -> Unit,
    onDeleteCourse: (Int) -> Unit
) {
    if (showDialog) {
        AlertDialog(modifier = Modifier
            .fillMaxWidth(),
            onDismissRequest = {
                onShowDialog(false)
            },
            title = {
                Text(
                    text = "Delete Course?",
                    style = MaterialTheme.typography.h6.copy(
                        MaterialTheme.colors.primaryVariant,
                        fontWeight = FontWeight.Medium
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            buttons = {

                Spacer(modifier = Modifier.height(30.dp))

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {

                    Button(
                        modifier = Modifier,
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = MaterialTheme.colors.primaryVariant,
                            backgroundColor = MaterialTheme.colors.primary
                        ),
                        onClick = {
                            onShowDialog(false)
                        },
                    ) {
                        Text(text = "Cancel", style = MaterialTheme.typography.body2)
                    }

                    Button(
                        modifier = Modifier,
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = MaterialTheme.colors.onSurface,
                            backgroundColor = Color.Red
                        ),
                        onClick = {
                            onDeleteCourse(courseId)
                            onShowDialog(false)
                        },
                    ) {
                        Text(text = "Delete", style = MaterialTheme.typography.body2)
                    }
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TeacherHomePreview() {
    CourseAppTheme {
        //TeacherHome()
    }
}



