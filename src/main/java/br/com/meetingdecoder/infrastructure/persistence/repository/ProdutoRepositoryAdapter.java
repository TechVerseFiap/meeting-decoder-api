package br.com.meetingdecoder.infrastructure.persistence.repository;

import br.com.meetingdecoder.domain.insight.model.Produto;
import br.com.meetingdecoder.domain.insight.repository.IProdutoRepository;
import br.com.meetingdecoder.domain.insight.valueobject.FaixaPreco;
import br.com.meetingdecoder.domain.insight.valueobject.ProdutoId;
import br.com.meetingdecoder.infrastructure.persistence.entity.ProdutoEntity;
import br.com.meetingdecoder.infrastructure.persistence.jpa.JpaProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProdutoRepositoryAdapter implements IProdutoRepository {

    private final JpaProdutoRepository jpa;

    @Override
    public Optional<Produto> findById(ProdutoId id) {
        return jpa.findById(id.value())
                .map(this::toDomain);
    }

    @Override
    public Optional<Produto> findByNome(String nome) {
        return jpa.findByNome(nome)
                .map(this::toDomain);
    }

    @Override
    public List<Produto> findByCategoria(String categoria) {
        return jpa.findAllByCategoria(categoria).stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<Produto> findByLinha(String linha) {
        return jpa.findAllByLinha(linha).stream()
                .map(this::toDomain)
                .toList();
    }

    private Produto toDomain(ProdutoEntity entity) {
        return Produto.restore(
                ProdutoId.of(entity.getId()),
                entity.getNome(),
                entity.getCategoria(),
                entity.getDescricao(),
                entity.getLinha(),
                (entity.getFaixaPrecoMinimo() != null || entity.getFaixaPrecoMaximo() != null)
                        ? new FaixaPreco(entity.getFaixaPrecoMinimo(), entity.getFaixaPrecoMaximo())
                        : null,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
