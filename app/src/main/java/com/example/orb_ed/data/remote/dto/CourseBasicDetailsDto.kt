package com.example.orb_ed.data.remote.dto

import com.example.orb_ed.domain.model.CourseBasicDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CourseBasicDetailsDto(
    @SerialName("course_id")
    val courseId: Int,
    @SerialName("course_name")
    val courseName: String,
    @SerialName("course_description")
    val courseDescription: String?,
    @SerialName("overall_progress_percentage")
    val progressPercentage: Float?,
    @SerialName("teacher_name")
    val teacherName: String,
    @SerialName("profile_picture_url")
    val teacherProfilePicture: String?,
    @SerialName("teacher_qualification")
    val teacherQualification: String?,
    @SerialName("start_date")
    val startDate: String,
    @SerialName("end_date")
    val endDate: String,
    @SerialName("course_total_hours")
    val totalHours: String,
    @SerialName("purchase_status")
    val purchaseStatus: String,
    @SerialName("program_name")
    val programName: String,
    @SerialName("specialization_name")
    val specializationName: String,
    @SerialName("is_purchased")
    val isPurchased: Boolean,
    @SerialName("is_free")
    val isFree: Boolean
) {
    fun toCourseBasicDetails(): CourseBasicDetails {
        return CourseBasicDetails(
            id = courseId.toString(),
            title = courseName,
            description = courseDescription ?: "No description available",
            progress = (progressPercentage ?: 0f) / 100f,
            teacherName = teacherName,
            teacherProfilePicture = teacherProfilePicture,
            teacherQualification = teacherQualification,
            startDate = startDate,
            endDate = endDate,
            totalHours = totalHours,
            purchaseStatus = purchaseStatus,
            programName = programName,
            specializationName = specializationName,
            isPurchased = isPurchased,
            isFree = isFree
        )
    }
}

data class CourseBasicDetailsResponse(
    val data: CourseBasicDetailsDto
)

typealias CourseBasicDetailsApiResponse = ApiResponse<CourseBasicDetailsDto>
