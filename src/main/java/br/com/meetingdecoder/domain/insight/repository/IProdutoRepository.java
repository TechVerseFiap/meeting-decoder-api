package br.com.meetingdecoder.domain.insight.repository;

import br.com.meetingdecoder.domain.insight.model.Produto;
import br.com.meetingdecoder.domain.insight.valueobject.ProdutoId;

import java.util.List;
import java.util.Optional;

public interface IProdutoRepository {
    Optional<Produto> findById(ProdutoId id);

    Optional<Produto> findByNome(String nome);

    List<Produto> findByCategoria(String categoria);

    List<Produto> findByLinha(String linha);
}
