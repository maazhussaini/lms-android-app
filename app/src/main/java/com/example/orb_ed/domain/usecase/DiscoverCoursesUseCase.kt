package com.example.orb_ed.domain.usecase

import com.example.orb_ed.domain.model.Course
import com.example.orb_ed.domain.repository.CourseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DiscoverCoursesUseCase @Inject constructor(
    private val repository: CourseRepository
) {
    suspend operator fun invoke(
        courseType: String? = null,
        programId: Int? = null,
        specializationId: Int? = null,
        searchQuery: String? = null
    ): Flow<List<Course>> {
        return repository.discoverCourses(
            courseType = courseType,
            programId = if (programId == -1) null else programId,
            specializationId = if (specializationId == -1) null else specializationId,
            searchQuery = searchQuery
        )
    }
}
