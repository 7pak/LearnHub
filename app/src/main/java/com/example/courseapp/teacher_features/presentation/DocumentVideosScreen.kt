package com.example.courseapp.teacher_features.presentation

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DoneOutline
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.material.icons.filled.OndemandVideo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.courseapp.navigation.Screens
import com.example.courseapp.navigation.SharedViewModel
import com.example.courseapp.teacher_features.data.remote.teacher_get_dto.FileData
import com.example.courseapp.teacher_features.data.remote.teacher_get_dto.VideoData
import com.example.courseapp.teacher_features.data.remote.teacher_post_dto.AddFileFields
import com.example.courseapp.teacher_features.domain.model.TeacherModel
import com.example.courseapp.teacher_features.presentation.common_item.addFile
import com.example.courseapp.teacher_features.presentation.common_item.deleteFile
import com.example.courseapp.teacher_features.presentation.common_item.deleteVideo
import com.example.courseapp.teacher_features.presentation.common_item.swipeActionItem
import com.example.courseapp.teacher_features.presentation.common_item.updateFile
import com.example.courseapp.teacher_features.presentation.states.add_section.VideoFields
import kotlinx.coroutines.delay
import me.saket.swipe.SwipeableActionsBox

@Composable
fun DocumentAndVideosScreen(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
    teacherModel: TeacherModel = hiltViewModel()
) {
    var isLoading by remember {
        mutableStateOf(true)
    }
    LaunchedEffect(key1 = teacherModel) {
        delay(1000)
        teacherModel.fetchAllSections(teacherModel.currentCourseId)
        isLoading = false
    }

    val lifecycleOwner = LocalLifecycleOwner.current


    var showDialog by remember {
        mutableStateOf(false)
    }

    val videos by teacherModel.videos.collectAsStateWithLifecycle(initialValue = emptyList())
    val document by teacherModel.file.collectAsStateWithLifecycle(initialValue = null)

    if (!videos.isNullOrEmpty() || document != null) isLoading = false
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background), contentAlignment = Alignment.Center
    ) {

        LazyColumn(Modifier.fillMaxSize()) {
            item {
                document?.let { file ->
                    FileItem(
                        onUpdateFile = { showDialog = true },
                        onDeleteFile = {
                            isLoading = true
                            Log.d("AddCour", "DocumentAndVideosScreen: ${file.fileId}")
                            deleteFile(
                                lifecycleOwner = lifecycleOwner,
                                teacherModel = teacherModel,
                                fileId = file.fileId, onFailed = {
                                    isLoading = false
                                }
                            ) {

                                isLoading = false
                                teacherModel.fetchAllSections(teacherModel.currentSectionId)
                            }

                        }
                    ) {

                        // TODO: clicked on file

                    }
                }


                if (showDialog) {
                    AddFileDialog(fileData = document, onShowDialog = { showDialog = it }) {
                        isLoading = true
                        updateFile(
                            lifecycleOwner = lifecycleOwner, teacherModel = teacherModel,
                            AddFileFields(
                                fileId = document?.fileId,
                                fileUri = it.fileUrl.toUri(),
                                sectionId = teacherModel.currentSectionId
                            ),onFailed = {isLoading = false}
                        ) {
                            isLoading = false
                            teacherModel.fetchAllSections(teacherModel.currentSectionId)                        }
                    }
                }
            }

            item {
                if (document==null) {
                    AddFileItem {
                        showDialog = true
                    }

                    if (showDialog) {
                        AddFileDialog(onShowDialog = { showDialog = it }, onAddFile = {
                            isLoading = true
                            addFile(
                                lifecycleOwner = lifecycleOwner, teacherModel = teacherModel,
                                AddFileFields(
                                    fileUri = it,
                                    sectionId = teacherModel.currentSectionId
                                ),onFailed = {isLoading = false}
                            ) {
                                isLoading = false
                                teacherModel.fetchAllSections(teacherModel.currentSectionId)                            }
                        }
                        )
                    }
                }
            }

            items(videos) { video ->
                VideoItem(
                    videoData = video,
                    onDeleteVideo = {
                        deleteVideo(
                            lifecycleOwner = lifecycleOwner,
                            teacherModel = teacherModel,
                            video.videoId,onFailed = {isLoading = false}
                        ) {
                            isLoading = false
                            teacherModel.fetchAllSections(teacherModel.currentSectionId)                        }
                    },
                ) {
                    sharedViewModel.updateVideoFields(
                        VideoFields(
                            videoId = video.videoId,
                            sectionId = teacherModel.currentSectionId,
                            videoName = video.videoTitle,
                            video = video.videoUrl.toUri(),
                            isVisible = video.videoVisible == 0,
                            isAddingNew = true
                        )
                    )
                    navController.navigate(Screens.AddVideoScreen.passSectionId(teacherModel.currentSectionId))
                }
            }
            item {
                AddVideoItem {
                    sharedViewModel.updateVideoFields(null)
                    navController.navigate(Screens.AddVideoScreen.passSectionId(teacherModel.currentSectionId))
                }

                Text(
                    text = "Swipe right / left to edit / delete ",
                    style = MaterialTheme.typography.body2.copy(MaterialTheme.colors.onSurface),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp)
                )
            }
        }
        if (isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colors.primaryVariant)
        }
    }
}

@Composable
private fun VideoItem(
    videoData: VideoData,
    onDeleteVideo: () -> Unit,
    onVideoClicked: () -> Unit
) {

    SwipeableActionsBox(
        endActions = swipeActionItem(
            icon = Icons.Default.Delete,
            color = Color.Red
        ) {
            onDeleteVideo()
        },
        swipeThreshold = 200.dp,
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colors.primaryVariant
                )
                .padding(horizontal = 20.dp, vertical = 20.dp)
                .clickable {
                    onVideoClicked()
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight(), verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.OndemandVideo,
                    contentDescription = "Video icon",
                    tint = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )

                Text(
                    text = videoData.videoTitle,
                    style = MaterialTheme.typography.body2.copy(MaterialTheme.colors.onSurface),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Text(
                text = if (videoData.videoVisible == 0) "visible" else "invisible",
                style = MaterialTheme.typography.body2.copy(if (videoData.videoVisible == 0) Color.Green else Color.Red),
            )
        }
    }
}

@Composable
private fun AddVideoItem(
    onAddVideoClicked: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(25.dp),
                color = MaterialTheme.colors.primaryVariant
            )
            .padding(horizontal = 15.dp, vertical = 15.dp)
            .clickable {
                onAddVideoClicked()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Add new video",
            style = MaterialTheme.typography.body2.copy(MaterialTheme.colors.onSurface),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}


@Composable
private fun FileItem(
    onUpdateFile: () -> Unit,
    onDeleteFile: () -> Unit,
    onFileClicked: () -> Unit
) {

    SwipeableActionsBox(
        startActions = swipeActionItem(
            icon = Icons.Default.AttachFile,
            color = Color.Yellow
        ) {
            onUpdateFile()
        },
        endActions = swipeActionItem(
            icon = Icons.Default.Delete,
            color = Color.Red
        ) {
            onDeleteFile()
        },
        swipeThreshold = 200.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 15.dp)
                .border(
                    width = 3.dp,
                    shape = RoundedCornerShape(15.dp),
                    color = MaterialTheme.colors.primaryVariant
                )
                .padding(horizontal = 15.dp, vertical = 15.dp)
                .clickable {
                    onFileClicked()
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Document Attached",
                style = MaterialTheme.typography.h6.copy(MaterialTheme.colors.onBackground),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Icon(
                imageVector = Icons.Default.FileOpen,
                contentDescription = "file icon",
                tint = MaterialTheme.colors.primaryVariant,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
    }
}

@Composable
private fun AddFileItem(
    onAddFileClicked: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(25.dp),
                color = MaterialTheme.colors.primaryVariant
            )
            .padding(horizontal = 15.dp, vertical = 15.dp)
            .clickable {
                onAddFileClicked()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Attach file",
            style = MaterialTheme.typography.body2.copy(MaterialTheme.colors.onSurface),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}


@Composable
private fun AddFileDialog(
    fileData: FileData? = null,
    onShowDialog: (Boolean) -> Unit,
    onAddFile: ((Uri) -> Unit)? = null,
    onUpdateFile: ((FileData) -> Unit)? = null
) {

    var fileUri: Uri? by remember {
        mutableStateOf(Uri.EMPTY)
    }

    AlertDialog(modifier = Modifier
        .fillMaxWidth(),
        onDismissRequest = {
            onShowDialog(false)
        },
        title = {
            Text(
                text = "Section Document",
                style = MaterialTheme.typography.h6.copy(
                    MaterialTheme.colors.primaryVariant,
                    fontWeight = FontWeight.Medium
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Spacer(modifier = Modifier.height(15.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                // verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Spacer(modifier = Modifier.height(30.dp))

                val filePickerLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.GetContent()
                )
                { uri ->
                    if (uri != null) {
                        // Handle the selected URI here
                        fileUri = uri
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    val hasAddedPic by remember {
                        derivedStateOf {
                            fileUri != Uri.EMPTY
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .border(
                                width = 1.dp,
                                color = if (hasAddedPic) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.primary,
                                shape = CircleShape
                            )
                            .clickable {
                                filePickerLauncher.launch("application/pdf")
                            }, contentAlignment = Alignment.Center
                    ) {

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(5.dp)
                        ) {

                            Text(
                                text = if (hasAddedPic) "Added" else "Add Document",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.primaryVariant),
                                modifier = Modifier.padding(
                                    horizontal = 10.dp,
                                    vertical = 10.dp
                                )
                            )
                            Icon(
                                imageVector = if (hasAddedPic) Icons.Default.DoneOutline else Icons.Default.AddCircle,
                                contentDescription = null,
                                modifier = Modifier.size(30.dp),
                                tint = MaterialTheme.colors.primaryVariant
                            )
                        }
                    }
                }
            }
        },
        buttons = {
            Box(
                Modifier
                    .fillMaxWidth(), contentAlignment = Alignment.Center
            ) {

                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(16.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colors.primaryVariant,
                        backgroundColor = MaterialTheme.colors.primary
                    ),
                    onClick = {
                        if (fileData?.fileId != null) {
                            onUpdateFile?.invoke(fileData.copy(fileUrl = fileUri.toString()))

                        } else onAddFile?.invoke(fileUri!!)

                        onShowDialog(false)
                    },
                    enabled = fileUri != Uri.EMPTY
                ) {
                    Text(text = "confirm", style = MaterialTheme.typography.body2)
                }
            }
        }
    )
}