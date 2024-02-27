package com.example.courseapp.authentication.presentation.login

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.courseapp.R
import com.example.courseapp.navigation.Screens
import com.example.courseapp.authentication.presentation.common_item.AuthStatesModel
import com.example.courseapp.authentication.presentation.common_item.ConfirmButton
import com.example.courseapp.authentication.presentation.common_item.HyperLinkClickable
import com.example.courseapp.authentication.presentation.common_item.OutlinedTextFieldWithLabelAndValidation
import com.example.courseapp.authentication.presentation.common_item.ProgressBar
import com.example.courseapp.authentication.presentation.common_item.UserTypeButtons
import com.example.courseapp.authentication.presentation.common_item.loginClicked
import com.example.courseapp.common.Constants
import com.example.courseapp.common.UserVerificationModel
import com.example.courseapp.domain.model.AuthModel
import com.example.courseapp.navigation.SharedViewModel
import com.example.courseapp.theme.CourseAppTheme
import java.util.regex.Pattern

@Composable
fun LoginScreen(
    navController: NavHostController,
    authStatesModel: AuthStatesModel = viewModel(),
    authModel: AuthModel = hiltViewModel(),
    userVerificationModel: UserVerificationModel = hiltViewModel(),

    ) {

    val state by authStatesModel.authState


    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    var emailError by remember { mutableStateOf("") }

    var passwordVisibility by remember {
        mutableStateOf(false)
    }
    val focusManager = LocalFocusManager.current


    val fieldsFilled by remember {
        derivedStateOf {
            state.email.isNotBlank() && state.password.isNotBlank() && emailError.isEmpty()
        }
    }

    var isLoading by remember {
        mutableStateOf(false)
    }


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
                        text = "Welcome Back!",
                        style = MaterialTheme.typography.h6.copy(
                            color = MaterialTheme.colors.onBackground
                        ),
                    )
                    Text(
                        text = "Please Login to your account",
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


                OutlinedTextFieldWithLabelAndValidation(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(vertical = 10.dp),
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
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(vertical = 10.dp),
                    value = state.password,
                    onValueChange = { newPassword ->
                        authStatesModel.updateAuthState(state.copy(password = newPassword))
                    },
                    label = "Password",
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                            Icon(
                                imageVector = if (passwordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = "password visibility",
                                tint = if (passwordVisibility) MaterialTheme.colors.primaryVariant
                                else MaterialTheme.colors.primaryVariant.copy(
                                    ContentAlpha.disabled
                                )
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus(true) }
                    )
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Forgot Password?",
                        style = MaterialTheme.typography.body1.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.disabled)
                        ),
                        modifier = Modifier
                            .padding(horizontal = 45.dp, vertical = 10.dp)
                            .clickable {
                                navController.navigate(Screens.EmailVerificationScreen.route)
                            }
                    )
                }

            }


            item {

                ConfirmButton(
                    modifier = Modifier.padding(vertical = 30.dp),
                    text = "Login",
                    onClick = {
                        loginClicked(
                            email = state.email,
                            password = state.password,
                            authModel = authModel,
                            userVerificationModel = userVerificationModel,
                            userType = state.userType,
                            lifecycle = lifecycleOwner,
                            context = context,
                            onNavigate = {
                                navController.popBackStack()
                                navController.navigate(Screens.TeacherScreen.passUserId(it))
                            }
                        ) {
                            isLoading = it
                        }

                    },
                    isEnabled = fieldsFilled
                )

                Button(
                    onClick = {},
                    modifier = Modifier
                        .width(300.dp)
                        .height(70.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = Color.White
                    ),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.login_google),
                        contentDescription = "login with google",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(30.dp)
                            .padding(horizontal = 5.dp)
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = Color.Red)) {
                                append("Login ")
                            }
                            withStyle(style = SpanStyle(color = Color.Green)) {
                                append("with ")
                            }
                            withStyle(style = SpanStyle(color = Color.Blue)) {
                                append("Google")
                            }
                        },
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.body1
                    )
                }
                HyperLinkClickable(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = "Don't have an Account? ",
                    linkText = "Sign up",
                    linkColor = MaterialTheme.colors.primaryVariant
                ) {
                    navController.navigate(Screens.SignUpScreen.route)
                }
            }
        }
        if (isLoading) {
            ProgressBar(modifier = Modifier.align(Alignment.Center))
        }
    }
}


@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun LoginScreenPreview() {
    CourseAppTheme {
        // LoginScreen (remebe)
    }
}
