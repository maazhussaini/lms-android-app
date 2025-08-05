package com.example.orb_ed.data.remote.dto

import com.example.orb_ed.domain.model.Course
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiscoveredCourseDto(
    @SerialName("course_id")
    val id: Int,
    @SerialName("course_name")
    val name: String,
    @SerialName("start_date")
    val startDate: String,
    @SerialName("end_date")
    val endDate: String,
    @SerialName("program_name")
    val programName: String,
    @SerialName("teacher_name")
    val teacherName: String,
    @SerialName("course_total_hours")
    val totalHours: String,
    @SerialName("profile_picture_url")
    val teacherProfilePic: String?,
    @SerialName("teacher_qualification")
    val teacherQualification: String?,
    @SerialName("program_id")
    val programId: Int,
    @SerialName("purchase_status")
    val purchaseStatus: String,
    @SerialName("is_purchased")
    val isPurchased: Boolean,
    @SerialName("is_free")
    val isFree: Boolean
) {
    fun toDomain(): Course = Course(
        id = id.toString(),
        title = name,
        description = "$programName • $teacherName • $startDate - $endDate",
        thumbnailUrl = "", // You might want to add a default course image
        progress = if (isPurchased) 0f else -1f, // -1 means not enrolled
        isEnrolled = isPurchased,
        programName = programName,
        duration = totalHours,
        teacherName = teacherName,
        teacherQualification = teacherQualification,
        price = purchaseStatus
    )
}

@Serializable
data class DiscoveredCoursesData(
    @SerialName("items")
    val items: List<DiscoveredCourseDto>,
    @SerialName("pagination")
    val pagination: Pagination
)

typealias DiscoveredCoursesResponse = ApiResponse<DiscoveredCoursesData>
