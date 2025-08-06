package com.example.orb_ed.presentation.screens.auth.coursedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.orb_ed.domain.usecase.course.GetCourseBasicDetailsUseCase
import com.example.orb_ed.domain.usecase.course.GetCourseModulesUseCase
import com.example.orb_ed.domain.usecase.course.GetCourseTopicsByModuleUseCase
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
    private val getCourseBasicDetailsUseCase: GetCourseBasicDetailsUseCase,
    private val getCourseModulesUseCase: GetCourseModulesUseCase,
    private val getCourseTopicsByModuleUseCase: GetCourseTopicsByModuleUseCase
) : ViewModel() {

    val data = state.toRoute<CourseDetail>()
    private val _state = MutableStateFlow(
        CourseDetailState(
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
        getCourseBasicDetailsUseCase.invoke(data.courseId).collect { result ->
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

        getCourseModulesUseCase.invoke(data.courseId).collect { result ->
            result.onSuccess { modules ->
                _state.update { currentState ->
                    currentState.copy(
                        listOfModules = modules
                    )
                }
            }.onFailure { exception ->
                // Handle error state, you might want to update the UI to show an error
                // For now, we'll just log the error
                println("Error fetching course modules: ${exception.message}")
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
        _state.update {
            it.copy(
                selectedModule = moduleId,
                listOfTopics = if (moduleId > 0) it.listOfTopics else emptyList()
            )
        }

        if (moduleId > 0)
            viewModelScope.launch {
                getCourseTopicsByModuleUseCase.invoke(moduleId).collect { result ->
                    result.onSuccess { topics ->
                        _state.update { currentState ->
                            currentState.copy(
                                listOfTopics = topics
                            )
                        }
                    }.onFailure { exception ->
                        // Handle error state, you might want to update the UI to show an error
                        // For now, we'll just log the error
                        println("Error fetching course topics: ${exception.message}")
                    }
                }
            }
    }

    fun onTopicSelected(topicId: Int) {
        _state.update {
            it.copy(
                selectedTopic = topicId,
                listOfVideoLectures = if (topicId > 0) it.listOfVideoLectures else emptyList()
            )
        }

    }
}