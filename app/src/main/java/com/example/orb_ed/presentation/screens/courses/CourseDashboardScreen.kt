package com.example.orb_ed.presentation.screens.courses


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.orb_ed.R
import com.example.orb_ed.presentation.components.CategoryTabs
import com.example.orb_ed.presentation.components.CourseCardGrid
import com.example.orb_ed.presentation.components.SearchBar
import com.example.orb_ed.presentation.theme.GreySubtitleColor
import com.example.orb_ed.presentation.theme.LightPurpleBackgroundColor
import com.example.orb_ed.presentation.theme.PrimaryColor
import com.example.orb_ed.util.Constants.VIDEO_ID
import kotlinx.coroutines.flow.Flow

@Composable
fun CourseDashboardScreen(
    modifier: Modifier = Modifier,
    state: CourseDashboardState,
    effect: Flow<CourseDashboardEffect>,
    onIntent: (CourseDashboardIntent) -> Unit,
    onNavigateBack: () -> Unit = {},
    onCourseClick: (String) -> Unit = {}
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightPurpleBackgroundColor),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 20.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Text(
                text = stringResource(R.string.courses_title),
                style = MaterialTheme.typography.titleMedium.copy(color = PrimaryColor)
            )


            // Search Bar
            SearchBar(
                query = state.searchQuery,
                onQueryChange = { onIntent(CourseDashboardIntent.SearchQueryChanged(it)) },
                onSearch = { onIntent(CourseDashboardIntent.OnSearchClicked) },
                onFilterClick = { /* TODO: Handle filter */ },
                modifier = Modifier.fillMaxWidth()
            )


            // Category Tabs
            CategoryTabs(
                selectedTabIndex = state.selectedTabIndex,
                onTabSelected = { onIntent(CourseDashboardIntent.TabSelected(it)) },
                modifier = Modifier.fillMaxWidth(),
                tabs = listOf(
                    "All Courses (${state.discoverCourses.size})",
                    "Enrolled (${state.enrolledCourses.size})",
                    "Unenrolled (0)"
                )
            )

            if (state.selectedTabIndex == 0) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (state.selectedSpecializationIndex > -1 || state.selectedProgramIndex > -1)
                        Icon(
                            modifier = Modifier.clickable {
                                if (state.selectedSpecializationIndex > -1) {
                                    onIntent(CourseDashboardIntent.SpecializationSelected(-1))
                                } else {
                                    onIntent(CourseDashboardIntent.ProgramSelected(-1))
                                }
                            },
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = null
                        )


                    Column {
                        Text(
                            text = if (state.selectedSpecializationIndex > -1) state.specializations[state.selectedSpecializationIndex].name else if (state.selectedProgramIndex > -1) "Specializations" else "Programs",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontSize = 18.sp, color = PrimaryColor
                            )
                        )

                        if (state.selectedSpecializationIndex > -1 || state.selectedProgramIndex > -1)
                            Text(
                                text = state.programs[state.selectedProgramIndex].name,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 14.sp, color = GreySubtitleColor
                                )
                            )
                    }

                }
            }
        }

        if (state.selectedTabIndex == 0 && state.selectedSpecializationIndex == -1) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(horizontal = 20.dp)
            ) {
                if (state.selectedProgramIndex > -1) {
                    itemsIndexed(state.specializations) { index, specialization ->
                        SubjectCategoryItem(
                            id = index,
                            name = specialization.name,
                            thumbnailUrl = specialization.thumbnailUrl
                        ) {
                            onIntent(CourseDashboardIntent.SpecializationSelected(index))
                            println("Clicked specialization index: $index")
                        }
                    }
                } else {
                    itemsIndexed(state.programs) { index, program ->
                        SubjectCategoryItem(
                            id = index,
                            name = program.name,
                            thumbnailUrl = program.thumbnailUrl
                        ) {
                            onIntent(CourseDashboardIntent.ProgramSelected(index))
                            println("Clicked program index: $index")
                        }
                    }
                }
            }
        }



        if (state.selectedTabIndex == 0 || state.selectedTabIndex == 1)
            CourseCardGrid(if (state.selectedTabIndex == 0) state.discoverCourses else state.enrolledCourses) {
                onCourseClick(VIDEO_ID)
            }
        else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                Image(
                    painter = painterResource(id = R.drawable.no_courses),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.Center)
                )
                Text(
                    text = "No Unenrolled Courses",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.titleLarge,
                    color = PrimaryColor
                )
            }
        }

    }

}

@Composable
fun SubjectCategoryItem(
    id: Int,
    name: String,
    thumbnailUrl: String?,
    onClick: (Int) -> Unit
) {
    SubjectCategory(
        id = id,
        subject = name,
        thumbnailUrl = thumbnailUrl,
        onItemClick = onClick
    )
}


/*
data class Program(
    val color: Long, val image: Int, val title: String
)*/
