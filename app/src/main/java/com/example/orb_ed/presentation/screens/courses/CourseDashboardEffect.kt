package com.example.orb_ed.presentation.screens.courses

/**
 * Represents side effects for the Course Dashboard screen.
 */
sealed class CourseDashboardEffect {
    // Navigation effects
    object NavigateBack : CourseDashboardEffect()
    data class NavigateToCourseDetail(val courseId: String) : CourseDashboardEffect()

    // UI feedback effects
    data class ShowError(val message: String) : CourseDashboardEffect()
    data class ShowMessage(val message: String) : CourseDashboardEffect()
    object ShowLoading : CourseDashboardEffect()
    object HideLoading : CourseDashboardEffect()

    // Filter effects
    object FiltersCleared : CourseDashboardEffect()
    object FiltersApplied : CourseDashboardEffect()
}
