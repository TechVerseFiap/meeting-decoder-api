package br.com.meetingdecoder.infrastructure.config;

import br.com.meetingdecoder.application.ports.client.ICreateClientUseCase;
import br.com.meetingdecoder.application.ports.client.IDeleteClientUseCase;
import br.com.meetingdecoder.application.ports.client.IFindClientByIdUseCase;
import br.com.meetingdecoder.application.ports.client.IUpdateClientUseCase;
import br.com.meetingdecoder.application.ports.insight.IFindActiveChurnRisksUseCase;
import br.com.meetingdecoder.application.ports.insight.IFindInsightByIdUseCase;
import br.com.meetingdecoder.application.ports.insight.IFindInsightsByTranscriptionUseCase;
import br.com.meetingdecoder.application.ports.insight.IFindPriorityInsightsForSellerUseCase;
import br.com.meetingdecoder.application.ports.insighttag.IFindInsightTagByIdUseCase;
import br.com.meetingdecoder.application.ports.insighttag.IFindInsightTagByNameUseCase;
import br.com.meetingdecoder.application.ports.insighttag.IListInsightTagsUseCase;
import br.com.meetingdecoder.application.ports.meeting.ICreateMeetingUseCase;
import br.com.meetingdecoder.application.ports.meeting.IFindMeetingByIdUseCase;
import br.com.meetingdecoder.application.ports.meeting.IUpdateMeetingUseCase;
import br.com.meetingdecoder.application.ports.product.IFindProdutoByIdUseCase;
import br.com.meetingdecoder.application.ports.product.IFindProdutoByNameUseCase;
import br.com.meetingdecoder.application.ports.product.IFindProdutosByCategoriaUseCase;
import br.com.meetingdecoder.application.ports.product.IFindProdutosByLinhaUseCase;
import br.com.meetingdecoder.application.ports.seller.IActivateSellerUseCase;
import br.com.meetingdecoder.application.ports.seller.ICreateSellerUseCase;
import br.com.meetingdecoder.application.ports.seller.IDeactivateSellerUseCase;
import br.com.meetingdecoder.application.ports.seller.IFindSellerByIdUseCase;
import br.com.meetingdecoder.application.ports.seller.IUpdateSellerUseCase;
import br.com.meetingdecoder.application.ports.transcription.ICreateTranscriptionUseCase;
import br.com.meetingdecoder.application.ports.transcription.IFindTranscriptionByIdUseCase;
import br.com.meetingdecoder.application.ports.transcription.IUpdateTranscriptionUseCase;

import br.com.meetingdecoder.application.service.client.CreateClientUseCase;
import br.com.meetingdecoder.application.service.client.DeleteClientUseCase;
import br.com.meetingdecoder.application.service.client.FindClientByIdUseCase;
import br.com.meetingdecoder.application.service.client.UpdateClientUseCase;
import br.com.meetingdecoder.application.service.insight.FindInsightByIdUseCase;
import br.com.meetingdecoder.application.service.insight.FindInsightsByTranscricaoUseCase;
import br.com.meetingdecoder.application.service.insight.FindInsightsPrioritariosParaVendedorUseCase;
import br.com.meetingdecoder.application.service.insight.FindRiscosChurnAtivosUseCase;
import br.com.meetingdecoder.application.service.insighttag.FindInsightTagByIdUseCase;
import br.com.meetingdecoder.application.service.insighttag.FindInsightTagByNameUseCase;
import br.com.meetingdecoder.application.service.insighttag.ListInsightTagsUseCase;
import br.com.meetingdecoder.application.service.meeting.CreateMeetingUseCase;
import br.com.meetingdecoder.application.service.meeting.FindMeetingByIdUseCase;
import br.com.meetingdecoder.application.service.meeting.UpdateMeetingUseCase;
import br.com.meetingdecoder.application.service.product.FindProdutoByIdUseCase;
import br.com.meetingdecoder.application.service.product.FindProdutoByNameUseCase;
import br.com.meetingdecoder.application.service.product.FindProdutosByCategoriaUseCase;
import br.com.meetingdecoder.application.service.product.FindProdutosByLinhaUseCase;
import br.com.meetingdecoder.application.service.seller.ActivateSellerUseCase;
import br.com.meetingdecoder.application.service.seller.CreateSellerUseCase;
import br.com.meetingdecoder.application.service.seller.DeactivateSellerUseCase;
import br.com.meetingdecoder.application.service.seller.FindSellerByIdUseCase;
import br.com.meetingdecoder.application.service.seller.UpdateSellerUseCase;
import br.com.meetingdecoder.application.service.transcription.CreateTranscriptionUseCase;
import br.com.meetingdecoder.application.service.transcription.FindTranscriptionByIdUseCase;
import br.com.meetingdecoder.application.service.transcription.UpdateTranscriptionUseCase;

import br.com.meetingdecoder.domain.insight.repository.IInsightRepository;
import br.com.meetingdecoder.domain.insight.repository.IInsightTagRepository;
import br.com.meetingdecoder.domain.insight.repository.IProdutoRepository;
import br.com.meetingdecoder.domain.sale.repository.IClientRepository;
import br.com.meetingdecoder.domain.sale.repository.ISellerRepository;
import br.com.meetingdecoder.domain.transcription.repository.IMeetingRepository;
import br.com.meetingdecoder.domain.transcription.repository.ITranscriptionRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseBeanConfiguration {

    @Bean
    public ICreateClientUseCase createClientUseCase(IClientRepository repository) {
        return new CreateClientUseCase(repository);
    }

    @Bean
    public IUpdateClientUseCase updateClientUseCase(IClientRepository repository) {
        return new UpdateClientUseCase(repository);
    }

    @Bean
    public IFindClientByIdUseCase findClientByIdUseCase(IClientRepository repository) {
        return new FindClientByIdUseCase(repository);
    }

    @Bean
    public IDeleteClientUseCase deleteClientUseCase(IClientRepository repository) {
        return new DeleteClientUseCase(repository);
    }

    @Bean
    public ICreateSellerUseCase createSellerUseCase(ISellerRepository repository) {
        return new CreateSellerUseCase(repository);
    }

    @Bean
    public IUpdateSellerUseCase updateSellerUseCase(ISellerRepository repository) {
        return new UpdateSellerUseCase(repository);
    }

    @Bean
    public IFindSellerByIdUseCase findSellerByIdUseCase(ISellerRepository repository) {
        return new FindSellerByIdUseCase(repository);
    }

    @Bean
    public IActivateSellerUseCase activateSellerUseCase(ISellerRepository repository) {
        return new ActivateSellerUseCase(repository);
    }

    @Bean
    public IDeactivateSellerUseCase deactivateSellerUseCase(ISellerRepository repository) {
        return new DeactivateSellerUseCase(repository);
    }

    @Bean
    public ICreateMeetingUseCase createMeetingUseCase(IMeetingRepository repository) {
        return new CreateMeetingUseCase(repository);
    }

    @Bean
    public IUpdateMeetingUseCase updateMeetingUseCase(IMeetingRepository repository) {
        return new UpdateMeetingUseCase(repository);
    }

    @Bean
    public IFindMeetingByIdUseCase findMeetingByIdUseCase(IMeetingRepository repository) {
        return new FindMeetingByIdUseCase(repository);
    }

    @Bean
    public ICreateTranscriptionUseCase createTranscriptionUseCase(ITranscriptionRepository repository) {
        return new CreateTranscriptionUseCase(repository);
    }

    @Bean
    public IUpdateTranscriptionUseCase updateTranscriptionUseCase(ITranscriptionRepository repository) {
        return new UpdateTranscriptionUseCase(repository);
    }

    @Bean
    public IFindTranscriptionByIdUseCase findTranscriptionByIdUseCase(ITranscriptionRepository repository) {
        return new FindTranscriptionByIdUseCase(repository);
    }

    @Bean
    public IFindInsightByIdUseCase findInsightByIdUseCase(IInsightRepository repository) {
        return new FindInsightByIdUseCase(repository);
    }

    @Bean
    public IFindInsightsByTranscriptionUseCase findInsightsByTranscricaoUseCase(IInsightRepository repository) {
        return new FindInsightsByTranscricaoUseCase(repository);
    }

    @Bean
    public IFindActiveChurnRisksUseCase findRiscosChurnAtivosUseCase(IInsightRepository repository) {
        return new FindRiscosChurnAtivosUseCase(repository);
    }

    @Bean
    public IFindPriorityInsightsForSellerUseCase findInsightsPrioritariosParaVendedorUseCase(IInsightRepository repository) {
        return new FindInsightsPrioritariosParaVendedorUseCase(repository);
    }

    @Bean
    public IFindInsightTagByIdUseCase findInsightTagByIdUseCase(IInsightTagRepository repository) {
        return new FindInsightTagByIdUseCase(repository);
    }

    @Bean
    public IFindInsightTagByNameUseCase findInsightTagByNameUseCase(IInsightTagRepository repository) {
        return new FindInsightTagByNameUseCase(repository);
    }

    @Bean
    public IListInsightTagsUseCase listInsightTagsUseCase(IInsightTagRepository repository) {
        return new ListInsightTagsUseCase(repository);
    }

    @Bean
    public IFindProdutoByIdUseCase findProdutoByIdUseCase(IProdutoRepository repository) {
        return new FindProdutoByIdUseCase(repository);
    }

    @Bean
    public IFindProdutoByNameUseCase findProdutoByNameUseCase(IProdutoRepository repository) {
        return new FindProdutoByNameUseCase(repository);
    }

    @Bean
    public IFindProdutosByCategoriaUseCase findProdutosByCategoriaUseCase(IProdutoRepository repository) {
        return new FindProdutosByCategoriaUseCase(repository);
    }

    @Bean
    public IFindProdutosByLinhaUseCase findProdutosByLinhaUseCase(IProdutoRepository repository) {
        return new FindProdutosByLinhaUseCase(repository);
    }
}