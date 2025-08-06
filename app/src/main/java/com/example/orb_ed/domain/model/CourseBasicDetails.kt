package com.example.orb_ed.domain.model

data class CourseBasicDetails(
    val id: String,
    val title: String,
    val description: String,
    val progress: Float,
    val teacherName: String,
    val teacherProfilePicture: String?,
    val teacherQualification: String?,
    val startDate: String,
    val endDate: String,
    val totalHours: String,
    val purchaseStatus: String,
    val programName: String,
    val specializationName: String,
    val isPurchased: Boolean,
    val isFree: Boolean
)
