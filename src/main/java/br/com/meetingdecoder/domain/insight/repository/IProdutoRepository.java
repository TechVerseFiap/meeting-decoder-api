package br.com.meetingdecoder.domain.insight.repository;

import br.com.meetingdecoder.domain.insight.model.Produto;
import br.com.meetingdecoder.domain.insight.valueobject.ProdutoId;

import java.util.List;
import java.util.Optional;

/**
 * Domain port for reading and querying products (services) from the repository.
 * Implementations provide access to the product catalog.
 */
public interface IProdutoRepository {
    /**
     * Finds a product by its unique identifier.
     *
     * @param id the product identifier
     * @return the product if found
     */
    Optional<Produto> findById(ProdutoId id);

    /**
     * Finds a product by its name.
     * Business rule: product names are unique.
     *
     * @param nome the product name
     * @return the product if found
     */
    Optional<Produto> findByNome(String nome);

    /**
     * Finds all products in a given category.
     *
     * @param categoria the category to search
     * @return a list of products in that category (may be empty)
     */
    List<Produto> findByCategoria(String categoria);

    /**
     * Finds all products in a given product line.
     *
     * @param linha the product line to search
     * @return a list of products in that line (may be empty)
     */
    List<Produto> findByLinha(String linha);
}
