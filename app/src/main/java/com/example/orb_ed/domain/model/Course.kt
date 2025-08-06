package com.example.orb_ed.domain.model

data class Course(
    val id: Int,
    val title: String,
    val description: String,
    val thumbnailUrl: String,
    val progress: Float = 0f,
    val isEnrolled: Boolean = false,
    val programName: String = "",
    val duration: String = "",
    val teacherName: String = "",
    val price: String = "",
    val teacherQualification: String? = null,
    val imageResId: Int = 0
)

data class Program(
    val id: Int,
    val name: String,
    val thumbnailUrl: String? = null,
)

data class Specialization(
    val id: Int,
    val name: String,
    val programId: Int,
    val thumbnailUrl: String? = null
)
