package com.example.orb_ed.data.remote.dto

import com.example.orb_ed.domain.model.Program
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProgramDto(
    @SerialName("program_id")
    val id: Int,
    @SerialName("program_name")
    val name: String,
    @SerialName("program_thumbnail_url")
    val thumbnailUrl: String?,
    @SerialName("tenant_id")
    val tenantId: Int,
    @SerialName("is_active")
    val isActive: Boolean,
    @SerialName("is_deleted")
    val isDeleted: Boolean,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String
) {
    fun toProgram(): Program {
        return Program(
            id = id,
            name = name,
            thumbnailUrl = thumbnailUrl
        )
    }
}

@Serializable
data class ProgramsData(
    @SerialName("items")
    val items: List<ProgramDto>,
    @SerialName("pagination")
    val pagination: Pagination
)

typealias ProgramsResponse = ApiResponse<ProgramsData>

@Serializable
data class Pagination(
    @SerialName("page")
    val page: Int,
    @SerialName("limit")
    val limit: Int,
    @SerialName("total")
    val total: Int,
    @SerialName("totalPages")
    val totalPages: Int,
    @SerialName("hasNext")
    val hasNext: Boolean,
    @SerialName("hasPrev")
    val hasPrev: Boolean
)
