package com.example.orb_ed.presentation.screens.courses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.orb_ed.domain.model.Course
import com.example.orb_ed.domain.usecase.DiscoverCoursesUseCase
import com.example.orb_ed.domain.usecase.course.GetEnrolledCoursesUseCase
import com.example.orb_ed.domain.usecase.course.GetProgramsUseCase
import com.example.orb_ed.domain.usecase.course.GetSpecializationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseDashboardViewModel @Inject constructor(
    private val getProgramsUseCase: GetProgramsUseCase,
    private val getSpecializationsUseCase: GetSpecializationsUseCase,
    private val getEnrolledCoursesUseCase: GetEnrolledCoursesUseCase,
//    private val searchCoursesUseCase: SearchCoursesUseCase,
    private val discoverCoursesUseCase: DiscoverCoursesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CourseDashboardState())
    val state: StateFlow<CourseDashboardState> = _state.asStateFlow()

    private val _effect = Channel<CourseDashboardEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init {
        loadInitialData()
        loadDiscoverCourses()
    }

    fun processIntent(intent: CourseDashboardIntent) {
        when (intent) {
            is CourseDashboardIntent.LoadInitialData -> loadInitialData()
            is CourseDashboardIntent.TabSelected -> onTabSelected(intent.index)
            is CourseDashboardIntent.ProgramSelected -> onProgramSelected(intent.index)
            is CourseDashboardIntent.SpecializationSelected -> onSpecializationSelected(intent.index)
            is CourseDashboardIntent.SearchQueryChanged -> onSearchQueryChanged(intent.query)
            is CourseDashboardIntent.CourseClicked -> onCourseClicked(intent.courseId)
            CourseDashboardIntent.ClearFilters -> onClearFilters()
            is CourseDashboardIntent.NavigateBack -> _effect.trySend(CourseDashboardEffect.NavigateBack)
            CourseDashboardIntent.RefreshData -> {}
            CourseDashboardIntent.LoadDiscoverCourses -> loadDiscoverCourses()
            CourseDashboardIntent.OnSearchClicked -> onSearchClicked()
        }
    }

    private fun loadInitialData() {
        _effect.trySend(CourseDashboardEffect.ShowLoading)

        viewModelScope.launch {
            try {
                // Load programs
                getProgramsUseCase().collectLatest { programs ->
                    _state.update { it.copy(programs = programs) }
                    if (programs.isNotEmpty()) {
                        // Load specializations for the first program by default
//                        loadSpecializations(programs[0].id)
                    }
                }

                // Load enrolled courses
                getEnrolledCoursesUseCase().collectLatest { courses ->
                    _state.update { state ->
                        state.copy(
                            discoverCourses = courses,
                            filteredCourses = applyFilters(
                                courses = courses,
                                searchQuery = state.searchQuery,
                                tabIndex = state.selectedTabIndex,
                                programIndex = state.selectedProgramIndex,
                                specializationIndex = state.selectedSpecializationIndex
                            )
                        )
                    }
                }

                _effect.send(CourseDashboardEffect.HideLoading)
            } catch (e: Exception) {
                _effect.send(CourseDashboardEffect.ShowError(e.message ?: "An error occurred"))
            }
        }
    }

    private fun loadSpecializations(programIndex: Int) {
        viewModelScope.launch {
            try {
                if (programIndex == -1) {
                    loadDiscoverCourses()
                    return@launch
                }
                getSpecializationsUseCase(state.value.programs[programIndex].id).collect { specializations ->
                    _state.update { it.copy(specializations = specializations) }
                    loadDiscoverCourses()
                }
            } catch (e: Exception) {
                _effect.trySend(CourseDashboardEffect.ShowError("Failed to load specializations"))
            }
        }
    }

    private fun onTabSelected(index: Int) {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    selectedTabIndex = index,
                    filteredCourses = applyFilters(
                        courses = state.discoverCourses,
                        searchQuery = state.searchQuery,
                        tabIndex = index,
                        programIndex = state.selectedProgramIndex,
                        specializationIndex = state.selectedSpecializationIndex
                    )
                )
            }
        }
    }

    private fun onProgramSelected(programIndex: Int) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    selectedProgramIndex = programIndex,
                    specializations = if (programIndex == -1) emptyList() else it.specializations,
                )
            }
            loadSpecializations(programIndex)
            /*if (programId >= -1) {

            } else {
                _state.update { state ->
                    state.copy(
                        selectedProgram = programId,
                        selectedSpecialization = -1, // Reset specialization when program changes
                        filteredCourses = applyFilters(
                            courses = state.discoverCourses,
                            searchQuery = state.searchQuery,
                            tabIndex = state.selectedTabIndex,
                            programIndex = programId,
                            specializationIndex = -1
                        )
                    )
                }
            }*/

//            applyFilters()
//            loadDiscoverCourses()
        }
    }

    private fun onSpecializationSelected(index: Int) {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    selectedSpecializationIndex = index,
                    /* filteredCourses = applyFilters(
                         courses = state.discoverCourses,
                         searchQuery = state.searchQuery,
                         tabIndex = state.selectedTabIndex,
                         programIndex = state.selectedProgramIndex,
                         specializationIndex = index
                     )*/
                )
            }
//            applyFilters()
//            if (index == -1)
            loadDiscoverCourses()
        }
    }

    private fun onSearchQueryChanged(query: String) {
        _state.update { it.copy(searchQuery = query) }
//        applyFilters()
        loadDiscoverCourses()
    }

    private fun onSearchClicked() {
        viewModelScope.launch {
            val searchResults = if (_state.value.searchQuery.isBlank()) {
                state.value.discoverCourses
            } else {
//                searchCoursesUseCase(_state.value.searchQuery).first()
            }

            /*_state.update { state ->
                state.copy(
                    filteredCourses = applyFilters(
                        courses = searchResults,
                        searchQuery = _state.value.searchQuery,
                        tabIndex = state.selectedTabIndex,
                        programIndex = state.selectedProgram,
                        specializationIndex = state.selectedSpecialization
                    )
                )
            }*/
        }
    }

    private fun onCourseClicked(courseId: String) {
        _effect.trySend(CourseDashboardEffect.NavigateToCourseDetail(courseId))
    }

    private fun onClearFilters() {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    selectedProgramIndex = -1,
                    selectedSpecializationIndex = -1,
                    searchQuery = "",
                    filteredCourses = applyFilters(
                        courses = state.discoverCourses,
                        searchQuery = "",
                        tabIndex = state.selectedTabIndex,
                        programIndex = -1,
                        specializationIndex = -1
                    )
                )
            }
            _effect.send(CourseDashboardEffect.FiltersCleared)
            // Reload discover courses with cleared filters
            loadDiscoverCourses()
        }
    }

    private fun loadDiscoverCourses() {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isDiscoverLoading = true, discoverError = null) }

                // Get the selected program ID if any
                val programId =
                    _state.value.programs.getOrNull(_state.value.selectedProgramIndex)?.id
                val specializationId =
                    _state.value.specializations.getOrNull(_state.value.selectedSpecializationIndex)?.id

                discoverCoursesUseCase(
                    programId = programId,
                    specializationId = specializationId,
                    searchQuery = _state.value.searchQuery.takeIf { it.isNotBlank() }
                ).collect { courses ->
                    _state.update {
                        it.copy(
                            discoverCourses = courses,
                            isDiscoverLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isDiscoverLoading = false,
                        discoverError = e.message ?: "Failed to load courses"
                    )
                }
                _effect.trySend(CourseDashboardEffect.ShowError("Failed to load discover courses"))
            }
        }
    }

    private suspend fun applyFilters(
        courses: List<Course>,
        searchQuery: String,
        tabIndex: Int,
        programIndex: Int,
        specializationIndex: Int
    ): List<Course> {
        return courses.filter { course ->
            // Filter by tab (All, In Progress, Completed)
            /*val matchesTab = when (tabIndex) {
                1 -> course.progress > 0f && course.progress < 100f // In Progress
                2 -> course.progress >= 100f // Completed
                else -> true // All
            }*/

            // Filter by search query
            val matchesSearch = searchQuery.isBlank() ||
                    course.title.contains(searchQuery, ignoreCase = true) ||
                    course.teacherName.contains(searchQuery, ignoreCase = true) ||
                    course.programName.contains(searchQuery, ignoreCase = true)

            // Filter by program if selected
            val matchesProgram = programIndex == -1 ||
                    state.value.programs.getOrNull(programIndex)?.let { program ->
                        course.programName == program.name
                    } ?: true

            // Filter by specialization if selected
            val matchesSpecialization = specializationIndex == -1 ||
                    state.value.specializations.getOrNull(specializationIndex)
                        ?.let { specialization ->
                            course.programName == specialization.name
                        } ?: true

            /*matchesTab && */matchesSearch && matchesProgram && matchesSpecialization
        }
    }
}
