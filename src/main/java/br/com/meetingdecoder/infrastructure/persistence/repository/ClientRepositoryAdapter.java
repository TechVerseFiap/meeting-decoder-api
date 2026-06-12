package br.com.meetingdecoder.infrastructure.persistence.repository;

import br.com.meetingdecoder.domain.sale.model.Client;
import br.com.meetingdecoder.domain.sale.repository.IClientRepository;
import br.com.meetingdecoder.domain.sale.valueobject.BillingRange;
import br.com.meetingdecoder.domain.sale.valueobject.ClientId;
import br.com.meetingdecoder.domain.sale.valueobject.NpsSnapshot;
import br.com.meetingdecoder.infrastructure.persistence.entity.ClientEntity;
import br.com.meetingdecoder.infrastructure.persistence.jpa.JpaClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClientRepositoryAdapter implements IClientRepository {

    private final JpaClientRepository jpa;

    @Override
    public Client save(Client client) {
        ClientEntity entity = toEntity(client);
        ClientEntity saved = jpa.save(entity);
        return toDomain(saved);
    }

    @Override
    public boolean existsById(ClientId id) {
        return jpa.existsById(id.value());
    }

    @Override
    public boolean existsByName(String name) {
        return jpa.existsByCorporateReason(name);
    }

    @Override
    public List<Client> findAll() {
        return jpa.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Optional<Client> findById(ClientId id) {
        return jpa.findById(id.value())
                .map(this::toDomain);
    }

    @Override
    public void deleteById(ClientId id) {
        jpa.deleteById(id.value());
    }

    private ClientEntity toEntity(Client client) {
        return ClientEntity.builder()
                .id(client.id())
                .externalId(client.externalId())
                .corporateReason(client.corporateReason())
                .fantasyName(client.fantasyName())
                .cnpj(client.cnpj())
                .cnae(client.cnae())
                .segment(client.segment())
                .size(client.size())
                .city(client.city())
                .state(client.state())
                .country(client.country())
                .type(client.type())
                .billingMin(client.minBilling())
                .billingMax(client.maxBilling())
                .npsNote(client.npsNote())
                .npsDate(client.npsDate())
                .npsCategory(client.npsCategory())
                .createdAt(client.createdAt())
                .updatedAt(client.updatedAt())
                .build();
    }

    private Client toDomain(ClientEntity entity) {
        NpsSnapshot nps = (entity.getNpsNote() != null && entity.getNpsDate() != null)
                ? NpsSnapshot.of(entity.getNpsNote(), entity.getNpsDate())
                : null;

        return Client.create(
                ClientId.of(entity.getId()),
                entity.getExternalId(),
                entity.getCorporateReason(),
                entity.getFantasyName(),
                entity.getCnpj(),
                entity.getCnae(),
                entity.getSegment(),
                entity.getCity(),
                entity.getState(),
                entity.getCountry(),
                entity.getType(),
                BillingRange.of(entity.getBillingMin(), entity.getBillingMax()),
                nps
        );
    }
}
