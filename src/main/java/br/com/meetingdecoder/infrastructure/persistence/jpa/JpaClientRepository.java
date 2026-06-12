package br.com.meetingdecoder.infrastructure.persistence.jpa;

import br.com.meetingdecoder.infrastructure.persistence.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaClientRepository extends JpaRepository<ClientEntity, UUID> {

    boolean existsByCorporateReason(String name);

    Optional<ClientEntity> findByCnpj(String cnpj);
}
