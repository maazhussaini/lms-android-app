package com.example.orb_ed.presentation.navigation

import androidx.annotation.DrawableRes
import com.example.orb_ed.R

data class BottomNavItem(
    val index: Int,
    val key: BottomTabType,
    val screen: Any,
    val title: String,
    @DrawableRes val iconRes: Int
)


val bottomNavItems = listOf(
    BottomNavItem(0, BottomTabType.REMINDERS, Reminders, "Reminders", R.drawable.ic_reminders),
    BottomNavItem(
        1,
        BottomTabType.NOTICEBOARD,
        Noticeboard,
        "Noticeboard",
        R.drawable.ic_notice_board
    ),
    BottomNavItem(2, BottomTabType.COURSES, CoursesDashboard, "My Courses", R.drawable.ic_courses),
    BottomNavItem(3, BottomTabType.SETTINGS, Settings, "Settings", R.drawable.ic_setting)
)

enum class BottomTabType {
    COURSES, REMINDERS, NOTICEBOARD, SETTINGS
}


