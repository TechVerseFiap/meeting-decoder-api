package br.com.meetingdecoder.infrastructure.persistence.jpa;

import br.com.meetingdecoder.infrastructure.persistence.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaProdutoRepository extends JpaRepository<ProdutoEntity, UUID> {

    Optional<ProdutoEntity> findByNome(String nome);

    List<ProdutoEntity> findAllByCategoria(String categoria);

    List<ProdutoEntity> findAllByLinha(String linha);
}
