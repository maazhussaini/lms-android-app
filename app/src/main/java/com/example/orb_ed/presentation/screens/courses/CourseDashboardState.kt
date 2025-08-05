package com.example.orb_ed.presentation.screens.courses

import com.example.orb_ed.domain.model.Course
import com.example.orb_ed.domain.model.Program
import com.example.orb_ed.domain.model.Specialization

/**
 * Represents the UI state for the Course Dashboard screen.
 */
data class CourseDashboardState(
    val selectedTabIndex: Int = 0,
    val programs: List<Program> = emptyList(),
    val selectedProgramIndex: Int = -1,
    val specializations: List<Specialization> = emptyList(),
    val selectedSpecializationIndex: Int = -1,
    val discoverCourses: List<Course> = emptyList(),


    val isLoading: Boolean = false,
    val isDiscoverLoading: Boolean = false,
//    val courses: List<Course> = emptyList(),
    val filteredCourses: List<Course> = emptyList(),
    val searchQuery: String = "",
    val error: String? = null,
    val discoverError: String? = null
)