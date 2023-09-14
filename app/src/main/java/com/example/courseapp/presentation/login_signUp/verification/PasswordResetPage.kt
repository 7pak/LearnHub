package com.example.courseapp.presentation.login_signUp.verification

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.courseapp.common.Constants
import com.example.courseapp.common.UserVerificationModel
import com.example.courseapp.domain.model.AuthModel
import com.example.courseapp.presentation.login_signUp.common_item.ConfirmButton
import com.example.courseapp.presentation.login_signUp.common_item.OutlinedTextFieldWithLabelAndValidation
import com.example.courseapp.presentation.login_signUp.common_item.resetPasswordClicked

@Composable
fun PasswordResetPage(
    navController: NavHostController,
    authModel: AuthModel = hiltViewModel(),
    userVerificationModel:UserVerificationModel = hiltViewModel()
) {
   val context = LocalContext.current
   val lifecycleOwner = LocalLifecycleOwner.current

    var newPassword by rememberSaveable {
        mutableStateOf("")
    }

    var passwordVisibility by rememberSaveable {
        mutableStateOf(false)
    }

    val filledField by remember {
        derivedStateOf {
            newPassword.length>7
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {

        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {


            Text(
                text = "Reset your password",
                style = MaterialTheme.typography.h6.copy(
                    color = MaterialTheme.colors.onBackground
                ),
                modifier = Modifier.padding(top = 40.dp)

            )

            Text(
                text = "type your new password",
                style = MaterialTheme.typography.body2.copy(
                    color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.disabled)
                ),
                modifier = Modifier.padding(vertical = 10.dp)

            )

            OutlinedTextFieldWithLabelAndValidation(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 10.dp),
                value = newPassword,
                onValueChange = {
                    newPassword = it
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
                )
            )

            ConfirmButton(text = "Next", onClick = {
                try {
                    Log.d("ErrorSkip", "Password: $newPassword")
                    resetPasswordClicked(authModel = authModel, userVerificationModel = userVerificationModel, lifecycleOwner = lifecycleOwner, context = context, newPassword = newPassword)
                    {
                        userVerificationModel.clearToken()
                        userVerificationModel.clearVerification()
                        navController.navigate(Constants.LOGIN_SIGNUP_SCREEN_ROUTE)
                    }
                }catch (e:Exception){
                    Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }, isEnabled = filledField)

        }
    }
}