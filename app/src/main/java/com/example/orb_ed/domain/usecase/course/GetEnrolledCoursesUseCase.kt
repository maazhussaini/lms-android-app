package com.example.orb_ed.domain.usecase.course

import com.example.orb_ed.domain.model.Course
import com.example.orb_ed.domain.repository.CourseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEnrolledCoursesUseCase @Inject constructor(
    private val repository: CourseRepository
) {
    suspend operator fun invoke(): Flow<List<Course>> = repository.getEnrolledCourses()
}
