package com.example.orb_ed.presentation.screens.courses

/**
 * Represents user intents for the Course Dashboard screen.
 */
sealed class CourseDashboardIntent {
    // Data loading
    object LoadInitialData : CourseDashboardIntent()

    // Tab and filter selection
    data class TabSelected(val index: Int) : CourseDashboardIntent()
    data class ProgramSelected(val index: Int) : CourseDashboardIntent()
    data class SpecializationSelected(val index: Int) : CourseDashboardIntent()
    object ClearFilters : CourseDashboardIntent()

    // Search
    data class SearchQueryChanged(val query: String) : CourseDashboardIntent()
    object OnSearchClicked : CourseDashboardIntent()

    // Course actions
    data class CourseClicked(val courseId: String) : CourseDashboardIntent()

    // Navigation
    object NavigateBack : CourseDashboardIntent()

    // Discover courses
    object LoadDiscoverCourses : CourseDashboardIntent()

    // Refresh intents
    object RefreshData : CourseDashboardIntent()
}
