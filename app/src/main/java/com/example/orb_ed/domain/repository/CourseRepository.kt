package com.example.orb_ed.domain.repository

import com.example.orb_ed.domain.model.Course
import com.example.orb_ed.domain.model.CourseBasicDetails
import com.example.orb_ed.domain.model.CourseModule
import com.example.orb_ed.domain.model.Program
import com.example.orb_ed.domain.model.Specialization
import kotlinx.coroutines.flow.Flow
import kotlin.Result

interface CourseRepository {
    suspend fun getEnrolledCourses(): Flow<List<Course>>
    suspend fun getPrograms(): Flow<List<Program>>
    suspend fun getSpecializations(programId: Int): Flow<List<Specialization>>

    suspend fun discoverCourses(
        courseType: String? = null,
        programId: Int? = null,
        specializationId: Int? = null,
        searchQuery: String? = null
    ): Flow<List<Course>>

    suspend fun getCourseBasicDetails(courseId: Int): Flow<Result<CourseBasicDetails>>

    suspend fun getCourseModules(courseId: Int): Flow<Result<List<CourseModule>>>
}
