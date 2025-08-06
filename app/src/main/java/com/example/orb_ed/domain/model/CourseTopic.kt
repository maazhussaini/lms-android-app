package com.example.orb_ed.domain.model

data class CourseTopic(
    val id: Int,
    val name: String,
    val videoLectures: String,
    val position: Int,
    val isActive: Boolean
)
