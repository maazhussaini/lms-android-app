package com.example.orb_ed.presentation.screens.auth.coursedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.orb_ed.domain.usecase.course.GetCourseBasicDetailsUseCase
import com.example.orb_ed.presentation.navigation.CourseDetail
import com.example.orb_ed.util.Constants.VIDEO_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CourseDetailViewModel @Inject constructor(
    state: SavedStateHandle,
    private val getCourseBasicDetails: GetCourseBasicDetailsUseCase,
) : ViewModel() {

    val data = state.toRoute<CourseDetail>()
    private val _state = MutableStateFlow(
        CourseDetailState(
            listOfModules = listOf(
                Module(1, "Module 1", 5, 3),
                Module(2, "Module 2", 4, 2),
                Module(3, "Module 3", 6, 4),
                Module(4, "Module 4", 3, 2),
                Module(5, "Module 5", 4, 3),
                Module(6, "Module 6", 5, 3),
                Module(7, "Module 7", 4, 2),
                Module(8, "Module 8", 5, 3),
                Module(9, "Module 9", 4, 2),
                Module(10, "Module 10", 6, 4),
                Module(11, "Module 11", 3, 2),
                Module(12, "Module 12", 4, 3),
                Module(13, "Module 13", 5, 3),
                Module(14, "Module 14", 4, 2),
                Module(15, "Module 15", 6, 4),
            ),
            listOfTopics = listOf(
                Topic(1, "Introduction", 3),
                Topic(2, "Basics", 5),
                Topic(3, "Advanced", 4),
                Topic(4, "Final Project", 2),
                Topic(5, "Review", 1),
                Topic(6, "Certification", 1),
                Topic(7, "Q&A", 2),
                Topic(8, "Bonus Content", 1),
                Topic(9, "Community", 1),
                Topic(10, "Additional Resources", 1),
                Topic(11, "Career Support", 1),
                Topic(12, "Feedback", 1),
                Topic(13, "Next Steps", 1),
                Topic(14, "Graduation", 1),
                Topic(15, "Alumni Network", 1)
            ),
            listOfVideoLectures = listOf(
                VideoLecture(1, "Video 1", "15 Mins", VIDEO_ID, "Completed", false),
                VideoLecture(2, "Video 2", "15 Mins", VIDEO_ID, "Pending", true),
                VideoLecture(3, "Video 3", "15 Mins", VIDEO_ID, "Incomplete", true),
            ),
        )
    )
    val state: StateFlow<CourseDetailState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getCourseBasicDetails()
        }
    }

    suspend fun getCourseBasicDetails() = withContext(Dispatchers.IO) {
        getCourseBasicDetails.invoke(data.courseId).collect { result ->
            result.onSuccess { courseDetails ->
                _state.update { currentState ->
                    currentState.copy(
                        profilePictureUrl = courseDetails.teacherProfilePicture,
                        courseName = courseDetails.title,
                        courseDescription = courseDetails.description,
                        courseDuration = "${courseDetails.startDate} - ${courseDetails.endDate}",
                        teacherName = courseDetails.teacherName,
                        coursePrice = courseDetails.purchaseStatus,
                        progress = courseDetails.progress,
                        programName = courseDetails.programName,
                        specializationName = courseDetails.specializationName
                    )
                }
            }.onFailure { exception ->
                // Handle error state, you might want to update the UI to show an error
                // For now, we'll just log the error
                println("Error fetching course details: ${exception.message}")
            }
        }
    }

    fun processIntent(intent: CourseDetailIntent) {
        when (intent) {
            is CourseDetailIntent.ModuleSelected -> onModuleSelected(intent.moduleId)
            is CourseDetailIntent.TopicSelected -> onTopicSelected(intent.topicId)
        }
    }

    fun onModuleSelected(moduleId: Int) {
        _state.update { it.copy(selectedModule = moduleId) }

    }

    fun onTopicSelected(topicId: Int) {
        _state.update { it.copy(selectedTopic = topicId) }

    }
}