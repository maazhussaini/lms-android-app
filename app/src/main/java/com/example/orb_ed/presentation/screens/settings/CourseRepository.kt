package com.example.orb_ed.domain.repository

import com.example.orb_ed.domain.model.Course
import com.example.orb_ed.domain.model.Program
import com.example.orb_ed.domain.model.Specialization
import kotlinx.coroutines.flow.Flow

interface CourseRepository {
    suspend fun getEnrolledCourses(): Flow<List<Course>>
    suspend fun getPrograms(): Flow<List<Program>>
    suspend fun getSpecializations(programId: Int): Flow<List<Specialization>>
//    suspend fun searchCourses(query: String): Flow<List<Course>>

    suspend fun discoverCourses(
        courseType: String? = null,
        programId: Int? = null,
        specializationId: Int? = null,
        searchQuery: String? = null
    ): Flow<List<Course>>
}
