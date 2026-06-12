package br.com.meetingdecoder.application.service;

import br.com.meetingdecoder.application.service.insight.FindInsightByIdUseCase;
import br.com.meetingdecoder.application.service.insight.FindInsightsByTranscricaoUseCase;
import br.com.meetingdecoder.application.service.insight.FindInsightsPrioritariosParaVendedorUseCase;
import br.com.meetingdecoder.application.service.insight.FindRiscosChurnAtivosUseCase;
import br.com.meetingdecoder.application.service.insighttag.FindInsightTagByIdUseCase;
import br.com.meetingdecoder.application.service.insighttag.FindInsightTagByNameUseCase;
import br.com.meetingdecoder.application.service.insighttag.ListInsightTagsUseCase;
import br.com.meetingdecoder.application.service.product.FindProdutoByIdUseCase;
import br.com.meetingdecoder.application.service.product.FindProdutoByNameUseCase;
import br.com.meetingdecoder.application.service.product.FindProdutosByCategoriaUseCase;
import br.com.meetingdecoder.application.service.product.FindProdutosByLinhaUseCase;
import br.com.meetingdecoder.domain.insight.repository.IInsightRepository;
import br.com.meetingdecoder.domain.insight.repository.IInsightTagRepository;
import br.com.meetingdecoder.domain.insight.repository.IProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static br.com.meetingdecoder.support.TestFixtures.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class QueryUseCasesTest {
    private IInsightRepository insightRepository;
    private IInsightTagRepository tagRepository;
    private IProdutoRepository productRepository;

    @BeforeEach
    void setUp() {
        insightRepository = mock(IInsightRepository.class);
        tagRepository = mock(IInsightTagRepository.class);
        productRepository = mock(IProdutoRepository.class);
    }

    @Test
    void shouldFindInsightByIdOrReturnNotFound() {
        when(insightRepository.findById(any()))
                .thenReturn(Optional.of(insight()), Optional.empty());
        FindInsightByIdUseCase useCase = new FindInsightByIdUseCase(insightRepository);

        assertEquals(INSIGHT_ID, useCase.execute(INSIGHT_ID).getData().id());
        assertTrue(useCase.execute(INSIGHT_ID).isFailure());
    }

    @Test
    void shouldListInsightsByAllSupportedCriteria() {
        when(insightRepository.findByTranscricaoId(any())).thenReturn(List.of(insight()));
        when(insightRepository.findRiscosChurnAtivos(any())).thenReturn(List.of(insight()));
        when(insightRepository.findInsightsPrioritariosParaVendedor(any())).thenReturn(List.of(insight()));

        assertEquals(1, new FindInsightsByTranscricaoUseCase(insightRepository)
                .execute(TRANSCRIPTION_ID).getData().size());
        assertEquals(1, new FindRiscosChurnAtivosUseCase(insightRepository)
                .execute(CLIENT_ID).getData().size());
        assertEquals(1, new FindInsightsPrioritariosParaVendedorUseCase(insightRepository)
                .execute(SELLER_ID).getData().size());
    }

    @Test
    void shouldFindTagByIdAndNameOrReturnNotFound() {
        when(tagRepository.findById(any())).thenReturn(Optional.of(insightTag()), Optional.empty());
        when(tagRepository.findByNome(anyString())).thenReturn(Optional.of(insightTag()), Optional.empty());

        FindInsightTagByIdUseCase byId = new FindInsightTagByIdUseCase(tagRepository);
        FindInsightTagByNameUseCase byName = new FindInsightTagByNameUseCase(tagRepository);

        assertEquals(TAG_ID, byId.execute(TAG_ID).getData().id());
        assertTrue(byId.execute(TAG_ID).isFailure());
        assertEquals("churn", byName.execute("churn").getData().nome());
        assertTrue(byName.execute("missing").isFailure());
    }

    @Test
    void shouldListTagsIncludingEmptyResult() {
        when(tagRepository.findAll()).thenReturn(List.of(insightTag()), List.of());
        ListInsightTagsUseCase useCase = new ListInsightTagsUseCase(tagRepository);

        assertEquals(1, useCase.execute().getData().size());
        assertTrue(useCase.execute().getData().isEmpty());
    }

    @Test
    void shouldFindProductByIdAndNameOrReturnNotFound() {
        when(productRepository.findById(any())).thenReturn(Optional.of(produto()), Optional.empty());
        when(productRepository.findByNome(anyString())).thenReturn(Optional.of(produto()), Optional.empty());

        FindProdutoByIdUseCase byId = new FindProdutoByIdUseCase(productRepository);
        FindProdutoByNameUseCase byName = new FindProdutoByNameUseCase(productRepository);

        assertEquals(PRODUCT_ID, byId.execute(PRODUCT_ID).getData().id());
        assertTrue(byId.execute(PRODUCT_ID).isFailure());
        assertEquals("Decoder Pro", byName.execute("Decoder Pro").getData().nome());
        assertTrue(byName.execute("missing").isFailure());
    }

    @Test
    void shouldListProductsByCategoryAndLine() {
        when(productRepository.findByCategoria("Software")).thenReturn(List.of(produto()));
        when(productRepository.findByLinha("Enterprise")).thenReturn(List.of(produto()));

        assertEquals(1, new FindProdutosByCategoriaUseCase(productRepository)
                .execute("Software").getData().size());
        assertEquals(1, new FindProdutosByLinhaUseCase(productRepository)
                .execute("Enterprise").getData().size());
    }
}
