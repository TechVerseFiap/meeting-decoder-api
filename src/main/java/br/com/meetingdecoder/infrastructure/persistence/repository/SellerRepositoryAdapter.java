package br.com.meetingdecoder.infrastructure.persistence.repository;

import br.com.meetingdecoder.domain.sale.enums.SellerType;
import br.com.meetingdecoder.domain.sale.model.Seller;
import br.com.meetingdecoder.domain.sale.repository.ISellerRepository;
import br.com.meetingdecoder.domain.sale.valueobject.Email;
import br.com.meetingdecoder.domain.sale.valueobject.SellerId;
import br.com.meetingdecoder.infrastructure.persistence.entity.SellerEntity;
import br.com.meetingdecoder.infrastructure.persistence.jpa.JpaSellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SellerRepositoryAdapter implements ISellerRepository {

    private final JpaSellerRepository jpa;

    @Override
    public Seller save(Seller seller) {
        SellerEntity entity = toEntity(seller);
        SellerEntity saved = jpa.save(entity);
        return toDomain(saved);
    }

    @Override
    public boolean existsById(SellerId id) {
        return jpa.existsById(id.value());
    }

    @Override
    public boolean existsByName(String name) {
        return jpa.existsByName(name);
    }

    @Override
    public List<Seller> findAll() {
        return jpa.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Optional<Seller> findById(SellerId id) {
        return jpa.findById(id.value())
                .map(this::toDomain);
    }

    @Override
    public void deleteById(SellerId id) {
        jpa.deleteById(id.value());
    }

    private SellerEntity toEntity(Seller seller) {
        return SellerEntity.builder()
                .id(seller.id())
                .managerId(seller.managerId())
                .type(seller.type())
                .name(seller.name())
                .email(seller.email())
                .active(seller.active())
                .createdAt(seller.createdAt())
                .updatedAt(seller.updatedAt())
                .build();
    }

    private Seller toDomain(SellerEntity entity) {
        SellerId managerId = entity.getManagerId() != null
                ? SellerId.of(entity.getManagerId())
                : null;

        return Seller.create(
                SellerId.of(entity.getId()),
                managerId,
                entity.getType(),
                entity.getName(),
                Email.of(entity.getEmail())
        );
    }
}
