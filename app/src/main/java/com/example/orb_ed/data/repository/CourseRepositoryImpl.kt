package com.example.orb_ed.data.repository

import android.util.Log
import com.example.orb_ed.data.manager.TokenManager
import com.example.orb_ed.data.remote.api.CourseApiService
import com.example.orb_ed.domain.model.Course
import com.example.orb_ed.domain.model.CourseBasicDetails
import com.example.orb_ed.domain.model.CourseModule
import com.example.orb_ed.domain.model.CourseTopic
import com.example.orb_ed.domain.model.CourseVideo
import com.example.orb_ed.domain.model.Program
import com.example.orb_ed.domain.model.Specialization
import com.example.orb_ed.domain.model.VideoDetails
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

    override suspend fun getCourseModules(courseId: Int): Flow<Result<List<CourseModule>>> = flow {
        try {
            val token = tokenManager.accessToken
            if (token == null) {
                emit(Result.failure(Exception("User not authenticated")))
                return@flow
            }

            val response = api.getCourseModules(courseId, "Bearer $token")

            if (response.isSuccessful) {
                val body = response.body()
                if (body?.success == true) {
                    val modules = body.data.items.map { it.toCourseModule() }
                    emit(Result.success(modules))
                } else {
                    emit(Result.failure(Exception(body?.message ?: "Unknown error")))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                emit(Result.failure(Exception("Failed to fetch course modules: $errorBody")))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting course modules", e)
            emit(Result.failure(e))
        }
    }

    override suspend fun getCourseTopicsByModule(moduleId: Int): Flow<Result<List<CourseTopic>>> =
        flow {
            try {
                val token = tokenManager.accessToken
                if (token == null) {
                    emit(Result.failure(Exception("User not authenticated")))
                    return@flow
                }

                val response = api.getCourseTopicsByModule(moduleId, "Bearer $token")

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body?.success == true) {
                        val topics = body.data.items.map { it.toCourseTopic() }
                        emit(Result.success(topics))
                    } else {
                        emit(Result.failure(Exception(body?.message ?: "Unknown error")))
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    emit(Result.failure(Exception("Failed to fetch course topics: $errorBody")))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error getting course topics", e)
                emit(Result.failure(e))
            }
        }

    override suspend fun getCourseVideosByTopic(topicId: Int): Flow<Result<List<CourseVideo>>> =
        flow {
            try {
                val token = tokenManager.accessToken
                if (token == null) {
                    emit(Result.failure(Exception("User not authenticated")))
                    return@flow
                }

                val response = api.getCourseVideosByTopic(topicId, "Bearer $token")

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body?.success == true) {
                        val videos = body.data.items.map { it.toCourseVideo() }
                        emit(Result.success(videos))
                    } else {
                        emit(Result.failure(Exception(body?.message ?: "Unknown error")))
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    emit(Result.failure(Exception("Failed to fetch course videos: $errorBody")))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error getting course videos", e)
                emit(Result.failure(e))
            }
        }

    override suspend fun getVideoDetailsById(videoId: Int): Flow<Result<VideoDetails>> = flow {
        try {
            val token = tokenManager.accessToken
            if (token == null) {
                emit(Result.failure(Exception("User not authenticated")))
                return@flow
            }

            val response = api.getVideoDetailsById(videoId, "Bearer $token")

            if (response.isSuccessful) {
                val body = response.body()
                if (body?.success == true) {
                    val videoDetails = body.data.toVideoDetails()
                    emit(Result.success(videoDetails))
                } else {
                    emit(Result.failure(Exception(body?.message ?: "Unknown error")))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                emit(Result.failure(Exception("Failed to fetch video details: $errorBody")))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting video details", e)
            emit(Result.failure(e))
        }
    }

    override suspend fun getCourseBasicDetails(courseId: Int): Flow<Result<CourseBasicDetails>> =
        flow {
            try {
                val token = tokenManager.accessToken
                if (token.isNullOrEmpty()) {
                    Log.d(TAG, "No access token available")
                    emit(Result.failure(Exception("Not authenticated")))
                    return@flow
                }

                val response = api.getCourseBasicDetails(
                    courseId = courseId,
                    authToken = "Bearer $token"
                )

                if (response.isSuccessful) {
                    val courseDetails = response.body()?.data?.toCourseBasicDetails()
                    if (courseDetails != null) {
                        Log.d(TAG, "Successfully fetched course details for course $courseId")
                        emit(Result.success(courseDetails))
                    } else {
                        Log.e(TAG, "Failed to parse course details response")
                        emit(Result.failure(Exception("Failed to parse course details")))
                    }
                } else {
                    val errorMessage =
                        "Failed to fetch course details: ${response.code()} - ${response.message()}"
                    Log.e(TAG, errorMessage)
                    emit(Result.failure(Exception(errorMessage)))
                }
            } catch (e: IOException) {
                val errorMessage = "Network error while fetching course details"
                Log.e(TAG, errorMessage, e)
                emit(Result.failure(Exception(errorMessage, e)))
            } catch (e: HttpException) {
                val errorMessage = "HTTP error while fetching course details"
                Log.e(TAG, errorMessage, e)
                emit(Result.failure(Exception(errorMessage, e)))
            } catch (e: Exception) {
                val errorMessage = "Unexpected error while fetching course details"
                Log.e(TAG, errorMessage, e)
                emit(Result.failure(Exception(errorMessage, e)))
            }
        }

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