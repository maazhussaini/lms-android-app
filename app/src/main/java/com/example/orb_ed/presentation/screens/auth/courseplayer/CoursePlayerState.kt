package com.example.orb_ed.presentation.screens.auth.courseplayer

data class CoursePlayerState(
    val profilePictureUrl: String? = null,
    val teacherName: String,
    val teacherDesignation: String,
    val moduleNumber: Int,
    val moduleName: String,
    val lectureNumber: Int,
    val lectureName: String,
    val previousLectureName: String? = null,
    val nextLectureName: String? = null,
    val previousLectureDuration: String? = null,
    val nextLectureDuration: String? = null,
)