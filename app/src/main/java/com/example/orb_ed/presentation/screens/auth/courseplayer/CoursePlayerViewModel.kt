package com.example.orb_ed.presentation.screens.auth.courseplayer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.orb_ed.domain.usecase.course.GetVideoDetailsByIdUseCase
import com.example.orb_ed.presentation.navigation.CoursePlayer
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
class CoursePlayerViewModel @Inject constructor(
    state: SavedStateHandle,
    private val getVideoDetailsByIdUseCase: GetVideoDetailsByIdUseCase
) : ViewModel() {
    val data = state.toRoute<CoursePlayer>()
    private val _state = MutableStateFlow(
        CoursePlayerState()
    )
    val state: StateFlow<CoursePlayerState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getVideoDetails(data.videoId)
        }
    }

    fun processIntent(intent: CoursePlayerIntent) {
        when (intent) {
            CoursePlayerIntent.OnNextClicked -> {
                _state.value.nextVideoId?.let { nextVideoId ->
                    _state.update { CoursePlayerState() }
                    viewModelScope.launch {
                        getVideoDetails(nextVideoId)
                    }
                }
            }

            CoursePlayerIntent.OnPreviousClicked -> {
                _state.value.previousVideoId?.let { previousVideoId ->
                    _state.update { CoursePlayerState() }
                    viewModelScope.launch {
                        getVideoDetails(previousVideoId)
                    }
                }
            }
        }
    }

    private suspend fun getVideoDetails(videoId: Int) = withContext(Dispatchers.IO) {
        getVideoDetailsByIdUseCase(videoId).collect { result ->
            result.onSuccess { videoDetails ->
                _state.update {
                    it.copy(
                        videoId = videoId,
                        bunnyVideoId = videoDetails.bunnyVideoId,
                        profilePictureUrl = videoDetails.profilePictureUrl,
                        teacherName = videoDetails.teacherName,
                        teacherDesignation = videoDetails.teacherQualification,
                        moduleNumber = null,
                        moduleName = null,
                        lectureNumber = videoDetails.position,
                        lectureName = videoDetails.name,
                        previousLectureName = videoDetails.previousVideoName,
                        nextLectureName = videoDetails.previousVideoDurationFormatted,
                        previousLectureDuration = videoDetails.previousVideoDurationFormatted,
                        nextLectureDuration = videoDetails.nextVideoDurationFormatted,
                        previousVideoId = videoDetails.previousCourseVideoId,
                        nextVideoId = videoDetails.nextCourseVideoId
                    )
                }

            }.onFailure { exception ->
                println("Error fetching video details: ${exception.message}")
            }


        }
    }
}