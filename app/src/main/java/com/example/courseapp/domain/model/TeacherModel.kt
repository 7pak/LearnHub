package com.example.courseapp.domain.model

import android.app.Application
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.courseapp.GET_COURSE_ID
import com.example.courseapp.GET_SECTION_ID
import com.example.courseapp.common.Constants
import com.example.courseapp.data.remote.AddCourseResponse
import com.example.courseapp.data.remote.AddFileResponse
import com.example.courseapp.data.remote.AddSectionResponse
import com.example.courseapp.data.remote.AddVideoResponse
import com.example.courseapp.data.remote.StatusResponse
import com.example.courseapp.data.remote.teacher_get_dto.CourseData
import com.example.courseapp.data.remote.teacher_get_dto.FileData
import com.example.courseapp.data.remote.teacher_get_dto.ProfileData
import com.example.courseapp.data.remote.teacher_get_dto.SectionData
import com.example.courseapp.data.remote.teacher_get_dto.VideoData
import com.example.courseapp.data.remote.teacher_post_dto.AddSectionFields
import com.example.courseapp.domain.repository.TeacherRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class TeacherModel @Inject constructor(
    private val application: Application,
    private val teacherRepositoryImp: TeacherRepositoryImp,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    private var _deleteAccountResponse = MutableLiveData<Response<StatusResponse>>()
    val deleteAccountResponse: LiveData<Response<StatusResponse>> = _deleteAccountResponse

    private var _addCourseResponse = MutableLiveData<Response<AddCourseResponse>>()
    val addCourseResponse: LiveData<Response<AddCourseResponse>> = _addCourseResponse

    private var _updateCourseResponse = MutableLiveData<String>()
    val updateCourseResponse: LiveData<String> = _updateCourseResponse
    private var _deleteCourseResponse = MutableLiveData<Response<StatusResponse>>()
    val deleteCourseResponse: LiveData<Response<StatusResponse>> = _deleteCourseResponse


    private var _addSectionResponse = MutableLiveData<Response<AddSectionResponse>>()
    val addSectionResponse: LiveData<Response<AddSectionResponse>> = _addSectionResponse

    private var _updateSectionResponse = MutableLiveData<Response<StatusResponse>>()
    val updateSectionResponse: LiveData<Response<StatusResponse>> = _updateSectionResponse
    private var _deleteSectionResponse = MutableLiveData<Response<StatusResponse>>()
    val deleteSectionResponse: LiveData<Response<StatusResponse>> = _deleteSectionResponse


    private var _addVideoResponse = MutableLiveData<Response<AddVideoResponse>>()
    val addVideoResponse: LiveData<Response<AddVideoResponse>> = _addVideoResponse

    private var _updateVideoResponse = MutableLiveData<Response<StatusResponse>>()
    val updateVideoResponse: LiveData<Response<StatusResponse>> = _updateVideoResponse
    private var _deleteVideoResponse = MutableLiveData<Response<StatusResponse>>()
    val deleteVideoResponse: LiveData<Response<StatusResponse>> = _deleteVideoResponse


    private var _addFileResponse = MutableLiveData<Response<AddFileResponse>>()
    val addFileResponse: LiveData<Response<AddFileResponse>> = _addFileResponse

    private var _updateFileResponse = MutableLiveData<Response<StatusResponse>>()
    val updateFileResponse: LiveData<Response<StatusResponse>> = _updateFileResponse
    private var _deleteFileResponse = MutableLiveData<Response<StatusResponse>>()
    val deleteFileResponse: LiveData<Response<StatusResponse>> = _deleteFileResponse


    /////////////////////////////////////////////////////////////////////////////////////////////////

    private var _profileInfo = MutableStateFlow<ProfileData?>(null)
    val profileInfo: StateFlow<ProfileData?> = _profileInfo

    private var _courses = MutableStateFlow<List<CourseData>>(emptyList())
    val courses: StateFlow<List<CourseData>> = _courses

    private var _sections = MutableStateFlow<List<SectionData>>(emptyList())
    val sections: StateFlow<List<SectionData>> = _sections

    private var _videos = MutableStateFlow<List<VideoData>>(emptyList())
    val videos: StateFlow<List<VideoData>> = _videos

    private var _file = MutableStateFlow<List<FileData>>(emptyList())
    val file: StateFlow<List<FileData>> = _file


    var currentCourseId by mutableStateOf(-1)

    var currentSectionId by mutableStateOf(-1)

    init {
        fetchProfileInfo()

        fetchAllCourses()

        val courseIdString = savedStateHandle.get<String>(GET_COURSE_ID)
        val courseId = courseIdString?.toIntOrNull() ?: -1
        currentCourseId = courseId
        fetchAllSections(id = currentCourseId)

        val sectionIdString = savedStateHandle.get<String>(GET_SECTION_ID)
        val sectionId = sectionIdString?.toIntOrNull() ?: -1
        currentSectionId = sectionId

    }
    //**********************************************************************************************\\

    fun deleteAccount() {
        viewModelScope.launch {
            _deleteAccountResponse.value = teacherRepositoryImp.deleteAccount()
        }
    }

    fun addCourse(
        category_id: Int,
        description: String,
        price: String,
        title: String,
        photoUri: Uri?
    ) {
        Log.d("AddCourse", "File URI: $photoUri")


        viewModelScope.launch {


            val externalCacheDir = application.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

            val newFileName = Constants.COURSE_IMAGE_NAME
            val file = File(externalCacheDir, newFileName)

            try {
                withContext(Dispatchers.IO) {
                    val inputStream = application.contentResolver.openInputStream(photoUri!!)
                    val outputStream = FileOutputStream(file)
                    inputStream?.use { input ->
                        outputStream.use { output ->
                            input.copyTo(output)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.d("ErrorTag", "input output streams error: ${e.localizedMessage} ")
            }

            //Log.d("FilePath", "File path: ${file.absolutePath}")

            val photoPart = try {
                MultipartBody.Part.createFormData(
                    name = "photo",  // This is the key for the video file in your server's API
                    filename = file.name,
                    body = file.asRequestBody("image/*".toMediaTypeOrNull())
                )
            } catch (e: Exception) {
                Log.d("ErrorTag", "photo part is null: ${e.localizedMessage}")
                null
            }

            _addCourseResponse.value = teacherRepositoryImp.addCourse(
                category_id = category_id,
                description = description,
                price = price,
                title = title,
                photoPart = photoPart
            )
        }
    }

    fun updateCourse(
        id: Int,
        category_id: Int,
        description: String,
        price: String,
        title: String,
        photoUri: Uri?
    ) {
        Log.d("AddCourse", "File URI: $photoUri")

        viewModelScope.launch {


            val externalCacheDir = application.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

            val newFileName = Constants.COURSE_IMAGE_NAME
            val file = File(externalCacheDir, newFileName)

            try {
                withContext(Dispatchers.IO) {
                    val inputStream = application.contentResolver.openInputStream(photoUri!!)
                    val outputStream = FileOutputStream(file)
                    inputStream?.use { input ->
                        outputStream.use { output ->
                            input.copyTo(output)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.d("ErrorTag", "input output streams error: ${e.localizedMessage} ")
            }

            //Log.d("FilePath", "File path: ${file.absolutePath}")

            val photoPart = try {
                MultipartBody.Part.createFormData(
                    name = "photo",
                    filename = file.name,
                    body = file.asRequestBody("image/*".toMediaTypeOrNull())
                )
            } catch (e: Exception) {
                Log.d("ErrorTag", "photo part is null: ${e.localizedMessage}")
                null
            }

            _updateCourseResponse.value = teacherRepositoryImp.updateCourse(
                id = id,
                category_id = category_id,
                description = description,
                price = price,
                title = title,
                photoPart = file
            )
        }
    }

    fun deleteCourse(
        id: Int,
    ) {
        viewModelScope.launch {
            _deleteCourseResponse.value = teacherRepositoryImp.deleteCourse(
                id = id
            )
        }
    }


    fun addSection(
        addSectionFields: AddSectionFields
    ) {
        viewModelScope.launch {
            _addSectionResponse.value = teacherRepositoryImp.addSection(addSectionFields)
        }
    }

    fun updateSection(
        id: Int,
        title: String
    ) {
        viewModelScope.launch {
            _updateSectionResponse.value = teacherRepositoryImp.updateSection(
                id = id,
                addSectionFields = AddSectionFields(title = title)
            )
        }
    }

    fun deleteSection(
        id: Int,
    ) {
        viewModelScope.launch {
            _deleteSectionResponse.value = teacherRepositoryImp.deleteSection(
                id = id
            )
        }
    }


    fun addVideo(
        sectionId: Int,
        title: String,
        visible: Boolean,
        videoUri: Uri?
    ) {
        viewModelScope.launch {
            val externalCacheDir = application.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

            val newFileName = Constants.VIDEO_NAME
            val file = File(externalCacheDir, newFileName)

            try {
                withContext(Dispatchers.IO) {
                    val inputStream = application.contentResolver.openInputStream(videoUri!!)
                    val outputStream = FileOutputStream(file)
                    inputStream?.use { input ->
                        outputStream.use { output ->
                            input.copyTo(output)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.d("ErrorTag", "error while upload the video in file: ${e.localizedMessage}")
            }

            val videoPart = try {
                MultipartBody.Part.createFormData(
                    name = "videoUrl",  // This is the key for the video file in your server's API
                    filename = file.name,
                    body = file.asRequestBody(contentType = "video/*".toMediaTypeOrNull())
                )
            } catch (e: Exception) {
                Log.d("ErrorTag", "video part is null: ${e.localizedMessage}")
                null
            }


            _addVideoResponse.value = teacherRepositoryImp.addVideo(
                sectionId = sectionId,
                title = title,
                visible = if (visible) 0 else 1,
                videoUrl = videoPart
            )
        }
    }

    fun updateVideo(
        id: Int,
        sectionId: Int,
        title: String,
        visible: Boolean,
        videoUri: Uri?
    ) {
        viewModelScope.launch {
            val externalCacheDir = application.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

            val newFileName = Constants.VIDEO_NAME
            val file = File(externalCacheDir, newFileName)

            try {
                withContext(Dispatchers.IO) {
                    val inputStream = application.contentResolver.openInputStream(videoUri!!)
                    val outputStream = FileOutputStream(file)
                    inputStream?.use { input ->
                        outputStream.use { output ->
                            input.copyTo(output)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.d("ErrorTag", "error while upload the video in file: ${e.localizedMessage}")
            }

            val videoPart = try {
                MultipartBody.Part.createFormData(
                    name = "videoUrl",  // This is the key for the video file in your server's API
                    filename = file.name,
                    body = file.asRequestBody(contentType = "video/*".toMediaTypeOrNull())
                )
            } catch (e: Exception) {
                Log.d("ErrorTag", "video part is null: ${e.localizedMessage}")
                null
            }


            _updateVideoResponse.value = teacherRepositoryImp.updateVideo(
                id = id,
                sectionId = sectionId,
                title = title,
                visible = if (visible) 0 else 1,
                videoUrl = videoPart
            )
        }
    }

    fun deleteVideo(
        id: Int,
    ) {
        viewModelScope.launch {
            _deleteVideoResponse.value = teacherRepositoryImp.deleteVideo(
                id = id
            )
        }
    }

    fun addFile(
        sectionId: Int,
        fileUri: Uri?,
    ) {
        viewModelScope.launch {
            val externalCacheDir = application.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)

            val newFileName = Constants.PDF_FILE_NAME
            val file = File(externalCacheDir, newFileName)

            try {
                withContext(Dispatchers.IO) {
                    val inputStream = application.contentResolver.openInputStream(fileUri!!)
                    val outputStream = FileOutputStream(file)
                    inputStream?.use { input ->
                        outputStream.use { output ->
                            input.copyTo(output)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.d("ErrorTag", "error while upload the video in file: ${e.localizedMessage}")
            }

            val filePart = MultipartBody.Part.createFormData(
                name = "fileUrl",  // This is the key for the video file in your server's API
                filename = file.name,
                body = file.asRequestBody("application/pdf".toMediaTypeOrNull())
            )

            _addFileResponse.value = teacherRepositoryImp.addFile(
                sectionId = sectionId,
                filePart = filePart
            )
        }
    }

    fun updateFile(
        id: Int,
        sectionId: Int,
        fileUri: Uri?,
    ) {
        viewModelScope.launch {
            val externalCacheDir = application.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)

            val newFileName = Constants.PDF_FILE_NAME
            val file = File(externalCacheDir, newFileName)

            try {
                withContext(Dispatchers.IO) {
                    val inputStream = application.contentResolver.openInputStream(fileUri!!)
                    val outputStream = FileOutputStream(file)
                    inputStream?.use { input ->
                        outputStream.use { output ->
                            input.copyTo(output)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.d("ErrorTag", "error while upload the video in file: ${e.localizedMessage}")
            }

            val filePart = MultipartBody.Part.createFormData(
                name = "fileUrl",  // This is the key for the video file in your server's API
                filename = file.name,
                body = file.asRequestBody("application/pdf".toMediaTypeOrNull())
            )

            _updateFileResponse.value = teacherRepositoryImp.updateFile(
                id = id,
                sectionId = sectionId,
                filePart = filePart
            )
        }
    }

    fun deleteFile(
        id: Int,
    ) {
        viewModelScope.launch {
            _deleteFileResponse.value = teacherRepositoryImp.deleteFile(
                id = id
            )
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    fun fetchProfileInfo() {
        viewModelScope.launch {
            val response = teacherRepositoryImp.getProfileInfo()
            if (response.isSuccessful) {
                response.body()?.data?.let {
                    _profileInfo.value = it
                }
            }
        }
    }

    fun fetchAllCourses() {
        viewModelScope.launch {
            val response = teacherRepositoryImp.getCourses()
            if (response.isSuccessful) {
                response.body()?.data?.let {
                    _courses.value = it
                }
            }
        }
    }

    fun fetchAllSections(
        id: Int
    ) {
        viewModelScope.launch {
            val response = teacherRepositoryImp.getSections(id = id)
            if (response.isSuccessful) {
                response.body()?.data?.let {
                    _sections.value = it
                }
            }
        }
    }

    fun fetchAllVideos(
        id: Int
    ) {
        viewModelScope.launch {
            val response = teacherRepositoryImp.getVideos(id = id)
            if (response.isSuccessful) {
                response.body()?.data?.let {
                    _videos.value = it
                }
            }
        }
    }

    fun fetchFile(
        id: Int
    ) {
        viewModelScope.launch {
            val response = teacherRepositoryImp.getFile(id = id)
            if (response.isSuccessful) {
                response.body()?.data?.let {
                    _file.value = it
                }
            }
        }
    }
}