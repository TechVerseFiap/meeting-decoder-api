package br.com.meetingdecoder.application.ports.out;

import br.com.meetingdecoder.application.dto.QueryOptions;
import br.com.meetingdecoder.domain.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IProductRepository {
    Product save(Product product);
    boolean existsById(UUID id);
    boolean existsByName(String name);
    Optional<Product> findById(UUID id);
    List<Product> findAll(QueryOptions queryOptions);
    void deleteById(UUID id);
}
