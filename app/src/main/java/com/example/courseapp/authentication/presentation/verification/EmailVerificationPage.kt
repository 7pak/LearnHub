package com.example.courseapp.authentication.presentation.verification

import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.courseapp.navigation.FROM_EMAIL_VERIFICATION_PAGE
import com.example.courseapp.navigation.Screens
import com.example.courseapp.common.UserVerificationModel
import com.example.courseapp.authentication.presentation.common_item.ConfirmButton
import com.example.courseapp.authentication.presentation.common_item.OutlinedTextFieldWithLabelAndValidation
import com.example.courseapp.authentication.presentation.common_item.accountVerificationClicked
import com.example.courseapp.domain.model.AuthModel
import java.util.regex.Pattern

@Composable
fun EmailVerificationPage(navController: NavHostController, authModel: AuthModel = hiltViewModel(), userVerificationModel: UserVerificationModel = hiltViewModel()) {

    val context = LocalContext.current
    val lifeCycleOwner = LocalLifecycleOwner.current

    var email by rememberSaveable {
        mutableStateOf("")
    }

    var emailError by remember {
        mutableStateOf("")
    }

    val filledField by remember {
        derivedStateOf {
            email.isNotEmpty() && emailError.isEmpty()
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
                text = "Help us know who you are ",
                style = MaterialTheme.typography.h6.copy(
                    color = MaterialTheme.colors.onBackground
                ),
                modifier = Modifier.padding(top = 40.dp)

            )

            Text(
                text = "type your email",
                style = MaterialTheme.typography.body2.copy(
                    color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.disabled)
                ),
                modifier = Modifier.padding(vertical = 10.dp)

            )

            OutlinedTextFieldWithLabelAndValidation(
                modifier = Modifier.padding(top = 30.dp),
                value = email,
                onValueChange = {
                    email = it

                    emailError =
                        if (!Pattern.matches(Patterns.EMAIL_ADDRESS.pattern(),email)){
                            "Email is not formatted right"
                        } else ""

                },
                label = "Email",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                )
            )

            ConfirmButton(text = "Next", onClick = {
                try {
                    Log.d("ErrorSkip", "VerificationPage: $email")
                    accountVerificationClicked(authModel = authModel, lifecycleOwner = lifeCycleOwner, userVerificationModel = userVerificationModel, context = context,email = email)
                    {
                        Log.d("ErrorSkip", "VerificationPage: im going to navigate......")
                        navController.navigate(Screens.VerificationScreen.passOptionalArg(source = FROM_EMAIL_VERIFICATION_PAGE))
                    }
                }catch (e:Exception){
                    Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }, isEnabled = filledField)

        }
    }
}