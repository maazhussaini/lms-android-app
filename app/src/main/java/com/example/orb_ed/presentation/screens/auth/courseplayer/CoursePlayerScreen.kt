package com.example.orb_ed.presentation.screens.auth.courseplayer

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.orb_ed.R
import com.example.orb_ed.presentation.components.PrimaryButton
import com.example.orb_ed.presentation.components.PrimaryOutlinedButton
import com.example.orb_ed.presentation.screens.courses.InitialsCircleAvatar
import com.example.orb_ed.presentation.theme.GreyHintColor
import com.example.orb_ed.presentation.theme.LightPurpleBackgroundColor
import com.example.orb_ed.presentation.theme.OrbEdTheme
import com.example.orb_ed.presentation.theme.PrimaryColor
import net.bunnystream.bunnystreamplayer.model.PlayerIconSet
import net.bunnystream.bunnystreamplayer.ui.BunnyStreamPlayer

@Preview(showBackground = true)
@Composable
fun Preview() {
    OrbEdTheme {
        CoursePlayerScreen(
            uiState = CoursePlayerState(), onIntent = {}, onBackClick = {}, onMessageClick = {}
        )
    }
}


@Composable
fun CoursePlayerScreen(
    libraryId: Long? = null,
    uiState: CoursePlayerState,
    onIntent: (CoursePlayerIntent) -> Unit,
    onBackClick: () -> Unit,
    onMessageClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = LightPurpleBackgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 20.dp),
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

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (uiState.profilePictureUrl != null) {
                        //todo implement thumbnail
                    } else {
                        InitialsCircleAvatar(uiState.teacherName, modifier = Modifier.size(30.dp))
                    }

                    Column(modifier = Modifier.align(Alignment.Bottom)) {
                        Text(
                            text = uiState.teacherName,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 12.sp,
                                color = PrimaryColor
                            )
                        )
                        uiState.teacherDesignation?.let {
                            Text(
                                text = uiState.teacherDesignation,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = GreyHintColor
                                )
                            )
                        }
                    }
                }

                Column {
                    if (uiState.moduleNumber != null && uiState.moduleNumber > 0 && !uiState.moduleName.isNullOrBlank())
                        Text(
                            text = "Module ${uiState.moduleNumber} ${uiState.moduleName}",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 9.sp,
                                color = GreyHintColor
                            )
                        )
                    if (uiState.lectureNumber > 0 && uiState.lectureName.isNotBlank())
                        Text(
                            text = "Lecture ${uiState.lectureNumber} ${uiState.lectureName}",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontSize = 12.sp,
                                color = PrimaryColor
                            )
                        )
                }

            }


        }
        BunnyPlayer(videoId = uiState.bunnyVideoId, libraryId = libraryId)
        EqualWidthButtonsRow(
            modifier = Modifier.weight(1f),
            uiState,
            onPreviousClick = { onIntent(CoursePlayerIntent.OnPreviousClicked) },
            onNextClick = { onIntent(CoursePlayerIntent.OnNextClicked) })
    }


}

@Composable
fun BunnyPlayer(modifier: Modifier = Modifier, videoId: String, libraryId: Long?) {
    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f),
        factory = { context ->
            BunnyStreamPlayer(context)
        },
        update = {
            it.iconSet = PlayerIconSet(
                playIcon = R.drawable.ic_play_48dp,

                pauseIcon = R.drawable.ic_pause_48dp,

                rewindIcon = R.drawable.ic_replay_10s_48dp,

                forwardIcon = R.drawable.ic_forward_10s_48dp,

                settingsIcon = R.drawable.ic_settings_24dp,

                volumeOnIcon = R.drawable.ic_volume_on_24dp,

                volumeOffIcon = R.drawable.ic_volume_off_24dp,

                fullscreenOnIcon = R.drawable.ic_full_screen_on,

                fullscreenOffIcon = R.drawable.ic_fullscreen_exit_24dp,
            )
            if (videoId.isNotBlank())
                it.playVideo(videoId, libraryId)


        }
    )
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun VideoWebView(modifier: Modifier = Modifier) {
    val videoUrl =
        "https://iframe.mediadelivery.net/embed/459051/6499d1b5-2bee-4843-9b66-2809f2627f02?autoplay=false&loop=false&muted=false&preload=true&responsive=true"

    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.mediaPlaybackRequiresUserGesture = false
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                webViewClient = WebViewClient()
                loadUrl(videoUrl)
            }
        }
    )
}


@Composable
fun EqualWidthButtonsRow(
    modifier: Modifier = Modifier,
    uiState: CoursePlayerState,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit
) {
    var maxButtonWidth by remember { mutableStateOf(0.dp) }

    // This captures and updates the max width between both buttons
    val updateMaxWidth: (Int, Density) -> Unit = { widthPx, density ->
        val widthDp = with(density) { widthPx.toDp() }
        if (widthDp > maxButtonWidth) maxButtonWidth = widthDp
    }

    val density = LocalDensity.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 40.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            PrimaryOutlinedButton(
                textStyle = MaterialTheme.typography.titleSmall.copy(fontSize = 12.sp),
                isEnabled = uiState.previousLectureName != null && uiState.previousLectureDuration != null,
                btnText = "Previous",
                onBtnClick = { onPreviousClick() },
                trailingIcon = Icons.Default.KeyboardArrowLeft,
                modifier = Modifier
                    .onGloballyPositioned {
                        updateMaxWidth(it.size.width, density)
                    }
                    .then(
                        if (maxButtonWidth > 0.dp) Modifier.width(maxButtonWidth)
                        else Modifier
                    )
            )
            if (uiState.previousLectureName != null && uiState.previousLectureDuration != null) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = uiState.previousLectureName,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 10.sp,
                        color = PrimaryColor
                    )
                )
                Text(
                    text = uiState.previousLectureDuration,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = GreyHintColor
                    )
                )
            }

        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            PrimaryButton(
                textStyle = MaterialTheme.typography.titleSmall.copy(fontSize = 12.sp),
                isEnabled = uiState.nextLectureName != null && uiState.nextLectureDuration != null,
                btnText = "Next",
                onBtnClick = { onNextClick() },
                leadingIcon = Icons.Default.KeyboardArrowRight,
                modifier = Modifier
                    .onGloballyPositioned {
                        updateMaxWidth(it.size.width, density)
                    }
                    .then(
                        if (maxButtonWidth > 0.dp) Modifier.width(maxButtonWidth)
                        else Modifier
                    )
            )
            if (uiState.nextLectureName != null && uiState.nextLectureDuration != null) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = uiState.nextLectureName,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 10.sp,
                        color = PrimaryColor
                    )
                )
                Text(
                    text = uiState.nextLectureDuration,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = GreyHintColor
                    )
                )
            }
        }
    }
}


