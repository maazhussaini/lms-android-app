package com.example.orb_ed.presentation.screens.auth.coursedetail

/**
 * Represents user intents for the Course Dashboard screen.
 */
sealed class CourseDetailIntent {
    //    object LoadInitialData : CourseDetailIntent()
    data class ModuleSelected(val moduleId: Int) : CourseDetailIntent()
    data class TopicSelected(val topicId: Int) : CourseDetailIntent()


    /* data class SpecializationSelected(val index: Int) : CourseDetailIntent()
     object ClearFilters : CourseDetailIntent()

     // Search
     data class SearchQueryChanged(val query: String) : CourseDetailIntent()
     object OnSearchClicked : CourseDetailIntent()

     // Course actions
     data class CourseClicked(val courseId: String) : CourseDetailIntent()

     // Navigation
     object NavigateBack : CourseDetailIntent()

     // Discover courses
     object LoadDiscoverCourses : CourseDetailIntent()

     // Refresh intents
     object RefreshData : CourseDetailIntent()*/
}
