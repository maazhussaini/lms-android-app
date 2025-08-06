package com.example.orb_ed.presentation.screens.auth.courseplayer

/**
 * Represents user intents for the Course Dashboard screen.
 */
sealed class CoursePlayerIntent {
    object OnPreviousClicked : CoursePlayerIntent()
    object OnNextClicked : CoursePlayerIntent()

}
