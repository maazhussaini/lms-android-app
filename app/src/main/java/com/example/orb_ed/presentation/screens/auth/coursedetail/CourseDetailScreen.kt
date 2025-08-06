package com.example.orb_ed.presentation.screens.auth.coursedetail

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.orb_ed.R
import com.example.orb_ed.domain.model.CourseModule
import com.example.orb_ed.presentation.components.CategoryTabs
import com.example.orb_ed.presentation.components.ProgressWithLabel
import com.example.orb_ed.presentation.screens.courses.InitialsCircleAvatar
import com.example.orb_ed.presentation.theme.FreeTagBackgroundColor
import com.example.orb_ed.presentation.theme.FreeTagColor
import com.example.orb_ed.presentation.theme.LightPurpleBackgroundColor
import com.example.orb_ed.presentation.theme.LockColor
import com.example.orb_ed.presentation.theme.OrbEdTheme
import com.example.orb_ed.presentation.theme.PendingTagBackgroundColor
import com.example.orb_ed.presentation.theme.PendingTagColor
import com.example.orb_ed.presentation.theme.PriceTagBackgroundColor
import com.example.orb_ed.presentation.theme.PriceTagColor
import com.example.orb_ed.presentation.theme.PrimaryColor
import com.example.orb_ed.presentation.theme.PurchasedTagBackgroundColor
import com.example.orb_ed.presentation.theme.PurchasedTagColor
import com.example.orb_ed.presentation.theme.UnLockColor


@Preview(showBackground = true)
@Composable
fun CourseDetailScreenPreview() {
    OrbEdTheme {
        CourseDetailScreen(
            CourseDetailState(
                courseName = "Introduction to Python",
                courseDescription = "Business course covering fundamentals of management and strategy.",
                courseDuration = "May 2025 - July 2025",
                teacherName = "Chris Evans",
                coursePrice = "Buy: $29",
                progress = 0.3276f,
                programName = "Machine Learning",
                specializationName = "Data Science",
                listOfTopics = listOf(
                    Topic(1, "Introduction", 3),
                    Topic(2, "Basics", 5),
                    Topic(3, "Advanced", 4),
                    Topic(4, "Final Project", 2),
                    Topic(5, "Review", 1),
                    Topic(6, "Certification", 1),
                    Topic(7, "Q&A", 2),
                    Topic(8, "Bonus Content", 1),
                    Topic(9, "Community", 1),
                    Topic(10, "Additional Resources", 1),
                    Topic(11, "Career Support", 1),
                    Topic(12, "Feedback", 1),
                    Topic(13, "Next Steps", 1),
                    Topic(14, "Graduation", 1),
                    Topic(15, "Alumni Network", 1)
                ),
                listOfVideoLectures = listOf(
                    VideoLecture(1, "Video 1", "15 Mins", "", "Completed", false),
                    VideoLecture(2, "Video 2", "15 Mins", "", "Pending", true),
                    VideoLecture(3, "Video 3", "15 Mins", "", "Incomplete", true),
                ),
                selectedTopic = 1,
                selectedModule = 1
            ), onIntent = {}, onBackClick = {}, onMessageClick = {}, onVideoClick = {}
        )
    }


}

@Composable
fun CourseDetailScreen(
    uiState: CourseDetailState,
    onIntent: (CourseDetailIntent) -> Unit,
    onBackClick: () -> Unit,
    onMessageClick: () -> Unit,
    onVideoClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = LightPurpleBackgroundColor)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {

            Image(
                painter = painterResource(R.drawable.course_detail_bg),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth() // set desired size
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(color = Color.Black.copy(alpha = 0.4f))
            )


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .matchParentSize()
                    .padding(horizontal = 20.dp)
//                    .padding(bottom = 30.dp)
                    .align(Alignment.BottomCenter),
                verticalArrangement = Arrangement.SpaceAround
            )
            {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(24.dp)
                            .background(White, shape = RoundedCornerShape(10.dp))
                            .clickable {
                                onBackClick()
                            },
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_back),
                            contentDescription = null
                        )
                    }

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(24.dp)
                            .background(White, shape = RoundedCornerShape(10.dp))
                            .clickable {
                                onMessageClick()
                            },
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_message),
                            contentDescription = null
                        )
                    }
                }
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    if (uiState.profilePictureUrl != null) {
                        //todo implement thumbnail
                    } else {
                        InitialsCircleAvatar(uiState.teacherName, modifier = Modifier.size(100.dp))
                    }
                    Column(modifier = Modifier.align(Alignment.Bottom)) {
                        Text(
                            text = uiState.courseName,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 20.sp,
                                color = White
                            )
                        )
                        Text(
                            text = uiState.programName + ", " + uiState.specializationName,
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontSize = 12.sp,
                                color = White
                            )
                        )
                        Text(
                            text = uiState.courseDuration,
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontSize = 8.sp,
                                color = White
                            )
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = uiState.teacherName,
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontSize = 8.sp,
                                    color = White
                                )
                            )

                            val price = uiState.coursePrice
                            Text(
                                textAlign = TextAlign.Center,
                                text = price,
                                overflow = Ellipsis,
                                maxLines = 1,
                                modifier = Modifier
                                    .background(
                                        color = if (price.contains("Free")) FreeTagBackgroundColor else if (price.contains(
                                                "Purchased"
                                            )
                                        )
                                            PurchasedTagBackgroundColor
                                        else PriceTagBackgroundColor,
                                        shape = RoundedCornerShape(5.dp)
                                    )
                                    .padding(vertical = 5.dp, horizontal = 15.dp),
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontSize = 6.sp,
                                    color = if (price.contains("Free")) FreeTagColor else if (price.contains(
                                            "Purchased"
                                        )
                                    )
                                        PurchasedTagColor else PriceTagColor
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        ProgressWithLabel(uiState.progress, labelSize = 7.sp, labelColor = White)
                    }
                }

            }


            /*Box(
                modifier = Modifier
                    .matchParentSize() // overlay exactly matches the image size
                    .background(Color.Black.copy(alpha = 0.6f))
            )*/
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .padding(top = 10.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            val courseMenu = listOf("Course AB")
            val moduleMenu = listOf(
                "Module ${uiState.selectedModule}",
                "Assignments (3)",
                "Quizzes (0)",
            )
            val topicMenu = listOf(
                "Topic ${uiState.selectedTopic}",
                "Live Classes",
                "Assignments (3)",
                "Quizzes (0)",
                "Material (2)"
            )

            CategoryTabs(
                selectedTabIndex = 0,
                onTabSelected = {},
                tabs = if (uiState.selectedTopic > 0) topicMenu else if (uiState.selectedModule > 0) moduleMenu else courseMenu
            )

            if (uiState.selectedModule <= 0)
                Text(
                    text = uiState.courseDescription, style =
                        MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 12.sp,
                            lineHeight = 20.sp
                        )
                )

            Row {
                if (uiState.selectedModule > 0 || uiState.selectedTopic > 0)
                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                if (uiState.selectedTopic > 0) {
                                    onIntent(CourseDetailIntent.TopicSelected(0))
                                } else {
                                    onIntent(CourseDetailIntent.ModuleSelected(0))
                                }
                            },
                        imageVector = ImageVector.vectorResource(R.drawable.ic_back),
                        contentDescription = null
                    )

                Text(
                    text = if (uiState.selectedTopic > 0) "Video Lectures" else if (uiState.selectedModule > 0) "Topics" else "Modules",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 20.sp,
                        color = PrimaryColor
                    )
                )
            }


            val itemModelList =
                if (uiState.selectedTopic > 0) uiState.listOfVideoLectures.toVideoLectureItemModels() else if (uiState.selectedModule > 0) uiState.listOfTopics.toTopicItemModels() else uiState.listOfModules.toModuleItemModels()
            LazyColumn(
                contentPadding = PaddingValues(bottom = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(itemModelList) { item ->
                    ListingItem(item) { itemId, videoId ->
                        if (videoId != null) {
                            onVideoClick(videoId)
                        } else {
                            if (item.itemType is ItemType.module)
                                onIntent(CourseDetailIntent.ModuleSelected(itemId))
                            else if (item.itemType is ItemType.topic)
                                onIntent(CourseDetailIntent.TopicSelected(itemId))
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun ListingItem(itemModel: ItemModel, onItemClick: (Int, String?) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp, horizontal = 10.dp)
        ) {
            Column {
                Text(
                    text = itemModel.title, style =
                        MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 12.sp
                        )
                )

                Text(
                    text = itemModel.subTitle,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 8.sp,
                        color = PrimaryColor
                    )
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                itemModel.completionStatus?.let {
                    Text(
                        textAlign = TextAlign.Center,
                        text = it,
                        overflow = Ellipsis,
                        maxLines = 1,
                        modifier = Modifier
                            .background(
                                color = if (it.contains("Pending")) PendingTagBackgroundColor else if (it.contains(
                                        "Completed"
                                    )
                                )
                                    PurchasedTagBackgroundColor
                                else PriceTagBackgroundColor,
                                shape = RoundedCornerShape(5.dp)
                            )
                            .padding(vertical = 5.dp, horizontal = 15.dp),
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontSize = 6.sp,
                            color = if (it.contains("Pending")) PendingTagColor else if (it.contains(
                                    "Completed"
                                )
                            )
                                PurchasedTagColor else PriceTagColor
                        )
                    )
                }

                itemModel.isLocked?.let {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(24.dp)
                            .background(
                                if (it) LockColor else UnLockColor,
                                shape = RoundedCornerShape(5.dp)
                            ),
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(if (it) R.drawable.ic_lock else R.drawable.ic_unlock),
                            contentDescription = null,
                            tint = White
                        )
                    }
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(24.dp)
                        .background(PrimaryColor, shape = RoundedCornerShape(5.dp))
                        .clickable {
                            val itemType = itemModel.itemType
                            val id = when (itemType) {
                                is ItemType.module -> itemType.id
                                is ItemType.topic -> itemType.id
                                is ItemType.videoLecture -> itemType.id
                            }
                            val videoId =
                                if (itemType is ItemType.videoLecture) itemType.videoId else null
                            onItemClick(id, videoId)
                        }
                ) {
                    Icon(
                        modifier = Modifier.rotate(180f),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_back),
                        contentDescription = null,
                        tint = White
                    )
                }


            }


        }

    }

}

fun List<CourseModule>.toModuleItemModels(): List<ItemModel> {
    return this.map {
        ItemModel(
            itemType = ItemType.module(it.id),
            title = it.name,
            subTitle = it.stats,
        )
    }
}

fun List<Topic>.toTopicItemModels(): List<ItemModel> {
    return this.map {
        ItemModel(
            itemType = ItemType.topic(it.topicId),
            title = it.topicName,
            subTitle = "${it.noOfVideoLectures} Video Lectures",
        )
    }
}

fun List<VideoLecture>.toVideoLectureItemModels(): List<ItemModel> {
    return this.map {
        ItemModel(
            itemType = ItemType.videoLecture(it.videoLectureId, it.videoId),
            title = it.videoLectureName,
            subTitle = it.videoLectureDuration,
            completionStatus = it.videoLectureStatus,
            isLocked = it.videoLectureIsLocked
        )
    }
}