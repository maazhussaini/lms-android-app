package com.example.orb_ed.presentation.screens.auth.courseplayer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.orb_ed.R
import com.example.orb_ed.presentation.theme.LightPurpleBackgroundColor
import net.bunnystream.bunnystreamplayer.model.PlayerIconSet
import net.bunnystream.bunnystreamplayer.ui.BunnyStreamPlayer

@Composable
fun CoursePlayerScreen(
    videoId: String,
    libraryId: Long? = null
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = LightPurpleBackgroundColor), contentAlignment = Alignment.Center
    ) {
        AndroidView(
            modifier = Modifier
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
                it.playVideo(videoId, libraryId)
            }
        )
    }

}
