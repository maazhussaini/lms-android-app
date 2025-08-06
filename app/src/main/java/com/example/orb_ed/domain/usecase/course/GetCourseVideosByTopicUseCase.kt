package com.example.orb_ed.domain.usecase.course

import com.example.orb_ed.domain.model.CourseVideo
import com.example.orb_ed.domain.repository.CourseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCourseVideosByTopicUseCase @Inject constructor(
    private val repository: CourseRepository
) {
    suspend operator fun invoke(topicId: Int): Flow<Result<List<CourseVideo>>> {
        return repository.getCourseVideosByTopic(topicId)
    }
}
