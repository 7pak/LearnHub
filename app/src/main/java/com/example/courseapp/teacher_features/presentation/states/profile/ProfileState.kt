package com.example.courseapp.teacher_features.presentation.states.profile

import android.net.Uri
import com.example.courseapp.teacher_features.presentation.common_item.ProfileScreenType

data class ProfileState(
    val firstName: String = "",
    val lastName:String="",
    val aboutUser:String = "",
    val email: String = "",
    val password: String = "",
    val passwordConfirmation: String = "",
    val profilePhoto: Uri?=null,
    val screenType: ProfileScreenType = ProfileScreenType.INFO_SCREEN
)
