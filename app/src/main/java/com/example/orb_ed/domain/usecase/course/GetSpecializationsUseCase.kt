package com.example.orb_ed.domain.usecase.course

import com.example.orb_ed.domain.model.Specialization
import com.example.orb_ed.domain.repository.CourseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSpecializationsUseCase @Inject constructor(
    private val repository: CourseRepository
) {
    suspend operator fun invoke(programId: Int): Flow<List<Specialization>> =
        repository.getSpecializations(programId)
}
