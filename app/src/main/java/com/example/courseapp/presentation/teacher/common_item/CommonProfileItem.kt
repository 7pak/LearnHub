package com.example.courseapp.presentation.teacher.common_item

import android.content.Context
import android.util.Log
import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.courseapp.R
import com.example.courseapp.common.UserVerificationModel
import com.example.courseapp.domain.model.AuthModel
import com.example.courseapp.domain.model.TeacherModel
import com.example.courseapp.presentation.login_signUp.common_item.*
import com.example.courseapp.presentation.teacher.states.profile.ProfileState
import com.example.courseapp.presentation.teacher.states.profile.ProfileStateModel
import java.util.regex.Pattern

enum class ProfileScreenType {
    EDITE_SCREEN,
    INFO_SCREEN
}

@Composable
fun EditableProfile(
    profileStateModel: ProfileStateModel = viewModel(),
    userVerificationModel: UserVerificationModel,
    authModel: AuthModel,
    teacherModel: TeacherModel,
    onNavigate: () -> Unit
) {
    val state by profileStateModel.profileState
    val profileInfo by teacherModel.profileInfo.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TopProfileEdit(
            context = context,
            profileStateModel = profileStateModel,
            profilePic = profileInfo?.teacherProfile
        )


        when (state.screenType) {
            ProfileScreenType.EDITE_SCREEN -> UserEditInformation(profileStateModel = profileStateModel) {
                // TODO:
            }

            ProfileScreenType.INFO_SCREEN -> UserInformation(profileStateModel.profileState.value,
                profileName = profileInfo?.teacherName
                    ?: "User",
                profileAbout = profileInfo?.teacherAbout ?: "",
                onDeleteAccount = {

                    deleteAccount(lifecycleOwner = lifecycleOwner, teacherModel = teacherModel,userVerificationModel = userVerificationModel,onFailed = null){
                        onNavigate()
                    }
                }
            ) {
                logoutClicked(
                    lifecycleOwner = lifecycleOwner,
                    userVerificationModel = userVerificationModel,
                    authModel = authModel
                ) {
                    onNavigate()
                }
            }
        }
    }

}


@Composable
fun TopProfileEdit(context: Context, profilePic: String?, profileStateModel: ProfileStateModel) {
    val state by profileStateModel.profileState
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        IconButton(
            onClick = {
                if (state.screenType == ProfileScreenType.INFO_SCREEN) {
                    profileStateModel.updateProfileState(state.copy(screenType = ProfileScreenType.EDITE_SCREEN))
                } else profileStateModel.updateProfileState(state.copy(screenType = ProfileScreenType.INFO_SCREEN))
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(5.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "edit profile",
                tint = MaterialTheme.colors.primaryVariant
            )
        }

        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(context = context)
                .data(
                    profilePic?.toUri() ?: R.drawable.ic_profile
                )
                .crossfade(true)
                .transformations(CircleCropTransformation())
                .build(),
            placeholder = painterResource(id = R.drawable.ic_profile),
            contentScale = ContentScale.Crop
        )


        Image(
            painter = painter,
            contentDescription = "profile_header",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 30.dp)
                .size(200.dp)
                .clickable {
                    if (state.screenType == ProfileScreenType.EDITE_SCREEN) {
                    }
                }
        )
    }
}


@Composable
fun UserInformation(
    state: ProfileState,
    profileName: String,
    profileAbout: String,
    onDeleteAccount: () -> Unit,
    onLogoutClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .padding(vertical = 16.dp),  // Add vertical padding to push content up a bit
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = profileName,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.h6.copy(color = MaterialTheme.colors.primaryVariant),
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(horizontal = 10.dp, vertical = 8.dp)
        )

        Text(
            text = profileAbout,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.h6.copy(color = MaterialTheme.colors.primaryVariant),
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(horizontal = 10.dp)
        )

        Spacer(modifier = Modifier.weight(1f))  // Add spacer to push Button to the bottom

        Button(
            onClick = {
                onLogoutClicked()

            },
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colors.primaryVariant,
                backgroundColor = MaterialTheme.colors.primary
            )
        ) {
            Text(text = "Logout")
        }

        Button(
            onClick = {
                onDeleteAccount()
            },
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colors.primaryVariant,
                backgroundColor = MaterialTheme.colors.primary
            )
        ) {
            Text(text = "delete account")
        }
    }
}

@Composable
private fun UserEditInformation(
    profileStateModel: ProfileStateModel,
    onConfirmEdit: (ProfileState) -> Unit
) {
    val state by profileStateModel.profileState

    var emailError by remember { mutableStateOf("") }

    var passwordError by remember { mutableStateOf("") }
    var passwordVisibility by remember {
        mutableStateOf(false)
    }
    val fieldsFilled by remember {
        derivedStateOf {
            state.firstName.isNotEmpty() && state.firstName.isNotEmpty() && state.email.isNotEmpty() && state.password.isNotEmpty() &&
                    state.passwordConfirmation.isNotEmpty() && (state.password == state.passwordConfirmation && state.password.length > 7) &&
                    emailError.isEmpty()
        }
    }
    val focusManager = LocalFocusManager.current

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(900.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedTextFieldWithLabel(
                    value = state.firstName,
                    onValueChange = { profileStateModel.updateProfileState(state.copy(firstName = it)) },
                    label = "First Name",
                    modifier = Modifier
                        .width(190.dp)
                        .padding(horizontal = 10.dp)
                )
                OutlinedTextFieldWithLabel(
                    value = state.lastName,
                    onValueChange = { profileStateModel.updateProfileState(state.copy(lastName = it)) },
                    label = "Last Name",
                    modifier = Modifier
                        .width(190.dp)
                        .padding(horizontal = 10.dp)

                )
            }


            OutlinedTextFieldWithLabelAndValidation(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                value = state.email,
                onValueChange = { newEmail ->
                    profileStateModel.updateProfileState(state.copy(email = newEmail))
                    emailError =
                        if (!Pattern.matches(Patterns.EMAIL_ADDRESS.pattern(), newEmail)) {
                            "Email is not formatted correctly"
                        } else {
                            ""
                        }
                },
                label = "Email",
                isError = emailError.isNotEmpty(),
                errorMessage = emailError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )


            OutlinedTextFieldWithLabelAndValidation(
                modifier = Modifier.padding(horizontal = 10.dp),
                value = state.password,
                onValueChange = { newPassword ->
                    profileStateModel.updateProfileState(state.copy(password = newPassword))
                    passwordError = if (newPassword.length < 8) {
                        "Password must be at least 8 characters"
                    } else {
                        ""
                    }
                },
                label = "Password",
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        Icon(
                            imageVector = if (passwordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = "password visibility",
                            tint = if (passwordVisibility) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.primaryVariant.copy(
                                ContentAlpha.disabled
                            ),
                            modifier = Modifier.padding(end = 20.dp)
                        )
                    }
                },
                isError = passwordError.isNotEmpty(),
                errorMessage = passwordError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        passwordError = if (state.password.length < 8) {
                            focusManager.clearFocus(false)
                            "Password must be at least 8 characters"
                        } else {
                            focusManager.moveFocus(FocusDirection.Down)
                            ""
                        }
                    }
                )
            )

            OutlinedTextFieldWithLabelAndValidation(
                modifier = Modifier.padding(horizontal = 10.dp),
                value = state.passwordConfirmation,
                onValueChange = {
                    profileStateModel.updateProfileState(
                        state.copy(
                            passwordConfirmation = it
                        )
                    )
                },
                label = "Password Confirmation",
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus(true) }
                ),
                isError = state.passwordConfirmation != state.password,
                errorMessage = "Passwords do not match"
            )

            ConfirmButton(
                modifier = Modifier.padding(vertical = 30.dp),
                text = "Save Edits",
                onClick = {
                    onConfirmEdit(state)
                    profileStateModel.updateProfileState(state.copy(screenType = ProfileScreenType.INFO_SCREEN))
                    profileStateModel.updateProfileState(ProfileState())
                },
                isEnabled = fieldsFilled
            )
        }
    }
}
