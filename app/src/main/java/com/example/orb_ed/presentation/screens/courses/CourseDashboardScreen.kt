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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.orb_ed.R
import com.example.orb_ed.presentation.components.CategoryTabs
import com.example.orb_ed.presentation.components.CourseCard
import com.example.orb_ed.presentation.components.CourseCardGrid
import com.example.orb_ed.presentation.components.SearchBar
import com.example.orb_ed.presentation.theme.GreySubtitleColor
import com.example.orb_ed.presentation.theme.LightPurpleBackgroundColor
import com.example.orb_ed.presentation.theme.PrimaryColor
import com.example.orb_ed.util.Constants.LIBRARY_ID
import com.example.orb_ed.util.Constants.VIDEO_ID

@Composable
fun CourseDashboardScreen(modifier: Modifier = Modifier, onVideoClick: (String, Long) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var selectedProgram by remember { mutableIntStateOf(-1) }
    var selectedSpecialization by remember { mutableIntStateOf(-1) }

    val programLists = listOf(
        Program(0xFF3DD4CF, R.drawable.science, "Science"),
        Program(0xFF70A5FE, R.drawable.computer, "Computer"),
        Program(0xFFFF869D, R.drawable.arts, "Arts"),
        Program(0xFFF9B823, R.drawable.marketing, "Marketing"),
        Program(0xFF0097FE, R.drawable.finance, "Finance"),
        Program(0xFF5E301E, R.drawable.history, "History"),
        Program(0xFF3DD4CF, R.drawable.science, "Science"),
        Program(0xFF70A5FE, R.drawable.computer, "Computer"),
        Program(0xFFFF869D, R.drawable.arts, "Arts"),
        Program(0xFFF9B823, R.drawable.marketing, "Marketing"),
        Program(0xFF0097FE, R.drawable.finance, "Finance"),
        Program(0xFF5E301E, R.drawable.history, "History")
    )

    val specializationLists = listOf(
        Program(0xFF3DD4CF, R.drawable.science, "Abc"),
        Program(0xFF70A5FE, R.drawable.computer, "Rxy"),
    )
    // Sample list
    val initialCourses = listOf(
        CourseCard(
            "Chris Evans",
            "CS Professor",
            "Computer Science",
            "Software Engineering",
            "3 Hrs 15 mins",
            "Free",
            R.drawable.user_image
        ),
        CourseCard(
            "Cherry Blossom",
            "CS Professor",
            "Basic Electronic",
            "Software Engineering",
            "3 Hrs 15 mins",
            "Buy $10",
            R.drawable.user_image
        ),
        CourseCard(
            "Mick Taylor",
            "CS Professor",
            "Financial Accounting",
            "Software Engineering",
            "3 Hrs 15 mins",
            "Purchased",
            R.drawable.user_image
        ),
        CourseCard(
            "Sebastian Liam",
            "CS Professor",
            "Economics",
            "Software Engineering",
            "3 Hrs 15 mins",
            "Buy $10",
            R.drawable.user_image
        ),
        CourseCard(
            "Matthew Phill",
            "CS Professor",
            "Artificial Inteliigence",
            "Software Engineering",
            "3 Hrs 15 mins",
            "Purchased",
            R.drawable.user_image
        ),
        CourseCard(
            "Bella Hadid",
            "CS Professor",
            "Marketing",
            "Software Engineering",
            "3 Hrs 15 mins",
            "Buy $10",
            R.drawable.user_image
        ),
        CourseCard(
            "Chris Evans",
            "CS Professor",
            "Computer Science",
            "Software Engineering",
            "3 Hrs 15 mins",
            "Free",
            R.drawable.user_image
        ),
        CourseCard(
            "Cherry Blossom",
            "CS Professor",
            "Basic Electronic",
            "Software Engineering",
            "3 Hrs 15 mins",
            "Buy $10",
            R.drawable.user_image
        ),
        CourseCard(
            "Mick Taylor",
            "CS Professor",
            "Financial Accounting",
            "Software Engineering",
            "3 Hrs 15 mins",
            "Free",
            R.drawable.user_image
        ),
        CourseCard(
            "Sebastian Liam",
            "CS Professor",
            "Economics",
            "Software Engineering",
            "3 Hrs 15 mins",
            "Buy $10",
            R.drawable.user_image
        ),
        CourseCard(
            "Matthew Phill",
            "CS Professor",
            "Artificial Inteliigence",
            "Software Engineering",
            "3 Hrs 15 mins",
            "Free",
            R.drawable.user_image
        ),
        CourseCard(
            "Bella Hadid",
            "CS Professor",
            "Marketing",
            "Software Engineering",
            "3 Hrs 15 mins",
            "Buy $10",
            R.drawable.user_image
        ),
        CourseCard(
            "Chris Evans",
            "CS Professor",
            "Computer Science",
            "Software Engineering",
            "3 Hrs 15 mins",
            "Free",
            R.drawable.user_image
        ),
        CourseCard(
            "Cherry Blossom",
            "CS Professor",
            "Basic Electronic",
            "Software Engineering",
            "3 Hrs 15 mins",
            "Buy $10",
            R.drawable.user_image
        ),
        CourseCard(
            "Mick Taylor",
            "CS Professor",
            "Financial Accounting",
            "Software Engineering",
            "3 Hrs 15 mins",
            "Free",
            R.drawable.user_image
        ),
        CourseCard(
            "Sebastian Liam",
            "CS Professor",
            "Economics",
            "Software Engineering",
            "3 Hrs 15 mins",
            "Buy $10",
            R.drawable.user_image
        ),
        CourseCard(
            "Matthew Phill",
            "CS Professor",
            "Artificial Inteliigence",
            "Software Engineering",
            "3 Hrs 15 mins",
            "Free",
            R.drawable.user_image
        ),
        CourseCard(
            "Bella Hadid",
            "CS Professor",
            "Marketing",
            "Software Engineering",
            "3 Hrs 15 mins",
            "Buy $10",
            R.drawable.user_image
        ),
        // Repeat or generate more
    )
    var courses by remember { mutableStateOf(initialCourses) }


    val enrolledCourses = listOf(
        CourseCard(
            "Chris Evans",
            "CS Professor",
            "Computer Science",
            "Software Engineering",
            "3 Hrs 15 mins",
            "Free",
            R.drawable.user_image,
            progress = 23f
        ),
        CourseCard(
            "Cherry Blossom",
            "CS Professor",
            "Basic Electronic",
            "Software Engineering",
            "3 Hrs 15 mins",
            "Buy $10",
            R.drawable.user_image,
            progress = 67f
        ),
        CourseCard(
            "Mick Taylor",
            "CS Professor",
            "Financial Accounting",
            "Software Engineering",
            "3 Hrs 15 mins",
            "Free",
            R.drawable.user_image,
            progress = 38f
        ),
        CourseCard(
            "Sebastian Liam",
            "CS Professor",
            "Economics",
            "Software Engineering",
            "3 Hrs 15 mins",
            "Buy $10",
            R.drawable.user_image,
            progress = 50f
        ),
        CourseCard(
            "Matthew Phill",
            "CS Professor",
            "Artificial Inteliigence",
            "Software Engineering",
            "3 Hrs 15 mins",
            "Free",
            R.drawable.user_image,
            progress = 40f
        ),
        CourseCard(
            "Bella Hadid",
            "CS Professor",
            "Marketing",
            "Software Engineering",
            "3 Hrs 15 mins",
            "Buy $10",
            R.drawable.user_image,
            progress = 80f
        ),
        // Repeat or generate more
    )

    Column(
        modifier = modifier
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
                text = "My Courses",
                style = MaterialTheme.typography.titleMedium.copy(color = PrimaryColor)
            )


            // Search Bar
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearch = { /* TODO: Handle search */ },
                onFilterClick = { /* TODO: Handle filter */ },
                modifier = Modifier.fillMaxWidth()
            )


            // Category Tabs
            CategoryTabs(
                selectedTabIndex = selectedTabIndex,
                onTabSelected = { selectedTabIndex = it },
                modifier = Modifier.fillMaxWidth(),
                tabs = listOf("All Courses (50)", "Enrolled (6)", "Unenrolled (0)")
            )



            if (selectedTabIndex == 0)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (selectedSpecialization > -1 || selectedProgram > -1)
                        Icon(
                            modifier = Modifier.clickable {
                                if (selectedSpecialization > -1) {
                                    selectedSpecialization = -1
                                } else {
                                    selectedProgram = -1
                                }
                                courses = courses.shuffled()
                            },
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = null
                        )


                    Column {
                        Text(
                            text = if (selectedSpecialization > -1) specializationLists[selectedSpecialization].title else if (selectedProgram > -1) "Specializations" else "Programs",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontSize = 18.sp, color = PrimaryColor
                            )
                        )

                        if (selectedSpecialization > -1 || selectedProgram > -1)
                            Text(
                                text = programLists[selectedProgram].title,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 14.sp, color = GreySubtitleColor
                                )
                            )
                    }

                }
        }

        if (selectedTabIndex == 0)
            if (selectedSpecialization == -1)
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    contentPadding = PaddingValues(horizontal = 20.dp)
                ) {
                    val list = if (selectedProgram > -1) specializationLists else programLists

                    itemsIndexed(list) { index, subject ->
                        SubjectCategory(
                            id = index,
                            color = subject.color,
                            image = subject.image,
                            subject = subject.title,
                            onItemClick = {
                                if (selectedProgram > -1) {
                                    selectedSpecialization = index
                                } else {
                                    selectedProgram = index
                                }

                                courses = courses.shuffled()
                                // ✅ You can now also use the `index`
                                println("Clicked item index: $index")
                            }
                        )
                    }
                }


        if (selectedTabIndex == 0 || selectedTabIndex == 1)
            CourseCardGrid(if (selectedTabIndex == 0) courses else enrolledCourses) {
                onVideoClick(VIDEO_ID, LIBRARY_ID)
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

data class Program(
    val color: Long, val image: Int, val title: String
)