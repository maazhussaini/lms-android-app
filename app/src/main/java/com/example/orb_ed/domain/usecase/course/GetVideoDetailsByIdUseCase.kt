package com.example.orb_ed.domain.usecase.course

import com.example.orb_ed.domain.model.VideoDetails
import com.example.orb_ed.domain.repository.CourseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVideoDetailsByIdUseCase @Inject constructor(
    private val repository: CourseRepository
) {
    suspend operator fun invoke(videoId: Int): Flow<Result<VideoDetails>> {
        return repository.getVideoDetailsById(videoId)
    }
}
