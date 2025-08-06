package com.example.orb_ed.presentation.navigation

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.orb_ed.presentation.screens.auth.coursedetail.CourseDetailScreen
import com.example.orb_ed.presentation.screens.auth.coursedetail.CourseDetailViewModel
import com.example.orb_ed.presentation.screens.auth.courseplayer.CoursePlayerScreen
import com.example.orb_ed.presentation.screens.auth.courseplayer.CoursePlayerState
import com.example.orb_ed.presentation.screens.courses.CourseDashboardScreen
import com.example.orb_ed.presentation.screens.courses.CourseDashboardViewModel
import com.example.orb_ed.presentation.theme.PrimaryColor
import com.example.orb_ed.util.Constants.LIBRARY_ID
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.indendshape.StraightIndent
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import kotlinx.coroutines.delay

@Composable
fun BottomNavScaffold(
    modifier: Modifier = Modifier
) {
    // Define your bottom nav tabs
    val bottomTabs = bottomNavItems
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(2) } // Default to Courses


    // Create a NavHostController for each tab
    val navControllers = remember {
        mutableMapOf<BottomTabType, NavHostController>()
    }.also { controllerMap ->
        BottomTabType.entries.forEach { type ->
            if (controllerMap[type] == null) {
                controllerMap[type] = rememberNavController()
            }
        }
    }

    val currentTab = bottomTabs[selectedTabIndex]
    val currentNavController = navControllers[currentTab.key]!!




    Scaffold(
        modifier = modifier,
        bottomBar = {
            AnimatedNavigationBar(
                selectedIndex = selectedTabIndex,
                ballColor = Transparent,
                barColor = PrimaryColor,
                cornerRadius = shapeCornerRadius(
                    topLeft = 40.dp, topRight = 40.dp, bottomLeft = 0.dp, bottomRight = 0.dp
                ),
                indentAnimation = StraightIndent(
                    indentWidth = 56.dp,
                    indentHeight = 15.dp,
                    animationSpec = tween(200)
                )
            ) {
                bottomTabs.forEach { tab ->
                    NavItem(
                        title = tab.title,
                        imageVector = ImageVector.vectorResource(tab.iconRes)
                    ) {
                        selectedTabIndex = tab.index
                    }
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = currentNavController,
            startDestination = currentTab.screen,
            modifier = Modifier.padding(padding)
        ) {
            // Type-safe main screens
            composable<CoursesDashboard> {
                val viewModel = hiltViewModel<CourseDashboardViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()

                CourseDashboardScreen(
                    onNavigateBack = { /* Handle back navigation if needed */ },
                    onCourseClick = { courseId ->
                        currentNavController.navigate(CourseDetail(courseId))
//                        currentNavController.navigate(CoursePlayer(videoId, LIBRARY_ID))
                    },
                    state = state,
                    effect = viewModel.effect,
                    onIntent = viewModel::processIntent
                )
            }

            composable<CourseDetail> {
                val viewModel = hiltViewModel<CourseDetailViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()
                val args = it.toRoute<CourseDetail>()
                CourseDetailScreen(
                    uiState = state,
                    onIntent = viewModel::processIntent,
                    onBackClick = { currentNavController.popBackStack() },
                    onMessageClick = {},
                    onVideoClick = {
                        currentNavController.navigate(CoursePlayer(it, LIBRARY_ID))
                    }
                )
            }

            composable<CoursePlayer> {
                val coursePlayerData = it.toRoute<CoursePlayer>()
                CoursePlayerScreen(
                    uiState = CoursePlayerState(
                        profilePictureUrl = null,
                        teacherName = "Chris Evans",
                        teacherDesignation = "Computer Science Professor",
                        moduleNumber = 1,
                        moduleName = "Computer Science",
                        lectureNumber = 1,
                        lectureName = "Basic Programming",
                        previousLectureName = null,
                        nextLectureName = null,
                        previousLectureDuration = null,
                        nextLectureDuration = null
                    ),
                    videoId = coursePlayerData.videoId,
                    libraryId = coursePlayerData.libraryId
                )

            }

            composable<Reminders> {
                /* RemindersScreen(onNavigateToDetail = {
                 currentNavController.navigate(ReminderDetail(it))
             }) */
            }

            composable<Settings> {
//                SettingsScreen()
            }
            composable<Noticeboard> {
//                NoticeboardScreen()
            }

            /*// Nested detail screens (per tab)
            composable<CourseDetail> { entry ->
                val args = entry.toRoute<CourseDetail>()
                CourseDetailScreen(courseId = args.id)
            }

            composable<ReminderDetail> { entry ->
                val args = entry.toRoute<ReminderDetail>()
                ReminderDetailScreen(reminderId = args.id)
            }*/
        }

        HandleBackPress(
            currentTabType = currentTab.key,
            onSwitchToCourses = { selectedTabIndex = 2 }, // 2 = Courses tab index
            currentNavController = currentNavController
        )
    }
}

@Composable
fun HandleBackPress(
    currentTabType: BottomTabType,
    onSwitchToCourses: () -> Unit,
    currentNavController: NavHostController
) {
    var backPressedOnce by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // 👇 This is called *in the correct composable scope*
    if (backPressedOnce) {
        LaunchedEffect(Unit) {
            delay(2000)
            backPressedOnce = false
        }
    }

    BackHandler {
        val popped = currentNavController.popBackStack()

        if (popped) {
            // A screen was popped; no further action needed
            return@BackHandler
        }

        if (currentTabType == BottomTabType.COURSES) {
            if (backPressedOnce) {
                (context as? Activity)?.finish()
            } else {
                backPressedOnce = true
                Toast.makeText(context, "Press back again to exit", Toast.LENGTH_SHORT).show()
            }
        } else {
            onSwitchToCourses()
        }
    }
}



