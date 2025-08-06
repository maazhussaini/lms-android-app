package com.example.orb_ed.presentation.screens.auth.courseplayer

data class CoursePlayerState(
    val bunnyVideoId: String = "",
    val videoId: Int = 0,
    val profilePictureUrl: String? = null,
    val teacherName: String = "",
    val teacherDesignation: String? = null,
    val moduleNumber: Int? = null,
    val moduleName: String? = null,
    val lectureNumber: Int = 0,
    val lectureName: String = "",
    val previousLectureName: String? = null,
    val previousLectureDuration: String? = null,
    val previousVideoId: Int? = null,
    val nextLectureName: String? = null,
    val nextLectureDuration: String? = null,
    val nextVideoId: Int? = null,
)