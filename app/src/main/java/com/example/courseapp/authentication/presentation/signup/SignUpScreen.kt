package com.example.courseapp.authentication.presentation.signup

import android.content.res.Configuration
import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.courseapp.navigation.FROM_SIGNUP
import com.example.courseapp.navigation.Screens
import com.example.courseapp.authentication.presentation.common_item.AuthStatesModel
import com.example.courseapp.authentication.presentation.common_item.ConfirmButton
import com.example.courseapp.authentication.presentation.common_item.HyperLinkClickable
import com.example.courseapp.authentication.presentation.common_item.OutlinedTextFieldWithLabel
import com.example.courseapp.authentication.presentation.common_item.OutlinedTextFieldWithLabelAndValidation
import com.example.courseapp.authentication.presentation.common_item.ProgressBar
import com.example.courseapp.authentication.presentation.common_item.UserTypeButtons
import com.example.courseapp.authentication.presentation.common_item.signUpClicked
import com.example.courseapp.common.UserVerificationModel
import com.example.courseapp.domain.model.AuthModel
import com.example.courseapp.theme.CourseAppTheme
import java.util.regex.Pattern

@Composable
fun SignUpScreen(
    navController: NavHostController,
    authStatesModel: AuthStatesModel = viewModel(),
    authModel: AuthModel = hiltViewModel(),
    userVerificationModel: UserVerificationModel = hiltViewModel(),
) {

    val state by authStatesModel.authState

    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    var emailError by remember { mutableStateOf("") }

    var passwordError by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    val fieldsFilled by remember {
        derivedStateOf {
            state.name.isNotEmpty() && state.email.isNotEmpty() && state.password.isNotEmpty() &&
                    state.passwordConfirmation.isNotEmpty() && (state.password == state.passwordConfirmation && state.password.length > 7) &&
                    emailError.isEmpty()
        }
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    val focusManager = LocalFocusManager.current


    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {

                Column(
                    modifier = Modifier.padding(vertical = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Create new account",
                        style = MaterialTheme.typography.h6.copy(
                            color = MaterialTheme.colors.onBackground
                        ),
                    )
                    Text(
                        text = "Please fill the form to continue",
                        style = MaterialTheme.typography.body1.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.disabled)
                        ),
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                }


                UserTypeButtons(state.userType) { selectedType ->
                    authStatesModel.updateAuthState(state.copy(userType = selectedType))
                }
            }

            item {

                OutlinedTextFieldWithLabel(
                    value = state.name,
                    onValueChange = { authStatesModel.updateAuthState(state.copy(name = it)) },
                    label = "Name",
                    modifier = Modifier.fillMaxWidth(0.8f)
                )

                OutlinedTextFieldWithLabelAndValidation(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    value = state.email,
                    onValueChange = { newEmail ->
                        authStatesModel.updateAuthState(state.copy(email = newEmail))
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
                    modifier = Modifier.fillMaxWidth(0.8f),
                    value = state.password,
                    onValueChange = { newPassword ->
                        authStatesModel.updateAuthState(state.copy(password = newPassword))
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
                    modifier = Modifier.fillMaxWidth(0.8f),
                    value = state.passwordConfirmation,
                    onValueChange = {
                        authStatesModel.updateAuthState(
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
            }

            item {

                ConfirmButton(
                    modifier = Modifier.padding(vertical = 20.dp),
                    text = "Sign Up",
                    onClick = {
                        signUpClicked(
                            name = state.name,
                            email = state.email,
                            password = state.password,
                            userType = state.userType,
                            authModel = authModel,
                            userVerificationModel = userVerificationModel,
                            lifecycle = lifecycleOwner,
                            context = context,
                            onNavigate = {
                                navController.navigate(
                                    Screens.VerificationScreen.passOptionalArg(
                                        FROM_SIGNUP
                                    )
                                )
                            }
                        ) {
                            isLoading = it
                        }
                    },
                    isEnabled = fieldsFilled
                )

                HyperLinkClickable(
                    text = "Have an Account? ",
                    linkText = "Login",
                    linkColor = MaterialTheme.colors.primaryVariant
                ) {
                    navController.popBackStack()
                }
            }
        }
        if (isLoading) {
            ProgressBar(modifier = Modifier.align(Alignment.Center))
        }
    }
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SignUpPreview() {
    CourseAppTheme {
        //SignUpScreen ()
    }
}