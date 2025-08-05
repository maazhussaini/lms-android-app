package com.example.orb_ed.data.repository

import android.util.Log
import com.example.orb_ed.data.manager.TokenManager
import com.example.orb_ed.data.remote.api.CourseApiService
import com.example.orb_ed.domain.model.Course
import com.example.orb_ed.domain.model.Program
import com.example.orb_ed.domain.model.Specialization
import com.example.orb_ed.domain.repository.CourseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "CourseRepositoryImpl"

@Singleton
class CourseRepositoryImpl @Inject constructor(
    private val api: CourseApiService,
    private val tokenManager: TokenManager
) : CourseRepository {
//    private val sampleCourses = courseLists

    override suspend fun getEnrolledCourses(): Flow<List<Course>> = flow {
        try {
            val token = tokenManager.accessToken
            if (token.isNullOrEmpty()) {
                Log.d(TAG, "No access token available")
                emit(emptyList())
                return@flow
            }

            val response = api.getEnrolledCourses(
                authToken = "Bearer $token"
            )

            if (response.isSuccessful) {
                val courses = response.body()?.data?.items?.map { it.toCourse() } ?: emptyList()
                Log.d(TAG, "Successfully fetched ${courses.size} enrolled courses")
                emit(courses)
            } else {
                Log.e(
                    TAG,
                    "Failed to fetch enrolled courses: ${response.code()} - ${response.message()}"
                )
                emit(emptyList())
            }
        } catch (e: IOException) {
            Log.e(TAG, "Network error while fetching enrolled courses", e)
            emit(emptyList())
        } catch (e: HttpException) {
            Log.e(TAG, "HTTP error while fetching enrolled courses", e)
            emit(emptyList())
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected error while fetching enrolled courses", e)
            emit(emptyList())
        }
    }

    override suspend fun getPrograms(): Flow<List<Program>> = flow {
        try {
            val token = tokenManager.accessToken
            if (token.isNullOrEmpty()) {
                Log.d(TAG, "No access token available")
                emit(emptyList())
                return@flow
            }

            val response = api.getPrograms(authToken = "Bearer $token")
            if (response.isSuccessful) {
                val programs = response.body()?.data?.items?.map { it.toProgram() } ?: emptyList()
                Log.d(TAG, "Successfully fetched ${programs.size} programs")
                emit(programs)
            } else {
                Log.e(TAG, "Failed to fetch programs: ${response.code()} - ${response.message()}")
                emit(emptyList())
            }
        } catch (e: IOException) {
            Log.e(TAG, "Network error while fetching programs", e)
            emit(emptyList())
        } catch (e: HttpException) {
            Log.e(TAG, "HTTP error while fetching programs", e)
            emit(emptyList())
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected error while fetching programs", e)
            emit(emptyList())
        }
    }

    override suspend fun getSpecializations(programId: Int): Flow<List<Specialization>> = flow {
        try {
            val token = tokenManager.accessToken
            if (token.isNullOrEmpty()) {
                Log.d(TAG, "No access token available")
                emit(emptyList())
                return@flow
            }

            val response = api.getSpecializations(
                programId = programId,
                authToken = "Bearer $token"
            )

            if (response.isSuccessful) {
                val specializations =
                    response.body()?.data?.items?.map { it.toSpecialization() } ?: emptyList()
                Log.d(
                    TAG,
                    "Successfully fetched ${specializations.size} specializations for program $programId"
                )
                emit(specializations)
            } else {
                Log.e(
                    TAG,
                    "Failed to fetch specializations: ${response.code()} - ${response.message()}"
                )
                emit(emptyList())
            }
        } catch (e: IOException) {
            Log.e(TAG, "Network error while fetching specializations", e)
            emit(emptyList())
        } catch (e: HttpException) {
            Log.e(TAG, "HTTP error while fetching specializations", e)
            emit(emptyList())
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected error while fetching specializations", e)
            emit(emptyList())
        }
    }

    /*override suspend fun searchCourses(query: String): Flow<List<Course>> = flow {
        if (query.isBlank()) {
            emit(emptyList())
        } else {
            val lowercaseQuery = query.lowercase()
            val results = sampleCourses.filter {
                it.title.lowercase().contains(lowercaseQuery) ||
                        it.teacherName.lowercase().contains(lowercaseQuery) ||
                        it.category.lowercase().contains(lowercaseQuery)
            }
            emit(results)
        }
    }*/

    override suspend fun discoverCourses(
        courseType: String?,
        programId: Int?,
        specializationId: Int?,
        searchQuery: String?
    ): Flow<List<Course>> = flow {
        try {
            val token = tokenManager.accessToken
            if (token.isNullOrEmpty()) {
                Log.d(TAG, "No access token available for discoverCourses")
                emit(emptyList())
                return@flow
            }

            val response = api.getDiscoverCourses(
                courseType = courseType,
                programId = programId,
                specializationId = specializationId,
                searchQuery = searchQuery,
                authToken = "Bearer $token"
            )

            if (response.isSuccessful) {
                val courses = response.body()?.data?.items?.map { it.toDomain() } ?: emptyList()
                Log.d(TAG, "Successfully fetched ${courses.size} discovered courses")
                emit(courses)
            } else {
                Log.e(
                    TAG,
                    "Failed to fetch discovered courses: ${response.code()} - ${response.message()}"
                )
                emit(emptyList())
            }
        } catch (e: IOException) {
            Log.e(TAG, "Network error while fetching discovered courses", e)
            emit(emptyList())
        } catch (e: HttpException) {
            Log.e(TAG, "HTTP error while fetching discovered courses", e)
            emit(emptyList())
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected error while fetching discovered courses", e)
            emit(emptyList())
        }
    }


}