package com.example.orb_ed.domain.repository

import com.example.orb_ed.domain.model.ApiResult
import kotlinx.coroutines.flow.Flow

/**
 * A generic interface for repository operations.
 */
interface BaseRepository<T, in ID> {
    
    /**
     * Fetches all items.
     * @return A flow of [ApiResult] containing a list of items or an error.
     */
    suspend fun getAll(): Flow<ApiResult<List<T>>>
    
    /**
     * Fetches an item by its ID.
     * @param id The ID of the item to fetch.
     * @return A flow of [ApiResult] containing the item or an error.
     */
    suspend fun getById(id: ID): Flow<ApiResult<T>>
    
    /**
     * Creates a new item.
     * @param item The item to create.
     * @return A flow of [ApiResult] containing the created item or an error.
     */
    suspend fun create(item: T): Flow<ApiResult<T>>
    
    /**
     * Updates an existing item.
     * @param id The ID of the item to update.
     * @param item The updated item data.
     * @return A flow of [ApiResult] containing the updated item or an error.
     */
    suspend fun update(id: ID, item: T): Flow<ApiResult<T>>
    
    /**
     * Deletes an item by its ID.
     * @param id The ID of the item to delete.
     * @return A flow of [ApiResult] indicating success or failure.
     */
    suspend fun delete(id: ID): Flow<ApiResult<Unit>>
}
