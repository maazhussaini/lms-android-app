package com.example.orb_ed.data.remote.dto

import com.example.orb_ed.domain.model.Course
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EnrolledCourseDto(
    @SerialName("enrollment_id")
    val enrollmentId: Int,
    @SerialName("course_id")
    val courseId: Int,
    @SerialName("course_name")
    val courseName: String,
    @SerialName("start_date")
    val startDate: String,
    @SerialName("end_date")
    val endDate: String,
    @SerialName("specialization_id")
    val specializationId: Int,
    @SerialName("specialization_name")
    val specializationName: String,
    @SerialName("program_id")
    val programId: Int,
    @SerialName("program_name")
    val programName: String,
    @SerialName("teacher_name")
    val teacherName: String,
    @SerialName("teacher_qualification")
    val teacherQualification: String?,
    @SerialName("profile_picture_url")
    val teacherProfilePicture: String?,
    @SerialName("course_total_hours")
    val totalHours: String,
    @SerialName("overall_progress_percentage")
    val progressPercentage: Float
) {
    fun toCourse(): Course {
        return Course(
            id = courseId.toString(),
            title = courseName,
            description = "$programName - $specializationName\nDuration: $startDate to $endDate\nTotal Hours: $totalHours",
            thumbnailUrl = teacherProfilePicture ?: "",
            progress = progressPercentage / 100f, // Convert to 0.0-1.0 range
            isEnrolled = true,
            programName = programName,
            duration = "$startDate - $endDate",
            teacherName = teacherName,
            price = "Enrolled",
            teacherQualification = teacherQualification
        )
    }
}

@Serializable
data class EnrolledCoursesData(
    @SerialName("items")
    val items: List<EnrolledCourseDto>,
    @SerialName("pagination")
    val pagination: Pagination
)

typealias EnrolledCoursesResponse = ApiResponse<EnrolledCoursesData>
