package com.example.orb_ed.domain.usecase.course

import com.example.orb_ed.domain.model.CourseTopic
import com.example.orb_ed.domain.repository.CourseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCourseTopicsByModuleUseCase @Inject constructor(
    private val repository: CourseRepository
) {
    suspend operator fun invoke(moduleId: Int): Flow<Result<List<CourseTopic>>> {
        return repository.getCourseTopicsByModule(moduleId)
    }
}
