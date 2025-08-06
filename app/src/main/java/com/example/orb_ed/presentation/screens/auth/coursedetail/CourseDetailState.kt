package com.example.orb_ed.presentation.screens.auth.coursedetail

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
    val listOfModules: List<Module> = emptyList(),
    val listOfTopics: List<Topic> = emptyList(),
    val listOfVideoLectures: List<VideoLecture> = emptyList()
)

data class Module(
    val moduleId: Int,
    val moduleName: String,
    val noOfTopics: Int,
    val noOfVideoLectures: Int
)

data class Topic(
    val topicId: Int,
    val topicName: String,
    val noOfVideoLectures: Int
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
