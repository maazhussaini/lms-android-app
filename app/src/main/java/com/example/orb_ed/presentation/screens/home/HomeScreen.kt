package com.example.orb_ed.presentation.screens.home

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.orb_ed.R
import com.example.orb_ed.presentation.components.CategoryTabs
import com.example.orb_ed.presentation.components.CourseCard
import com.example.orb_ed.presentation.components.CourseCardGrid
import com.example.orb_ed.presentation.components.SearchBar
import com.example.orb_ed.presentation.theme.GreySubtitleColor
import com.example.orb_ed.presentation.theme.LightPurpleBackgroundColor
import com.example.orb_ed.presentation.theme.OrbEdTheme
import com.example.orb_ed.presentation.theme.PrimaryColor
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.indendshape.StraightIndent
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
//    navController: NavController = rememberNavController(),
//    viewModel: HomeViewModel = hiltViewModel()
    onVideoClick: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedTabIndex by remember { mutableStateOf(0) }
    var selectedProgram by remember { mutableStateOf(-1) }
    var selectedSpecialization by remember { mutableStateOf(-1) }

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

    Scaffold(
        bottomBar = {
            var selectedItem by remember { mutableStateOf(2) }
            AnimatedNavigationBar(
                selectedIndex = selectedItem,
                ballColor = Transparent,
                barColor = PrimaryColor,
                cornerRadius = shapeCornerRadius(
                    topLeft = 40.dp, topRight = 40.dp, bottomLeft = 0.dp, bottomRight = 0.dp
                ),
                indentAnimation = StraightIndent(
                    indentWidth = 56.dp, indentHeight = 15.dp, animationSpec = tween(1000)
                )
            ) {

                NavItem(
                    title = "Reminders",
                    imageVector = ImageVector.vectorResource(R.drawable.ic_reminders)
                ) {
                    selectedItem = 0
                }
                NavItem(
                    title = "Noticeboard",
                    imageVector = ImageVector.vectorResource(R.drawable.ic_notice_board)
                ) {
                    selectedItem = 1
                }
                NavItem(
                    title = "My Courses",
                    imageVector = ImageVector.vectorResource(R.drawable.ic_courses)
                ) {
                    selectedItem = 2
                }
                NavItem(
                    title = "Settings",
                    imageVector = ImageVector.vectorResource(R.drawable.ic_setting)
                ) {
                    selectedItem = 3
                }
            }
        }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
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
                    onVideoClick()
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
}

data class CourseData(
    val id: String,
    val title: String,
    val instructor: String,
    val duration: String,
    val enrolled: Boolean,
    val progress: Float,
    val imageUrl: String
)

@Composable
private fun CourseItem() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
    ) {
        // Course item content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Course Item", style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FeatureCard(
    title: String, description: String, onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title, style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Go to $title",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun NavItem(imageVector: ImageVector, title: String, onItemClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        Image(
            modifier = Modifier
                .padding(top = 24.dp)
                .clickable {
                    onItemClick()
                }, imageVector = imageVector, contentDescription = null
        )
        Text(
            title, color = LightPurpleBackgroundColor, style = MaterialTheme.typography.labelMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
    }

}

@Composable
fun SubjectCategory(
    id: Int,
    color: Long, @DrawableRes image: Int, subject: String, onItemClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier.clickable {
            onItemClick(id)
        },
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(40.dp)
                .background(Color(color), shape = RoundedCornerShape(6.dp)),
            painter = painterResource(id = image),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )

        Text(
            subject, style = MaterialTheme.typography.labelSmall.copy(color = GreySubtitleColor)
        )

    }

}

data class Program(
    val color: Long, val image: Int, val title: String
)

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    OrbEdTheme {
        HomeScreen({})
    }
}
