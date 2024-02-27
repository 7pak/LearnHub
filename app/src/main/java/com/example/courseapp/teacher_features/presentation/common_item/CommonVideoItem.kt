package com.example.courseapp.teacher_features.presentation.common_item

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VideoFile
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.courseapp.authentication.presentation.common_item.ConfirmButton
import com.example.courseapp.navigation.SharedViewModel
import com.example.courseapp.teacher_features.presentation.states.add_section.VideoFields

@Composable
fun SectionVideos(
    sectionId:Int,
    sharedViewModel: SharedViewModel,
    onNewVideo:(VideoFields, Boolean)->Unit
) {
    val currentSectionId by remember {
        mutableStateOf(sharedViewModel.videoFields?.sectionId?:sectionId)
    }
    val videoId by remember {
        mutableStateOf(sharedViewModel.videoFields?.videoId)
    }

    val isUpdating by remember {
        mutableStateOf(sharedViewModel.videoFields?.isAddingNew?:false)
    }


    var videoName by remember {
        mutableStateOf(sharedViewModel.videoFields?.videoName ?: "")
    }
    var videoUri: Uri? by remember {
        mutableStateOf(sharedViewModel.videoFields?.video?:Uri.EMPTY)
    }

    var isVisible by remember {
        mutableStateOf(sharedViewModel.videoFields?.isVisible?:false)
    }


    val videoPicker =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                videoUri = uri
            }
        }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var lifecycle: Lifecycle.Event by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }

    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }




    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Text(
            text = "Section video",
            style = MaterialTheme.typography.h5.copy(
                MaterialTheme.colors.primaryVariant,
                fontWeight = FontWeight.Medium
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        Box(
            modifier = Modifier

                .fillMaxWidth(0.8f)
                .wrapContentHeight()
                .border(
                    width = 3.dp,
                    color = MaterialTheme.colors.primary
                ), contentAlignment = Alignment.Center
        ) {
            if (videoUri != Uri.EMPTY) {
                VideoPlayerComponent(
                    videoUri = videoUri!!,
                    context = context,
                    lifecycle = lifecycle
                )
            } else {
                Text(
                    text = "Empty video",
                    style = MaterialTheme.typography.h6.copy(
                        MaterialTheme.colors.primary,
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                )
            }
        }


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(vertical = 10.dp)
                .clickable {
                    videoUri = Uri.EMPTY
                    videoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly))
                }
        ) {

            Text(
                text = "Add video",
                style = MaterialTheme.typography.body2.copy(
                    MaterialTheme.colors.primary,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier
                    .padding(vertical = 20.dp)
            )
            Icon(
                imageVector = Icons.Default.VideoFile,
                contentDescription = "add video",
                tint = MaterialTheme.colors.primary
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
        TextField(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .background(Color.Transparent),
            value = videoName,
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colors.onBackground,
                cursorColor = MaterialTheme.colors.primaryVariant,
                backgroundColor = Color.Transparent
            ),
            textStyle = TextStyle(fontSize = MaterialTheme.typography.h6.fontSize),
            onValueChange = {
                videoName = it
            },
            placeholder = {
                Text(
                    text = "Video name", color = MaterialTheme.colors.primary
                )
            },
            singleLine = true
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            VisibilityCheckBox(text = "Invisible for people", isVisible = !isVisible) {
                isVisible = false
            }

            VisibilityCheckBox(text = "Visible for people", isVisible = isVisible) {
                isVisible = true
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        ConfirmButton(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(16.dp),
            text = "Add video",
            onClick = {
                sharedViewModel.updateVideoFields(newVideoFields = VideoFields(
                    videoName = videoName,
                    video = videoUri,
                    isVisible = isVisible,
                    sectionId = currentSectionId,
                    videoId = videoId
                )
                )
                onNewVideo(
                    VideoFields(
                        videoName = videoName,
                        video = videoUri,
                        isVisible = isVisible,
                        sectionId = currentSectionId
                    ),isUpdating
                )
            },
            isEnabled = videoName.isNotEmpty(),
        )
    }
}

@Composable
fun VisibilityCheckBox(
    text: String,
    isVisible: Boolean,
    onVisibilityResult: (Boolean) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Checkbox(
            checked = isVisible, onCheckedChange = {
                onVisibilityResult(it)
            }, colors = CheckboxDefaults.colors(
                uncheckedColor = MaterialTheme.colors.primary,
                checkedColor = MaterialTheme.colors.primaryVariant,
                checkmarkColor = MaterialTheme.colors.onBackground
            )
        )
        Text(
            text = text,
            style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.onSurface)
        )
    }
}

@Composable
fun VideoPlayerComponent(videoUri: Uri, context: Context, lifecycle: Lifecycle.Event) {

    val mediaItem = remember {
        MediaItem.fromUri(videoUri)
    }
    val player = ExoPlayer.Builder(context).build()

    player.setMediaItem(mediaItem)


    // player.play()
    player.repeatMode
    val playerView = AndroidView(
        factory = { context ->
            PlayerView(context).also {

                it.player = player
            }
        }, update = {
            when (lifecycle) {
                Lifecycle.Event.ON_PAUSE -> {
                    it.onPause()
                    it.player?.stop()
                }

                Lifecycle.Event.ON_RESUME -> {
                    it.onResume()
                }

                else -> Unit
            }
        }, modifier = Modifier
            .fillMaxSize()
            .aspectRatio(16 / 9f)
    )



    DisposableEffect(key1 = playerView) {
        player.prepare()
        onDispose {
            player.release()
        }
    }

}

