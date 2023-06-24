package com.github.diegoberaldin.metaphrase.domain.project.persistence.dao

import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import com.github.diegoberaldin.metaphrase.domain.project.data.TranslationUnitTypeFilter

/**
 * Contract for the segment data access object.
 */
interface SegmentDao {
    /**
     * Create a new segment.
     *
     * @param model segment to creeate
     * @param languageId Language ID
     * @return ID of the newly created segment
     */
    suspend fun create(model: SegmentModel, languageId: Int): Int

    /**
     * Create multiple segments in a single transaction.
     *
     * @param models segments to create
     * @param languageId Language ID
     */
    suspend fun createBatch(models: List<SegmentModel>, languageId: Int)

    /**
     * Update a segment.
     *
     * @param model segment to update
     */
    suspend fun update(model: SegmentModel)

    /**
     * Delete a segment.
     *
     * @param model segment to delete
     */
    suspend fun delete(model: SegmentModel)

    /**
     * Get all segments for a language within a project.
     *
     * @param languageId Language ID
     * @return list of segments of that language
     */
    suspend fun getAll(languageId: Int): List<SegmentModel>

    /**
     * Get all the untranslatable segments for a language within a project.
     *
     * @param languageId Language ID
     * @return list of non-translatable (source only) segments
     */
    suspend fun getUntranslatable(languageId: Int): List<SegmentModel>

    /**
     * Get the list of segments corresponding to some search criteria.
     *
     * @param languageId target language ID
     * @param baseLanguageId source language ID
     * @param filter message type filter
     * @param search textual query
     * @param skip offset for pagination
     * @param limit page size for pagination, if set to `0` no pagination will be performed
     * @return list of segment corresponding to the criteria
     */
    suspend fun search(
        languageId: Int,
        baseLanguageId: Int = 0,
        filter: TranslationUnitTypeFilter = TranslationUnitTypeFilter.ALL,
        search: String? = null,
        skip: Int = 0,
        limit: Int = 0,
    ): List<SegmentModel>

    /**
     * Get a segment by ID.
     *
     * @param id segment ID
     * @return [SegmentModel] or null
     */
    suspend fun getById(id: Int): SegmentModel?

    /**
     * Get a segment by key given its language within a project. There can only be at most one segment
     * with a given key for any given language (compound index).
     *
     * @param key segment key
     * @param languageId Language ID
     * @return [SegmentModel] or null
     */
    suspend fun getByKey(key: String, languageId: Int): SegmentModel?
}
