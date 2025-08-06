package com.example.orb_ed.domain.usecase.course

import com.example.orb_ed.domain.model.CourseBasicDetails
import com.example.orb_ed.domain.repository.CourseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCourseBasicDetailsUseCase @Inject constructor(
    private val repository: CourseRepository
) {
    suspend operator fun invoke(courseId: Int): Flow<Result<CourseBasicDetails>> {
        return repository.getCourseBasicDetails(courseId)
    }
}
