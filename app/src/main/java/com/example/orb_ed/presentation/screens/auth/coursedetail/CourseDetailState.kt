package com.example.orb_ed.presentation.screens.auth.coursedetail

import com.example.orb_ed.domain.model.CourseModule
import com.example.orb_ed.domain.model.CourseTopic

data class CourseDetailState(
    val profilePictureUrl: String? = null,
    val courseName: String = "",
    val courseDescription: String = "",
    val courseDuration: String = "",
    val coursePrice: String = "",
    val teacherName: String = "",
    val progress: Float = 0f,
    val programName: String = "",
    val specializationName: String = "",
    val selectedTopic: Int = 0,
    val selectedModule: Int = 0,
    val listOfModules: List<CourseModule> = emptyList(),
    val listOfTopics: List<CourseTopic> = emptyList(),
    val listOfVideoLectures: List<VideoLecture> = emptyList()
)

data class VideoLecture(
    val videoLectureId: Int,
    val videoLectureName: String,
    val videoLectureDuration: String,
    val videoId: String,
    val videoLectureStatus: String,
    val videoLectureIsLocked: Boolean
)

data class ItemModel(
    val itemType: ItemType,
    val title: String,
    val subTitle: String,
    val completionStatus: String? = null,
    val isLocked: Boolean? = null
)

sealed class ItemType {
    data class module(val id: Int) : ItemType()
    data class topic(val id: Int) : ItemType()
    data class videoLecture(val id: Int, val videoId: String) : ItemType()
}
