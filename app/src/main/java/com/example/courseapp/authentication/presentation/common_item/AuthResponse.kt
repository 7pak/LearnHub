package com.example.courseapp.authentication.presentation.common_item

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import com.example.courseapp.common.UserVerificationModel
import com.example.courseapp.authentication.data.remote.auth_data.FieldsVerificationData
import com.example.courseapp.authentication.data.remote.auth_data.LoginUserData
import com.example.courseapp.authentication.data.remote.auth_data.VerificationData
import com.example.courseapp.authentication.data.remote.auth_data.RegisterUserData
import com.example.courseapp.domain.model.AuthModel
import com.example.courseapp.navigation.UserType
import java.util.Date

fun loginClicked(
    email: String,
    password: String,
    authModel: AuthModel,
    userVerificationModel: UserVerificationModel,
    userType: UserType,
    lifecycle: LifecycleOwner,
    context: Context,
    onNavigate: (Int) -> Unit,
    onLoading: (Boolean) -> Unit
) {



    onLoading(true)
    authModel.loginUser(
        LoginUserData(
            email = email,
            password = password,
            type = when (userType) {
                UserType.TEACHER -> "teacher"
                UserType.STUDENT -> "student"
            }
        )
    )

    authModel.loginResponse.observe(lifecycle) { response ->
        Log.d(
            "ErrorSkip",
            " Login before Success Collected verification :${response.body()?.data?.email} "
        )
        if (response.isSuccessful && response.errorBody() == null) {
            Log.d(
                "ErrorSkip",
                " Login after Success Collected verification :${response.body()?.data?.verification.toString()} "
            )
            val token = response.body()?.data?.token
            Log.d(
                "ErrorSkip",
                " Login after Success Collected token :${token} "
            )
            token?.let {
                userVerificationModel.saveToken(it)
            }
            if (!token.isNullOrEmpty()) {
                val verificationId = response.body()?.data?.verification.toString()
                verificationId.let {
                    try {
                        userVerificationModel.saveVerification(it)
                    } catch (e: Exception) {
                        Log.d(
                            "ErrorSkip",
                            " didn't success updating verification :${response.body()?.data?.verification} "
                        )
                    }
                }

                val userId = response.body()?.data?.id


                Toast.makeText(
                    context,
                    "Login is Successful ${response.body()?.message}",
                    Toast.LENGTH_SHORT
                ).show()

                Log.d("ErrorSkip", "loginClicked: ${response.body()?.data?.id} ")

                userId?.let{
                    userVerificationModel.saveUserId(it)
                    onNavigate(it)
                }

            }

        } else {
            Log.d("LogSignScreen", "LogSignScreen:${response.errorBody()?.string()} ")
            Toast.makeText(
                context,
                "Login is NOT Successful: Invalid Type, Email or Password ",
                Toast.LENGTH_SHORT
            ).show()
        }
        onLoading(false)
    }
}


fun signUpClicked(
    name: String,
    email: String,
    password: String,
    userType: UserType,
    authModel: AuthModel,
    userVerificationModel: UserVerificationModel,
    lifecycle: LifecycleOwner,
    context: Context,
    onNavigate: () -> Unit,
    onLoading: (Boolean) -> Unit
) {

    onLoading(true)
    authModel.registerUser(
        RegisterUserData(
            name = name,
            email = email,
            password = password,
            type = when (userType) {
                UserType.TEACHER -> "teacher"
                UserType.STUDENT -> "student"
            }
        )
    )
    authModel.registerResponse.observe(lifecycle) { response ->

        if (response.isSuccessful && response.errorBody() == null) {
            val token = response.body()?.data?.token
            token?.let {
                userVerificationModel.saveToken(it)
            }

            Log.d(
                "LogSignScreen",
                "SignScreen:${response.body()?.data?.token} ${response.body()?.data?.email} "
            )
            Toast.makeText(
                context,
                "Response is Successful ${response.body()?.message}",
                Toast.LENGTH_SHORT
            ).show()
            when (userType) {
                UserType.TEACHER, UserType.STUDENT -> {
                    onNavigate()
                }
            }

        } else {
            Log.d("LogSignScreen", "LogSignScreen:${response.errorBody()?.string()} ")
            Toast.makeText(
                context,
                "Response is NOT Successful ${response.errorBody()?.string()}",
                Toast.LENGTH_SHORT
            ).show()
        }
        onLoading(false)
    }
}


fun logoutClicked(
    lifecycleOwner: LifecycleOwner,
    userVerificationModel: UserVerificationModel,
    authModel: AuthModel,
    onNavigate: () -> Unit
) {
    authModel.logoutUser()
    authModel.logoutResponse.observe(lifecycleOwner) { response ->
        if (response.isSuccessful && response.errorBody() == null) {
            Log.d("LogoutResponse", "onCreate: Logout Successfully ${response.body()?.message}")
            userVerificationModel.clearToken()
            userVerificationModel.clearVerification()
            userVerificationModel.clearUserId()
            onNavigate()
        } else {
            Log.d(
                "LogoutResponse",
                "onCreate: Logout UnSuccessfully ${response.errorBody()?.string()}"
            )

        }
    }
}


fun verificationClicked(
    authModel: AuthModel,
    userVerificationModel: UserVerificationModel,
    otp: Int,
    lifecycleOwner: LifecycleOwner,
    context: Context,
    onNavigate: () -> Unit
) {
    Log.d("ErrorSkip", "verificationClicked:  didn't verified yet")
    try {

        authModel.verifyUser(verificationData = VerificationData(code = otp))
        Log.d("ErrorSkip", "verificationClicked: verified and the otp has send ")
        authModel.otpVerificationResponse.observe(lifecycleOwner) { response ->
            if (response.isSuccessful && response.errorBody() == null) {
                Log.d("Verification", "verificationClicked: ${response.body()?.message} ")
                Toast.makeText(context, "${response.body()?.message}", Toast.LENGTH_SHORT).show()
                val token = response.body()?.data?.token
                token?.let {
                    userVerificationModel.saveToken(it)
                }
                userVerificationModel.saveVerification(Date().time.toString())

                onNavigate()
            } else {
                Log.d(
                    "Verification",
                    "verificationClicked: Error While Verificating ${response.errorBody()?.string()}"
                )
                Toast.makeText(context, "invalid Verification OTP", Toast.LENGTH_SHORT).show()

            }
        }
    }catch (e:Exception){
        Log.d("ErrorSkip", "error has occurred : ${e.localizedMessage} ")
    }

}

fun resendVerificationOtpClicked(
    authModel: AuthModel,
    lifecycleOwner: LifecycleOwner,
    context: Context,
    isSuccessful: (Boolean) -> Unit
) {
    authModel.resendOtp()
    authModel.resendOtpResponse.observe(lifecycleOwner) { response ->
        if (response.isSuccessful && response.errorBody() == null) {
            Log.d("Verification", "resendVerifyOtp: ${response.body()?.message} ")
            Toast.makeText(context, "${response.body()?.message}", Toast.LENGTH_SHORT).show()

            isSuccessful(true)
        } else {
            Log.d(
                "Verification",
                "resendVerifyOtp: Error While Resending ${response.errorBody()?.string()}"
            )
            isSuccessful(false)
        }
    }
}

//TODO try every thing then refactor the code to be more readable with sealed classes and Resource files

fun accountVerificationClicked(
    authModel: AuthModel,
    lifecycleOwner: LifecycleOwner,
    userVerificationModel:UserVerificationModel,
    context: Context,
    email: String,
    onNavigate: () -> Unit
) {
    authModel.accountVerification(fieldsVerificationData = FieldsVerificationData(email = email))

    authModel.accountVerificationResponse.observe(lifecycleOwner) { response ->
        if (response.isSuccessful && response.errorBody() == null) {
            Log.d("Verification", "EmailVerification: ${response.body()?.message} ")
           response.body()?.data?.token?.let {
               userVerificationModel.saveToken(it)
           }
            Toast.makeText(context, "${response.body()?.message}", Toast.LENGTH_SHORT).show()
            onNavigate()
        } else {
            Log.d(
                "Verification",
                "EmailVerification: Error While verifying ${response.errorBody()?.string()}"
            )
        }
    }
}


fun resetPasswordClicked(
    authModel: AuthModel,
    userVerificationModel: UserVerificationModel,
    lifecycleOwner: LifecycleOwner,
    context: Context,
    newPassword: String,
    isSuccessful: () -> Unit
) {

    authModel.resetPassword(FieldsVerificationData(password = newPassword))
    authModel.resetPasswordResponse.observe(lifecycleOwner) { response ->
        if (response.isSuccessful && response.errorBody() == null) {
            Log.d("Verification", "resetPassword: ${response.body()?.message} ")
            Toast.makeText(context, "${response.body()?.message}", Toast.LENGTH_SHORT).show()
            isSuccessful()
        } else {
            Log.d(
                "Verification",
                "resetPassword: Error While resetting ${response.errorBody()?.string()}"
            )
            userVerificationModel.clearToken()
        }
    }
}
