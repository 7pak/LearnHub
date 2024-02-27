package com.example.courseapp.teacher_features.presentation.common_item

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.example.courseapp.common.UserVerificationModel
import com.example.courseapp.teacher_features.data.remote.teacher_post_dto.AddCourseFields
import com.example.courseapp.teacher_features.data.remote.teacher_post_dto.AddFileFields
import com.example.courseapp.teacher_features.data.remote.teacher_post_dto.AddSectionFields
import com.example.courseapp.teacher_features.data.remote.teacher_post_dto.AddVideoFields
import com.example.courseapp.teacher_features.domain.model.TeacherModel


fun deleteAccount(
    lifecycleOwner: LifecycleOwner,
    teacherModel: TeacherModel,
    userVerificationModel: UserVerificationModel,
    onFailed: (() -> Unit)?,
    onNavigate: () -> Unit,
){
    teacherModel.deleteAccount()
    teacherModel.deleteAccountResponse.observe(lifecycleOwner ){response->
        if (response.isSuccessful) {
            Log.d(
                "Account",
                "delete: Response is Successful : ${response.body()?.message}}"
            )
            userVerificationModel.clearVerification()
            userVerificationModel.clearToken()
            onNavigate()

        } else {
            Log.d(
                "Account",
                "delete: Response is NOT : ${response.errorBody()?.string()}"
            )
            if (onFailed != null) {
                onFailed()
            }
        }
    }
}

fun addCourse(
    lifecycleOwner: LifecycleOwner,
    teacherModel: TeacherModel,
    addCourseFields: AddCourseFields,
    onFailed: (() -> Unit)?,
    onNavigate: (Int) -> Unit,
) {
    teacherModel.addCourse(
        category_id = addCourseFields.category_id,
        description = addCourseFields.description,
        price = addCourseFields.price,
        title = addCourseFields.title,
        photoUri = addCourseFields.photo
    )
    teacherModel.addCourseResponse.observe(lifecycleOwner) { response ->
        if (response.isSuccessful) {
            Log.d(
                "AddCourse",
                "addCourse: Response is Successful : ${response.body()?.message}}"
            )

            response.body()?.data?.courseId?.let {
                Log.d("AddCourse", "post_courseID in fun: $it")
                onNavigate(it)
            }
        } else {
            Log.d(
                "AddCourse",
                "addCourse: Response is NOT : ${response.errorBody()?.string()}"
            )
            if (onFailed != null) {
                onFailed()
            }
        }
    }
}

fun updateCourse(
    lifecycleOwner: LifecycleOwner,
    teacherModel: TeacherModel,
    addCourseFields: AddCourseFields,
    onFailed: (() -> Unit)?,
    onNavigate: () -> Unit,
) {
    teacherModel.updateCourse(
        category_id = addCourseFields.category_id,
        description = addCourseFields.description,
        price = addCourseFields.price,
        title = addCourseFields.title,
        photoUri = addCourseFields.photo,
        id = addCourseFields.courseId!!
    )
    teacherModel.updateCourseResponse.observe(lifecycleOwner) { response ->


        when(response.status.value){
            in 200..299 -> {
                Log.d(
                    "AddCourse",
                    "updateCourse: Response is Successful: ${response.status.description}}"
                )

                onNavigate()
            }

            else -> {
                Log.d(
                    "AddCourse",
                    "updateCourse: Response is Not Successful: : ${response.status.description}}"
                )
                if (onFailed != null) {
                    onFailed()
                }
            }
        }
    }
}

fun deleteCourse(
    lifecycleOwner: LifecycleOwner,
    teacherModel: TeacherModel,
    courseId: Int,
    onFailed: (() -> Unit)?,
    onNavigate: () -> Unit,
) {
    teacherModel.deleteCourse(id = courseId)
    teacherModel.deleteCourseResponse.observe(lifecycleOwner) { response ->
        if (response.isSuccessful) {
            Log.d(
                "AddCourse",
                "delete course: Response is Successful : ${response.body()?.message} -> $courseId}"
            )
            onNavigate()
        } else {
            Log.d(
                "AddCourse",
                "delete course: Response is NOT Successful: ${response.errorBody()?.string()}"
            )
            if (onFailed != null) {
                onFailed()
            }
        }
    }
}

fun addSection(
    teacherModel: TeacherModel,
    addSectionFields: AddSectionFields,
    lifecycleOwner: LifecycleOwner,
    onFailed: (() -> Unit)?,
    onNavigate: (Int) -> Unit,
) {
    teacherModel.addSectionResponse.removeObservers(lifecycleOwner)
    teacherModel.addSection(addSectionFields = addSectionFields)
    teacherModel.addSectionResponse.observe(lifecycleOwner) { response ->
        if (response.isSuccessful) {
            Log.d(
                "AddCourse",
                "addSection: Response is Successful : ${response.body()?.message}}"
            )

            Log.d(
                "AddCourse",
                "addSection: Response is Successful : ${response.body()?.data?.sectionId}}"
            )

            val sectionId = response.body()?.data?.sectionId
            sectionId?.let {
                onNavigate(sectionId)
            }


        } else {
            Log.d(
                "AddCourse",
                "addSection: Response is NOT Successful: ${response.errorBody()?.string()}"
            )
            if (onFailed != null) {
                onFailed()
            }
        }
    }
}

fun updateSection(
    lifecycleOwner: LifecycleOwner,
    teacherModel: TeacherModel,
    sectionId: Int,
    title: String,
    onFailed: (() -> Unit)?,
    onNavigate: () -> Unit,
) {
    teacherModel.updateSection(id = sectionId, title = title)

    teacherModel.updateSectionResponse.observe(lifecycleOwner) { response ->
        if (response.isSuccessful) {
            Log.d(
                "AddCourse",
                "update section:: Response is Successful : ${response.body()?.message}}"
            )
            onNavigate()

        } else {
            Log.d(
                "AddCourse",
                "update section: Response is NOT Successful: ${response.errorBody()?.string()}"
            )
        }
        if (onFailed != null) {
            onFailed()
        }
    }
}

fun deleteSection(
    lifecycleOwner: LifecycleOwner,
    teacherModel: TeacherModel,
    sectionId: Int,
    onFailed: (() -> Unit)?,
    onNavigate: () -> Unit,
) {

    teacherModel.deleteSection(id = sectionId)
    teacherModel.deleteSectionResponse.observe(lifecycleOwner) { response ->
        if (response.isSuccessful) {
            Log.d(
                "AddCourse",
                "delete section: Response is Successful : ${response.body()?.message} -> $sectionId}"
            )
            onNavigate()
        } else {
            Log.d(
                "AddCourse",
                "addSection: Response is NOT Successful: ${response.errorBody()?.string()}"
            )
            if (onFailed != null) {
                onFailed()
            }
        }
    }
}

fun addVideo(
    lifecycleOwner: LifecycleOwner,
    teacherModel: TeacherModel,
    addVideoFields: AddVideoFields,
    onFailed: (() -> Unit)?,
    onNavigate: () -> Unit,
) {
    Log.d("AddCourse", "AddVideoScreen:${addVideoFields.videoUrl} ")
    teacherModel.addVideo(
        sectionId = addVideoFields.section_id,
        title = addVideoFields.title,
        visible = addVideoFields.visible,
        videoUri = addVideoFields.videoUrl
    )

    teacherModel.addVideoResponse.observe(lifecycleOwner) { response ->
        if (response.isSuccessful) {
            Log.d(
                "AddCourse",
                "addVideo: Response is Successful : ${response.body()?.message}}"
            )
            onNavigate()
        } else {
            Log.d(
                "AddCourse",
                "addVideo: Response is NOT : ${response.errorBody()?.string()}"
            )
        }
        if (onFailed != null) {
            onFailed()
        }
    }
}

fun updateVideo(
    lifecycleOwner: LifecycleOwner,
    teacherModel: TeacherModel,
    addVideoFields: AddVideoFields,
    onFailed: (() -> Unit)?,
    onNavigate: () -> Unit,
) {
    Log.d("AddCourse", "AddVideoScreen:${addVideoFields} ")
    teacherModel.updateVideo(
        id = addVideoFields.videoId!!,
        title = addVideoFields.title,
        visible = addVideoFields.visible,
        videoUri = addVideoFields.videoUrl,
        sectionId = addVideoFields.section_id
    )

    teacherModel.updateVideoResponse.observe(lifecycleOwner) { response ->
        when(response.status.value){
            in 200..299 -> {
                Log.d(
                    "AddCourse",
                    "updateCourse: Response is Successful: ${response.status.description}}"
                )
                onNavigate()
            }


            else -> {
                Log.d(
                    "AddCourse",
                    "updateCourse: Response is Not Successful: : ${response.status.description}}"
                )
                if (onFailed != null) {
                    onFailed()
                }
            }
        }
    }
}

fun deleteVideo(
    lifecycleOwner: LifecycleOwner,
    teacherModel: TeacherModel,
    videoId: Int,
    onFailed: (() -> Unit)?,
    onNavigate: () -> Unit,
) {

    teacherModel.deleteVideo(id = videoId)
    teacherModel.deleteVideoResponse.observe(lifecycleOwner) { response ->
        if (response.isSuccessful) {
            Log.d(
                "AddCourse",
                "delete section: Response is Successful : ${response.body()?.message} -> $videoId}"
            )
            onNavigate()
        } else {
            Log.d(
                "AddCourse",
                "addSection: Response is NOT Successful: ${response.errorBody()?.string()}"
            )
            if (onFailed != null) {
                onFailed()
            }
        }
    }
}

fun addFile(
    lifecycleOwner: LifecycleOwner,
    teacherModel: TeacherModel,
    addFileFields: AddFileFields,
    onFailed: (() -> Unit)?,
    onNavigate: () -> Unit
) {
    Log.d("AddCourse", "AddFile:${addFileFields.fileUri} ")
    teacherModel.addFile(
        sectionId = addFileFields.sectionId,
        fileUri = addFileFields.fileUri
    )

    teacherModel.addFileResponse.observe(lifecycleOwner) { response ->
        if (response.isSuccessful) {
            Log.d(
                "AddCourse",
                "addFile: Response is Successful : ${response.body()?.message}}"
            )
            onNavigate()
        } else {
            Log.d(
                "AddCourse",
                "addFile: Response is NOT : ${response.errorBody()?.string()}"
            )
            if (onFailed != null) {
                onFailed()
            }
        }
    }
}

fun updateFile(
    lifecycleOwner: LifecycleOwner,
    teacherModel: TeacherModel,
    addFileFields: AddFileFields,
    onFailed: (() -> Unit)?,
    onNavigate: () -> Unit
) {
    Log.d("AddCourse", "AddFile:${addFileFields} ")
    teacherModel.updateFile(
        id = addFileFields.fileId!!,
        fileUri = addFileFields.fileUri,
        sectionId = addFileFields.sectionId
    )

    teacherModel.updateFileResponse.observe(lifecycleOwner) { response ->
        when(response.status.value){
            in 200..299 -> {
                Log.d(
                    "AddCourse",
                    "updateCourse: Response is Successful: ${response.status.description}}"
                )
                onNavigate()
            }

            else -> {
                Log.d(
                    "AddCourse",
                    "updateCourse: Response is Not Successful: : ${response.status.description}}"
                )
                if (onFailed != null) {
                    onFailed()
                }
            }
        }
    }
}

fun deleteFile(
    lifecycleOwner: LifecycleOwner,
    teacherModel: TeacherModel,
    fileId: Int,
    onFailed: (() -> Unit)?,
    onNavigate: () -> Unit,
) {

    teacherModel.deleteFile(id = fileId)
    teacherModel.deleteFileResponse.observe(lifecycleOwner) { response ->
        if (response.isSuccessful) {
            Log.d(
                "AddCourse",
                "delete section: Response is Successful : ${response.body()?.message} -> $fileId}"
            )
            onNavigate()
        } else {
            Log.d(
                "AddCourse",
                "addSection: Response is NOT Successful: ${response.errorBody()?.string()}"
            )
            if (onFailed != null) {
                onFailed()
            }
        }
    }
}