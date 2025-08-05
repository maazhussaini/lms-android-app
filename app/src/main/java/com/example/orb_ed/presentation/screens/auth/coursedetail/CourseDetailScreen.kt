package com.example.orb_ed.presentation.screens.auth.coursedetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.orb_ed.presentation.screens.courses.InitialsCircleAvatar


@Preview(showBackground = true)
@Composable
fun CourseDetailScreenPreview() {
    CourseDetailScreen()

}

@Composable
fun CourseDetailScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            InitialsCircleAvatar("yolo sjdn")
            /*Image(
                painter = painterResource(R.drawable.user_image),
                contentDescription = null,
            ) // set desired size*/

        }

        /*Image(
            painter = painterResource(R.drawable.course_detail_bg),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth() // set desired size
        )*/


        /*Box(
            modifier = Modifier
                .matchParentSize() // overlay exactly matches the image size
                .background(Color.Black.copy(alpha = 0.6f))
        )*/
    }
}