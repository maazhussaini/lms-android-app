package com.example.orb_ed.domain.usecase.course

import com.example.orb_ed.domain.repository.CourseRepository
import javax.inject.Inject

class SearchCoursesUseCase @Inject constructor(
    private val repository: CourseRepository
) {
//    suspend operator fun invoke(query: String): Flow<List<Course>> = repository.searchCourses(query)
}
