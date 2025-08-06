package com.example.orb_ed.data.remote.dto

import com.example.orb_ed.domain.model.CourseVideo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CourseVideoDto(
    @SerialName("course_video_id")
    val id: Int,
    @SerialName("position")
    val position: Int,
    @SerialName("video_name")
    val name: String,
    @SerialName("duration_seconds")
    val durationSeconds: Int,
    @SerialName("duration_formatted")
    val durationFormatted: String,
    @SerialName("is_completed")
    val isCompleted: Boolean?,
    @SerialName("completion_percentage")
    val completionPercentage: Int?,
    @SerialName("last_watched_at")
    val lastWatchedAt: String?,
    @SerialName("completion_status")
    val completionStatus: String,
    @SerialName("is_video_locked")
    val isVideoLocked: Boolean
) {
    fun toCourseVideo(): CourseVideo {
        return CourseVideo(
            id = id,
            position = position,
            name = name,
            durationSeconds = durationSeconds,
            durationFormatted = durationFormatted,
            isCompleted = isCompleted ?: false,
            completionPercentage = completionPercentage ?: 0,
            lastWatchedAt = lastWatchedAt,
            completionStatus = completionStatus,
            isVideoLocked = isVideoLocked
        )
    }
}

@Serializable
data class CourseVideosData(
    @SerialName("items")
    val items: List<CourseVideoDto>,
    @SerialName("pagination")
    val pagination: Pagination
)

typealias CourseVideosResponse = ApiResponse<CourseVideosData>
