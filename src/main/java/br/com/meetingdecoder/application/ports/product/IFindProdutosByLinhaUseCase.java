package br.com.meetingdecoder.application.ports.product;

import br.com.meetingdecoder.application.dto.produto.ProdutoOutput;
import br.com.meetingdecoder.application.shared.result.Result;

import java.util.List;

public interface IFindProdutosByLinhaUseCase {
    Result<List<ProdutoOutput>> execute(String linha);
}
