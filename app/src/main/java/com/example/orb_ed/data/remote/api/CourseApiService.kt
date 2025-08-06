package com.example.orb_ed.data.remote.api

import com.example.orb_ed.data.remote.dto.CourseBasicDetailsApiResponse
import com.example.orb_ed.data.remote.dto.CourseModulesResponse
import com.example.orb_ed.data.remote.dto.CourseTopicsResponse
import com.example.orb_ed.data.remote.dto.DiscoveredCoursesResponse
import com.example.orb_ed.data.remote.dto.EnrolledCoursesResponse
import com.example.orb_ed.data.remote.dto.ProgramsResponse
import com.example.orb_ed.data.remote.dto.SpecializationsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface CourseApiService {
    @GET("courses/{courseId}/modules")
    suspend fun getCourseModules(
        @Path("courseId") courseId: Int,
        @Header("Authorization") authToken: String
    ): Response<CourseModulesResponse>

    @GET("modules/{moduleId}/topics")
    suspend fun getCourseTopicsByModule(
        @Path("moduleId") moduleId: Int,
        @Header("Authorization") authToken: String
    ): Response<CourseTopicsResponse>
    
    /*@GET("courses/enrolled")
    suspend fun getEnrolledCourses(): Response<ApiResponse<CoursesData>>
    
    @GET("courses/recommended")
    suspend fun getRecommendedCourses(): Response<ApiResponse<CoursesData>>*/

    @GET("programs/tenant/list")
    suspend fun getPrograms(
        @Query("is_active") isActive: Boolean = true,
        @Header("Authorization") authToken: String
    ): Response<ProgramsResponse>

    /*@GET("search/courses")
    suspend fun searchCourses(
        @Query("q") query: String
    ): Response<ApiResponse<SearchData>>*/

    @GET("student/profile/courses/discover")
    suspend fun getDiscoverCourses(
        @Query("course_type") courseType: String? = null,
        @Query("program_id") programId: Int? = null,
        @Query("specialization_id") specializationId: Int? = null,
        @Query("search_query") searchQuery: String? = null,
        @Header("Authorization") authToken: String
    ): Response<DiscoveredCoursesResponse>

    @GET("specializations/by-program")
    suspend fun getSpecializations(
        @Query("program_id") programId: Int,
        @Header("Authorization") authToken: String
    ): Response<SpecializationsResponse>

    @GET("student/profile/enrollments")
    suspend fun getEnrolledCourses(
        @Query("search_query") searchQuery: String? = null,
        @Query("enrollment_status") enrollmentStatus: String = "ACTIVE",
        @Query("include_progress") includeProgress: Boolean = true,
        @Header("Authorization") authToken: String
    ): Response<EnrolledCoursesResponse>

    @GET("student/profile/courses/{courseId}/basic-details")
    suspend fun getCourseBasicDetails(
        @Path("courseId") courseId: Int,
        @Header("Authorization") authToken: String
    ): Response<CourseBasicDetailsApiResponse>
}

/*data class CoursesData(
    val courses: List<CourseDto>
)

data class SearchData(
    val results: List<CourseDto>
)

data class SearchResponse(
    val results: List<CourseDto>
)*/
