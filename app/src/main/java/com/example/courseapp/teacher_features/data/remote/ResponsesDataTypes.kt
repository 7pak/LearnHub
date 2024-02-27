package com.example.courseapp.teacher_features.data.remote

import com.example.courseapp.common.DataResponse
import com.example.courseapp.teacher_features.data.remote.teacher_get_dto.CourseData
import com.example.courseapp.teacher_features.data.remote.teacher_get_dto.FileData
import com.example.courseapp.teacher_features.data.remote.teacher_get_dto.ProfileData
import com.example.courseapp.teacher_features.data.remote.teacher_get_dto.SectionData
import com.example.courseapp.teacher_features.data.remote.teacher_get_dto.VideoData
import com.example.courseapp.teacher_features.data.remote.teacher_post_dto.AddCourseData
import com.example.courseapp.teacher_features.data.remote.teacher_post_dto.AddFileFields
import com.example.courseapp.teacher_features.data.remote.teacher_post_dto.AddSectionFields
import com.example.courseapp.teacher_features.data.remote.teacher_post_dto.AddVideoFields


typealias AddCourseResponse = DataResponse<AddCourseData>

typealias AddSectionResponse = DataResponse<AddSectionFields>

typealias AddFileResponse = DataResponse<AddFileFields>

typealias AddVideoResponse = DataResponse<AddVideoFields>

///////////////////////////////////////////////////////////

//Get data
typealias GetCourseDataResponse = DataResponse<List<CourseData>>

typealias GetSectionResponse = DataResponse<List<SectionData>>

typealias GetVideoResponse = DataResponse<List<VideoData>>

typealias GetFileResponse = DataResponse<FileData>

typealias GetProfileResponse = DataResponse<ProfileData>