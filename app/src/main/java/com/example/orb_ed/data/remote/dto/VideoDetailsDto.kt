package com.example.orb_ed.data.remote.dto

import com.example.orb_ed.domain.model.VideoDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoDetailsDto(
    @SerialName("course_video_id")
    val id: Int,
    @SerialName("video_name")
    val name: String,
    @SerialName("video_url")
    val videoUrl: String,
    @SerialName("thumbnail_url")
    val thumbnailUrl: String?,
    @SerialName("duration")
    val duration: Int,
    @SerialName("duration_formatted")
    val durationFormatted: String,
    @SerialName("position")
    val position: Int,
    @SerialName("bunny_video_id")
    val bunnyVideoId: String,
    @SerialName("course_topic_id")
    val courseTopicId: Int,
    @SerialName("course_id")
    val courseId: Int,
    @SerialName("teacher_name")
    val teacherName: String,
    @SerialName("teacher_qualification")
    val teacherQualification: String?,
    @SerialName("profile_picture_url")
    val profilePictureUrl: String?,
    @SerialName("next_course_video_id")
    val nextCourseVideoId: Int?,
    @SerialName("next_video_name")
    val nextVideoName: String?,
    @SerialName("next_video_duration")
    val nextVideoDuration: Int?,
    @SerialName("next_video_duration_formatted")
    val nextVideoDurationFormatted: String?,
    @SerialName("previous_course_video_id")
    val previousCourseVideoId: Int?,
    @SerialName("previous_video_name")
    val previousVideoName: String?,
    @SerialName("previous_video_duration")
    val previousVideoDuration: Int?,
    @SerialName("previous_video_duration_formatted")
    val previousVideoDurationFormatted: String?
) {
    fun toVideoDetails(): VideoDetails {
        return VideoDetails(
            id = id,
            name = name,
            videoUrl = videoUrl,
            thumbnailUrl = thumbnailUrl,
            duration = duration,
            durationFormatted = durationFormatted,
            position = position,
            bunnyVideoId = bunnyVideoId,
            courseTopicId = courseTopicId,
            courseId = courseId,
            teacherName = teacherName,
            teacherQualification = teacherQualification,
            profilePictureUrl = profilePictureUrl,
            nextCourseVideoId = nextCourseVideoId,
            nextVideoName = nextVideoName,
            nextVideoDuration = nextVideoDuration,
            nextVideoDurationFormatted = nextVideoDurationFormatted,
            previousCourseVideoId = previousCourseVideoId,
            previousVideoName = previousVideoName,
            previousVideoDuration = previousVideoDuration,
            previousVideoDurationFormatted = previousVideoDurationFormatted
        )
    }
}

typealias VideoDetailsResponse = ApiResponse<VideoDetailsDto>
