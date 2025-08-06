package com.example.orb_ed.data.remote.dto

import com.example.orb_ed.domain.model.CourseTopic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CourseTopicDto(
    @SerialName("course_topic_id")
    val id: Int,
    @SerialName("course_topic_name")
    val name: String,
    @SerialName("overall_video_lectures")
    val videoLectures: String,
    @SerialName("position")
    val position: Int,
    @SerialName("is_active")
    val isActive: Boolean,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String
) {
    fun toCourseTopic(): CourseTopic {
        return CourseTopic(
            id = id,
            name = name,
            videoLectures = videoLectures,
            position = position,
            isActive = isActive
        )
    }
}

@Serializable
data class CourseTopicsData(
    @SerialName("items")
    val items: List<CourseTopicDto>,
    @SerialName("pagination")
    val pagination: Pagination
)

typealias CourseTopicsResponse = ApiResponse<CourseTopicsData>
