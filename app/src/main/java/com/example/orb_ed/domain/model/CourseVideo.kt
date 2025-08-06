package com.example.orb_ed.domain.model

data class CourseVideo(
    val id: Int,
    val position: Int,
    val name: String,
    val durationSeconds: Int,
    val durationFormatted: String,
    val isCompleted: Boolean,
    val completionPercentage: Int,
    val lastWatchedAt: String?,
    val completionStatus: String,
    val isVideoLocked: Boolean
)
