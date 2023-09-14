package com.example.courseapp.presentation.login_signUp.verification

import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.courseapp.FROM_EMAIL_VERIFICATION_PAGE
import com.example.courseapp.FROM_SIGNUP
import com.example.courseapp.Screens
import com.example.courseapp.common.Constants
import com.example.courseapp.common.UserVerificationModel
import com.example.courseapp.domain.model.AuthModel
import com.example.courseapp.presentation.login_signUp.common_item.ConfirmButton
import com.example.courseapp.presentation.login_signUp.common_item.HyperLinkClickable
import com.example.courseapp.presentation.login_signUp.common_item.OutlinedTextFieldWithLabelAndValidation
import com.example.courseapp.presentation.login_signUp.common_item.resendVerificationOtpClicked
import com.example.courseapp.presentation.login_signUp.common_item.verificationClicked
import com.example.courseapp.presentation.ui.theme.CourseAppTheme

@Composable
fun VerificationPage(
    navController: NavHostController,
    authModel: AuthModel = hiltViewModel(),
    userVerificationModel: UserVerificationModel = hiltViewModel(),
    source:String,
    onNavigate:()->Unit
) {

    val lifeCycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    var otpNumber by remember {
        mutableStateOf("")
    }

    val filledField by remember {
        derivedStateOf {
            otpNumber.toIntOrNull() != null
        }
    }

    // Timer value in milliseconds (5 minutes)
    val initialTime = 5 * 60 * 1000L
    var remainingTime by remember { mutableStateOf(initialTime) }

    val timer: CountDownTimer = object : CountDownTimer(remainingTime, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            remainingTime = millisUntilFinished
        }

        override fun onFinish() {
            Toast.makeText(
                context,
                "time up!: opt code is invalid please resend for new one",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    // Start the timer when the composable is first composed
    DisposableEffect(Unit) {
        timer.start()

        // Cleanup when the composable is disposed
        onDispose {
            timer.cancel()
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
                text = "Email Verification ",
                style = MaterialTheme.typography.h6.copy(
                    color = MaterialTheme.colors.onBackground
                ),
                modifier = Modifier.padding(top = 40.dp)

            )

            Text(
                text = "Remaining: ${formatTime(remainingTime)}",
                style = MaterialTheme.typography.h6.copy(
                    color = MaterialTheme.colors.onBackground
                ),
                modifier = Modifier.padding(top = 40.dp)

            )

            OutlinedTextFieldWithLabelAndValidation(
                modifier = Modifier.padding(top = 10.dp),
                value = if (otpNumber.toIntOrNull() == null) "" else otpNumber,
                onValueChange = {
                    otpNumber = it

                },
                label = "Otp Number",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                )
            )
            HyperLinkClickable(
                text = "Didn't get any email? ",
                linkText = "Resend",
                linkColor = MaterialTheme.colors.primaryVariant
            ) {
                resendVerificationOtpClicked(
                    authModel = authModel,
                    lifecycleOwner = lifeCycleOwner,
                    context = context
                ){
                    if (it) remainingTime = initialTime
                }
            }

            ConfirmButton(text = "Submit", onClick = {
                try {
                    Log.d("ErrorSkip", "VerificationPage: $otpNumber")
                    verificationClicked(
                        authModel = authModel,
                        userVerificationModel = userVerificationModel,
                        otp = otpNumber.toInt(),
                        lifecycleOwner = lifeCycleOwner,
                        context = context
                    ) {
                        onNavigate()
                    }
                }catch (e:Exception){
                    Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }, isEnabled = filledField)

            Button(
                onClick = {
                    userVerificationModel.clearToken()
                    navController.navigate(Constants.LOGIN_SIGNUP_SCREEN_ROUTE)
                },
                modifier = Modifier
                    .width(300.dp)
                    .height(70.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor =  MaterialTheme.colors.onBackground,
                    backgroundColor =  MaterialTheme.colors.error ,
                    disabledContentColor = MaterialTheme.colors.primary
                ),
                shape = RoundedCornerShape(25.dp),
            ) {
                Text(
                    text = "Logout",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h6
                )
            }
        }
    }
}


fun formatTime(millis: Long): String {
    val seconds = millis / 1000
    val minutes = seconds / 60
    val formattedSeconds = seconds % 60
    return String.format("%02d:%02d", minutes, formattedSeconds)
}


@Preview(showBackground = true)
@Composable
fun OtpScreenPreview() {
    CourseAppTheme {
         // VerificationPage(rememberNavController())
    }
}