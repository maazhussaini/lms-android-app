package com.example.orb_ed.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.orb_ed.domain.model.Course
import com.example.orb_ed.presentation.screens.courses.InitialsCircleAvatar
import com.example.orb_ed.presentation.theme.FreeTagBackgroundColor
import com.example.orb_ed.presentation.theme.FreeTagColor
import com.example.orb_ed.presentation.theme.GreyHintColor
import com.example.orb_ed.presentation.theme.PriceTagBackgroundColor
import com.example.orb_ed.presentation.theme.PriceTagColor
import com.example.orb_ed.presentation.theme.PrimaryColor
import com.example.orb_ed.presentation.theme.ProgressIndicatorColor
import com.example.orb_ed.presentation.theme.PurchasedTagBackgroundColor
import com.example.orb_ed.presentation.theme.PurchasedTagColor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CourseCardGrid(courseList: List<Course>, onCardClick: (Course) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(courseList) { course ->
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCardClick(course) }
                    .padding(4.dp)
            ) {
                Column(modifier = Modifier.padding(7.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(20.dp)
                                .background(color = Color(0xFFF9BAD3), shape = CircleShape)
                        ) {
                            InitialsCircleAvatar(name = course.teacherName)
                            /*Image(
                                modifier = Modifier
                                    .size(16.dp)
                                    .clip(CircleShape)
                                    .align(Alignment.Center),
                                painter = painterResource(id = course.imageResId),
                                contentDescription = null
                            )*/
                        }

                        Column {
                            Text(
                                text = course.teacherName,
                                maxLines = 1,
                                minLines = 1,
                                overflow = Ellipsis,
                                style = MaterialTheme.typography.bodyMedium.copy(color = PrimaryColor)
                            )

                            course.teacherQualification?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.bodySmall.copy(color = GreyHintColor)
                                )
                            }

                        }
                    }

                    Spacer(Modifier.height(4.dp))

                    Text(
                        minLines = 2,
                        maxLines = 2,
                        softWrap = true,
                        text = course.title/*.split(" ").joinToString("\n")*/,
                        style = MaterialTheme.typography.labelMedium.copy(color = PrimaryColor)
                    )

                    Spacer(Modifier.height(2.dp))

                    Text(
                        text = course.programName,
                        style = MaterialTheme.typography.labelSmall.copy(color = GreyHintColor)
                    )

                    Spacer(Modifier.height(5.dp))

                    if (course.progress <= 0) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = course.duration,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = GreyHintColor,
                                )
                            )

                            Text(
                                textAlign = TextAlign.Center,
                                text = course.price,
                                overflow = Ellipsis,
                                maxLines = 1,
                                modifier = Modifier
//                                    .width(44.dp)
                                    .background(
                                        color = if (course.price.contains("Free")) FreeTagBackgroundColor else if (course.price.contains(
                                                "Purchased"
                                            )
                                        )
                                            PurchasedTagBackgroundColor
                                        else PriceTagBackgroundColor,
                                        shape = RoundedCornerShape(5.dp)
                                    )
                                    .padding(vertical = 4.dp, horizontal = 6.dp),
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontSize = 8.sp,
                                    color = if (course.price.contains("Free")) FreeTagColor else if (course.price.contains(
                                            "Purchased"
                                        )
                                    )
                                        PurchasedTagColor else PriceTagColor
                                )
                            )
                        }
                    } else {
                        ProgressWithLabel(course.progress / 100f)
                    }


                }
            }
        }
    }
}

@Composable
fun ProgressWithLabel(progress: Float) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val clampedProgress = progress.coerceIn(0f, 1f)
        val progressPosition = maxWidth * clampedProgress

        Column {
            // Progress bar (track + filled progress)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Box(
                    modifier = Modifier
                        .width(progressPosition)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(3.dp))
                        .background(ProgressIndicatorColor)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Labels Row
            Box(modifier = Modifier.fillMaxWidth()) {
                // 0% Label at start
                Text(
                    text = "0%",
                    modifier = Modifier.align(Alignment.CenterStart),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 4.sp,
                        color = GreyHintColor
                    )
                )

                // 100% Label at end
                Text(
                    text = "100%",
                    modifier = Modifier.align(Alignment.CenterEnd),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 4.sp,
                        color = GreyHintColor
                    )
                )

                // Current Progress Label
                Text(
                    text = "${(clampedProgress * 100).toInt()}%",
                    modifier = Modifier
                        .offset(x = progressPosition - 10.dp) // adjust for label width
                        .align(Alignment.CenterStart),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 4.sp,
                        color = GreyHintColor
                    )
                )
            }
        }
    }
}


