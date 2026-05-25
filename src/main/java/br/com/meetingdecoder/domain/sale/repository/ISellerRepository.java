package br.com.meetingdecoder.domain.sale.repository;

import br.com.meetingdecoder.domain.sale.model.Seller;
import br.com.meetingdecoder.domain.sale.valueobject.SellerId;

import java.util.List;
import java.util.Optional;

public interface ISellerRepository {
    Seller save(Seller seller);
    boolean existsById(SellerId id);
    boolean existsByName(String name);
    List<Seller> findAll();
    Optional<Seller> findById(SellerId id);
    void deleteById(SellerId id);
}
