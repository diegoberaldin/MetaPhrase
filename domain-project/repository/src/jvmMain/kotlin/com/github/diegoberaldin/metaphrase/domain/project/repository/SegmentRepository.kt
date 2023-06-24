package com.github.diegoberaldin.metaphrase.domain.project.repository

import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import com.github.diegoberaldin.metaphrase.domain.project.data.TranslationUnitTypeFilter

/**
 * Contract for the segment repository.
 */
interface SegmentRepository {
    /**
     * Create a new segment.
     *
     * @param model Segment to create
     * @param languageId Language ID
     * @return ID of the newly created segment
     */
    suspend fun create(model: SegmentModel, languageId: Int): Int

    /**
     * Create multiple segments in a single transaction.
     *
     * @param models Segments to create
     * @param languageId Language ID
     */
    suspend fun createBatch(models: List<SegmentModel>, languageId: Int)

    /**
     * Update a segment.
     *
     * @param model Segment to update
     */
    suspend fun update(model: SegmentModel)

    /**
     * Delete a segment.
     *
     * @param model Segment to delete
     */
    suspend fun delete(model: SegmentModel)

    /**
     * Get all segments for a given language within a given project.
     *
     * @param languageId Language ID
     * @return list of segments
     */
    suspend fun getAll(languageId: Int): List<SegmentModel>

    /**
     * Get all untranslatable (source only) segments.
     *
     * @param languageId Language ID
     * @return list of segments
     */
    suspend fun getUntranslatable(languageId: Int): List<SegmentModel>

    /**
     * Get all the segments corresponding to a set of search criteria.
     *
     * @param languageId Target language ID
     * @param baseLanguageId Source language ID
     * @param filter Message type filter
     * @param search Textual query (on the key, source text or target text)
     * @param skip offset for pagination
     * @param limit page size for pagination, if set to `0` pagination is ignored
     * @return the list of matching segments
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
     * Get a segment by its ID.
     *
     * @param id Segment ID
     * @return [SegmentModel] or null if no such segment exists
     */
    suspend fun getById(id: Int): SegmentModel?

    /**
     * Get a segment by its key.
     *
     * @param key Message key
     * @param languageId Language ID
     * @return [SegmentModel] or null if no such segment exists
     */
    suspend fun getByKey(key: String, languageId: Int): SegmentModel?
}
