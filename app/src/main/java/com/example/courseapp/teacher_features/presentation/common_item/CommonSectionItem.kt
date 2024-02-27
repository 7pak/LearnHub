package com.example.courseapp.teacher_features.presentation.common_item

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DoneOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.OndemandVideo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.courseapp.navigation.Screens
import com.example.courseapp.teacher_features.presentation.states.CourseStatesModel
import com.example.courseapp.teacher_features.presentation.states.add_section.FileFields
import com.example.courseapp.teacher_features.presentation.states.add_section.SectionState
import com.example.courseapp.teacher_features.presentation.states.add_section.VideoFields
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox


@Composable
fun Sections(
    courseStatesModel: CourseStatesModel,
    navController: NavHostController,
    onCreateFile: (SectionState) -> Unit,
    onUpdateSection: (SectionState) -> Unit,
    onDeleteSection: (SectionState) -> Unit,
    onCreateSection: (SectionState) -> Unit,
) {

    var currentSectionState by remember {
        mutableStateOf(SectionState())
    }

    val sectionList by courseStatesModel.sections.collectAsState()


    sectionList.forEach { section ->
        SectionField(sectionState = section, onUpdateSection = {
            onUpdateSection(it)
        }, onDeleteSection = {
            courseStatesModel.updateSectionState(it)
            onDeleteSection(it)
            Log.d("AddCourse", "sectionId for videos :${it.sectionId} ")
        }, onAddDocumentClicked = {
            currentSectionState = it
        }) {
            Log.d("AddCourse", "sectionId for files :${it.sectionId} ")
            it.sectionId?.let { sectionId ->
                navController.navigate(Screens.AddVideoScreen.passSectionId(id = sectionId))
            }
        }
    }


    AddSection {
        onCreateSection(SectionState(sectionName = it))
    }
    Text(
        text = "Swipe left to delete section",
        style = MaterialTheme.typography.body2.copy(MaterialTheme.colors.onSurface),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
    )

    if (currentSectionState.showDialog) {
        AddFileDialog(state = currentSectionState) {
            currentSectionState = it
            onCreateFile(it)

            currentSectionState = SectionState()
        }
    }

}


@Composable
fun AddSection(
    onNewSection: (String) -> Unit
) {

    var state by remember { mutableStateOf("") }


    var addingSection by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .border(
                width = 3.dp,
                color = MaterialTheme.colors.primaryVariant,
                shape = RoundedCornerShape(10.dp)
            ), verticalAlignment = Alignment.CenterVertically

    ) {
        if (!addingSection) {
            Row(
                modifier = Modifier
                    .clickable { addingSection = true }
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Add New Section",
                    style = MaterialTheme.typography.h6.copy(MaterialTheme.colors.onBackground),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 20.dp)
                )

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )


            }
        } else {
            TextField(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 20.dp, horizontal = 10.dp)
                    .background(Color.Transparent),
                value = state,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colors.onBackground,
                    cursorColor = MaterialTheme.colors.primaryVariant,
                    backgroundColor = MaterialTheme.colors.background
                ),
                textStyle = TextStyle(fontSize = MaterialTheme.typography.h6.fontSize),
                onValueChange = {

                    state = it
                },
                placeholder = {
                    Text(
                        text = "Section name",
                        style = MaterialTheme.typography.h6.copy(MaterialTheme.colors.onBackground),
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onNewSection( state)
                        addingSection = false
                        state = ""
                    }
                ),
                singleLine = true
            )

        }

    }
}

@Composable
private fun AddDocument(
    sectionState: SectionState,
    onAddVideoClicked: (SectionState) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(15.dp)
            .clickable {
                onAddVideoClicked(sectionState.copy(showDialog = true))
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(
            imageVector = Icons.Default.AddCircle,
            contentDescription = "New document",
            tint = MaterialTheme.colors.primaryVariant
        )

        Text(
            text = "New document",
            style = MaterialTheme.typography.body2.copy(MaterialTheme.colors.onSurface),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun SectionField(
    sectionState: SectionState,
    onUpdateSection: (SectionState) -> Unit,
    onDeleteSection: (SectionState) -> Unit,
    onAddDocumentClicked: (SectionState) -> Unit,
    onSectionClicked: (SectionState) -> Unit
) {

    var isAddingSection by remember {
        mutableStateOf(false)
    }
    var state by remember {
        mutableStateOf("")
    }

    if (isAddingSection) {
        TextField(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 20.dp, horizontal = 10.dp)
                .background(Color.Transparent),
            value = state,
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colors.onBackground,
                cursorColor = MaterialTheme.colors.primaryVariant,
                backgroundColor = MaterialTheme.colors.background
            ),
            textStyle = TextStyle(fontSize = MaterialTheme.typography.h6.fontSize),
            onValueChange = {
                state = it
            },
            placeholder = {
                Text(
                    text = "New section name",
                    style = MaterialTheme.typography.h6.copy(MaterialTheme.colors.onBackground),
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onUpdateSection(sectionState.copy(sectionName = state))
                    isAddingSection = false
                }
            ),
            singleLine = true
        )
    } else {
        SwipeableActionsBox(
            startActions = swipeActionItem(
                icon = Icons.Default.Edit,
                color = MaterialTheme.colors.primaryVariant
            ) {
                isAddingSection = true
            },
            endActions = swipeActionItem(
                icon = Icons.Default.Delete,
                color = Color.Red
            ) {
                onDeleteSection(sectionState)
            },
            swipeThreshold = 200.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
                    .border(
                        width = 3.dp,
                        color = MaterialTheme.colors.primaryVariant,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clickable {
                        onSectionClicked(sectionState)
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = sectionState.sectionName,
                    style = MaterialTheme.typography.h6.copy(MaterialTheme.colors.onBackground),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
                if (sectionState.sectionFile == null) {
                    AddDocument(sectionState) {
                        onAddDocumentClicked(it)
                    }
                } else {
                    Text(
                        text = "Document: " + sectionState.sectionFile.fileName,
                        style = MaterialTheme.typography.body2.copy(MaterialTheme.colors.onSurface),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(vertical = 20.dp)
                    )
                }

                sectionState.sectionVideos.forEach { videoFields ->
                    Log.d("AddCourse", "SectionFieldVideos:${videoFields.videoName} ")
                    VideoField(
                        videoFields = videoFields, modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 10.dp)
                    )
                }

            }
        }
    }
}


    @Composable
    private fun VideoField(
        modifier: Modifier = Modifier,
        videoFields: VideoFields
    ) {
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {

            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(), verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.OndemandVideo,
                    contentDescription = "Video icon",
                    tint = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )

                Text(
                    text = videoFields.videoName,
                    style = MaterialTheme.typography.body2.copy(MaterialTheme.colors.onSurface),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Text(
                text = if (videoFields.isVisible) "visible" else "invisible",
                style = MaterialTheme.typography.body2.copy(if (videoFields.isVisible) Color.Green else Color.Red),
            )
        }
    }

    @Composable
    private fun AddFileDialog(
        state: SectionState,
        onNewList: (SectionState) -> Unit
    ) {

        var fileUri: Uri? by remember {
            mutableStateOf(Uri.EMPTY)
        }

        var fileName by remember {
            mutableStateOf("")
        }


        AlertDialog(modifier = Modifier
            .fillMaxWidth(),
            onDismissRequest = {
                onNewList(state.copy(showDialog = false))
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


                    TextField(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .background(Color.Transparent),
                        value = fileName,
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = MaterialTheme.colors.onBackground,
                            cursorColor = MaterialTheme.colors.primaryVariant,
                            backgroundColor = Color.Transparent
                        ),
                        // textStyle = TextStyle(fontSize = MaterialTheme.typography.h6.fontSize),
                        onValueChange = {
                            fileName = it
                        },
                        placeholder = {
                            Text(
                                text = "Document name",
                            )
                        },
                        singleLine = true
                    )

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
                            onNewList(
                                state.copy(
                                    showDialog = false,
                                    sectionFile = FileFields(
                                        fileUri = fileUri!!,
                                        fileName = fileName,
                                        sectionId = state.sectionId!!
                                    )
                                )
                            )
                        },
                        enabled = fileName.isNotEmpty() && fileUri != Uri.EMPTY && state.sectionId != null
                    ) {
                        Text(text = "confirm", style = MaterialTheme.typography.body2)
                    }
                }
            }
        )
    }


    fun swipeActionItem(icon: ImageVector, color: Color, onClick: () -> Unit): List<SwipeAction> {
        return listOf(
            SwipeAction(
                onSwipe = {
                    onClick()
                },
                icon = {
                    Icon(
                        modifier = Modifier.padding(10.dp),
                        imageVector = icon,
                        contentDescription = "SWIPE ACTION"
                    )
                },
                background = color
            )
        )
    }