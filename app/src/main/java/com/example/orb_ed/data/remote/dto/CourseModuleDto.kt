package com.example.orb_ed.data.remote.dto

import com.example.orb_ed.domain.model.CourseModule
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CourseModuleDto(
    @SerialName("course_module_id")
    val id: Int,
    @SerialName("course_module_name")
    val name: String,
    @SerialName("module_stats")
    val stats: String
) {
    fun toCourseModule(): CourseModule {
        return CourseModule(
            id = id,
            name = name,
            stats = stats
        )
    }
}

@Serializable
data class CourseModulesData(
    @SerialName("items")
    val items: List<CourseModuleDto>,
    @SerialName("pagination")
    val pagination: Pagination
)

typealias CourseModulesResponse = ApiResponse<CourseModulesData>
