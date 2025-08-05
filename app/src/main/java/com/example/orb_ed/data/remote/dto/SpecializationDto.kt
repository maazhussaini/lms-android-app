package com.example.orb_ed.data.remote.dto

import com.example.orb_ed.domain.model.Specialization
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpecializationDto(
    @SerialName("specialization_id")
    val id: Int,
    @SerialName("program_id")
    val programId: Int,
    @SerialName("specialization_name")
    val name: String,
    @SerialName("specialization_thumbnail_url")
    val thumbnailUrl: String?
) {
    fun toSpecialization(): Specialization {
        return Specialization(
            id = id,
            name = name,
            programId = programId,
            thumbnailUrl = thumbnailUrl
        )
    }
}

@Serializable
data class SpecializationsData(
    @SerialName("items")
    val items: List<SpecializationDto>,
    @SerialName("pagination")
    val pagination: Pagination
)

typealias SpecializationsResponse = ApiResponse<SpecializationsData>
