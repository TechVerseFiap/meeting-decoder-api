package br.com.meetingdecoder.application.ports.product;

import br.com.meetingdecoder.application.dto.produto.ProdutoOutput;
import br.com.meetingdecoder.application.shared.result.Result;

import java.util.UUID;

public interface IFindProdutoByIdUseCase {
    Result<ProdutoOutput> execute(UUID produtoId);
}
