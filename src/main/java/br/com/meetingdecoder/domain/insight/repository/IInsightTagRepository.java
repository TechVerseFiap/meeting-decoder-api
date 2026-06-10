package br.com.meetingdecoder.domain.insight.repository;

import br.com.meetingdecoder.domain.insight.model.InsightTag;
import br.com.meetingdecoder.domain.insight.model.InsightTagId;

import java.util.List;
import java.util.Optional;

/**
 * Domain port for reading and querying insight tags from the repository.
 * Tags are classification labels used to organize and filter insights.
 */
public interface IInsightTagRepository {
    /**
     * Finds a tag by its unique identifier.
     *
     * @param id the tag identifier
     * @return the tag if found
     */
    Optional<InsightTag> findById(InsightTagId id);

    /**
     * Finds a tag by its name.
     * Business rule: tag names are unique.
     *
     * @param nome the tag name
     * @return the tag if found
     */
    Optional<InsightTag> findByNome(String nome);

    /**
     * Retrieves all classification tags in the system.
     *
     * @return a list of all tags (may be empty)
     */
    List<InsightTag> findAll();
}
