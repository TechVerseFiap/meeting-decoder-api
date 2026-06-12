package br.com.meetingdecoder.infrastructure.persistence.jpa;

import br.com.meetingdecoder.infrastructure.persistence.entity.SellerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaSellerRepository extends JpaRepository<SellerEntity, UUID> {

    boolean existsByName(String name);

    Optional<SellerEntity> findByEmail(String email);
}
