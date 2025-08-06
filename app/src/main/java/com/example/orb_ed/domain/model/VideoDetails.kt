package com.example.orb_ed.domain.model

data class VideoDetails(
    val id: Int,
    val name: String,
    val videoUrl: String,
    val thumbnailUrl: String?,
    val duration: Int,
    val durationFormatted: String,
    val position: Int,
    val bunnyVideoId: String,
    val courseTopicId: Int,
    val courseId: Int,
    val teacherName: String,
    val teacherQualification: String?,
    val profilePictureUrl: String?,
    val nextCourseVideoId: Int?,
    val nextVideoName: String?,
    val nextVideoDuration: Int?,
    val nextVideoDurationFormatted: String?,
    val previousCourseVideoId: Int?,
    val previousVideoName: String?,
    val previousVideoDuration: Int?,
    val previousVideoDurationFormatted: String?
)
