package br.com.meetingdecoder.application.ports.product;

import br.com.meetingdecoder.application.dto.produto.ProdutoOutput;
import br.com.meetingdecoder.application.shared.result.Result;

public interface IFindProdutoByNameUseCase {
    Result<ProdutoOutput> execute(String nome);
}
