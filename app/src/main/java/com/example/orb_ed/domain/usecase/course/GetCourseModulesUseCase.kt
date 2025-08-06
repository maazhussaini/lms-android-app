package com.example.orb_ed.domain.usecase.course

import com.example.orb_ed.domain.model.CourseModule
import com.example.orb_ed.domain.repository.CourseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCourseModulesUseCase @Inject constructor(
    private val repository: CourseRepository
) {
    suspend operator fun invoke(courseId: Int): Flow<Result<List<CourseModule>>> {
        return repository.getCourseModules(courseId)
    }
}
