package br.com.meetingdecoder.infrastructure.web;

import br.com.meetingdecoder.application.ports.client.*;
import br.com.meetingdecoder.application.ports.insight.*;
import br.com.meetingdecoder.application.ports.insighttag.*;
import br.com.meetingdecoder.application.ports.meeting.*;
import br.com.meetingdecoder.application.ports.product.*;
import br.com.meetingdecoder.application.ports.seller.*;
import br.com.meetingdecoder.application.ports.transcription.*;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.shared.exception.DomainValidationException;
import br.com.meetingdecoder.domain.shared.validation.DomainError;
import br.com.meetingdecoder.domain.shared.validation.DomainErrorCode;
import br.com.meetingdecoder.infrastructure.web.controller.*;
import br.com.meetingdecoder.infrastructure.web.handler.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static br.com.meetingdecoder.support.TestFixtures.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class ControllerHttpIntegrationTest {
    private ICreateClientUseCase createClient;
    private IUpdateClientUseCase updateClient;
    private IFindClientByIdUseCase findClient;
    private IDeleteClientUseCase deleteClient;
    private ICreateSellerUseCase createSeller;
    private IUpdateSellerUseCase updateSeller;
    private IFindSellerByIdUseCase findSeller;
    private IActivateSellerUseCase activateSeller;
    private IDeactivateSellerUseCase deactivateSeller;
    private ICreateMeetingUseCase createMeeting;
    private IUpdateMeetingUseCase updateMeeting;
    private IFindMeetingByIdUseCase findMeeting;
    private ICreateTranscriptionUseCase createTranscription;
    private IUpdateTranscriptionUseCase updateTranscription;
    private IFindTranscriptionByIdUseCase findTranscription;
    private IFindInsightByIdUseCase findInsight;
    private IFindInsightsByTranscriptionUseCase findInsightsByTranscription;
    private IFindActiveChurnRisksUseCase findChurnRisks;
    private IFindPriorityInsightsForSellerUseCase findPriorityInsights;
    private IFindInsightTagByIdUseCase findTagById;
    private IFindInsightTagByNameUseCase findTagByName;
    private IListInsightTagsUseCase listTags;
    private IFindProdutoByIdUseCase findProductById;
    private IFindProdutoByNameUseCase findProductByName;
    private IFindProdutosByCategoriaUseCase findProductsByCategory;
    private IFindProdutosByLinhaUseCase findProductsByLine;
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        createClient = mock(ICreateClientUseCase.class);
        updateClient = mock(IUpdateClientUseCase.class);
        findClient = mock(IFindClientByIdUseCase.class);
        deleteClient = mock(IDeleteClientUseCase.class);
        createSeller = mock(ICreateSellerUseCase.class);
        updateSeller = mock(IUpdateSellerUseCase.class);
        findSeller = mock(IFindSellerByIdUseCase.class);
        activateSeller = mock(IActivateSellerUseCase.class);
        deactivateSeller = mock(IDeactivateSellerUseCase.class);
        createMeeting = mock(ICreateMeetingUseCase.class);
        updateMeeting = mock(IUpdateMeetingUseCase.class);
        findMeeting = mock(IFindMeetingByIdUseCase.class);
        createTranscription = mock(ICreateTranscriptionUseCase.class);
        updateTranscription = mock(IUpdateTranscriptionUseCase.class);
        findTranscription = mock(IFindTranscriptionByIdUseCase.class);
        findInsight = mock(IFindInsightByIdUseCase.class);
        findInsightsByTranscription = mock(IFindInsightsByTranscriptionUseCase.class);
        findChurnRisks = mock(IFindActiveChurnRisksUseCase.class);
        findPriorityInsights = mock(IFindPriorityInsightsForSellerUseCase.class);
        findTagById = mock(IFindInsightTagByIdUseCase.class);
        findTagByName = mock(IFindInsightTagByNameUseCase.class);
        listTags = mock(IListInsightTagsUseCase.class);
        findProductById = mock(IFindProdutoByIdUseCase.class);
        findProductByName = mock(IFindProdutoByNameUseCase.class);
        findProductsByCategory = mock(IFindProdutosByCategoriaUseCase.class);
        findProductsByLine = mock(IFindProdutosByLinhaUseCase.class);

        mvc = standaloneSetup(
                new ClientController(createClient, updateClient, findClient, deleteClient),
                new SellerController(createSeller, updateSeller, findSeller, activateSeller, deactivateSeller),
                new MeetingController(createMeeting, updateMeeting, findMeeting),
                new TranscriptionController(createTranscription, updateTranscription, findTranscription),
                new InsightController(findInsight, findInsightsByTranscription, findChurnRisks, findPriorityInsights),
                new InsightTagController(findTagById, findTagByName, listTags),
                new ProdutoController(findProductById, findProductByName, findProductsByCategory, findProductsByLine)
        ).setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    void shouldCreateClientAndMapApplicationFailure() throws Exception {
        when(createClient.execute(any())).thenReturn(
                Result.success(clientOutput()), failure("client")
        );

        mvc.perform(post("/clients").contentType(MediaType.APPLICATION_JSON).content(validClientJson()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(CLIENT_ID.toString()))
                .andExpect(jsonPath("$.corporateReason").value("Meeting Decoder Ltda"));

        mvc.perform(post("/clients").contentType(MediaType.APPLICATION_JSON).content(validClientJson()))
                .andExpect(status().isUnprocessableContent())
                .andExpect(jsonPath("$[0].code").value("NOT_FOUND"));
    }

    @Test
    void shouldValidateClientBillingBeforeCallingUseCase() throws Exception {
        mvc.perform(post("/clients").contentType(MediaType.APPLICATION_JSON).content("""
                {"externalId":"x","corporateReason":"x","fantasyName":"x","cnpj":"x","cnae":"x",
                 "segment":"x","city":"x","state":"x","country":"x","type":"CUSTOMER",
                 "billingMin":500,"billingMax":100}
                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("billingRange"));
        verifyNoInteractions(createClient);
    }

    @Test
    void shouldFindUpdateAndDeleteClientWithSuccessAndErrors() throws Exception {
        when(findClient.execute(CLIENT_ID)).thenReturn(Result.success(clientOutput()), failure("clientId"));
        when(updateClient.execute(any(), any())).thenReturn(Result.success(clientOutput()), failure("client"));
        when(deleteClient.execute(CLIENT_ID)).thenReturn(Result.success(), failure("clientId"));

        mvc.perform(get("/clients/{id}", CLIENT_ID))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(CLIENT_ID.toString()));
        mvc.perform(get("/clients/{id}", CLIENT_ID))
                .andExpect(status().isNotFound()).andExpect(jsonPath("$[0].code").value("NOT_FOUND"));

        mvc.perform(put("/clients/{id}", CLIENT_ID).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"corporateReason\":\"Updated\"}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.fantasyName").value("Meeting Decoder"));
        mvc.perform(put("/clients/{id}", CLIENT_ID).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"corporateReason\":\"Updated\"}"))
                .andExpect(status().isUnprocessableContent());

        mvc.perform(delete("/clients/{id}", CLIENT_ID)).andExpect(status().isNoContent());
        mvc.perform(delete("/clients/{id}", CLIENT_ID))
                .andExpect(status().isNotFound()).andExpect(jsonPath("$[0].field").value("clientId"));
    }

    @Test
    void shouldCreateSellerAndValidateEmail() throws Exception {
        when(createSeller.execute(any()))
                .thenReturn(Result.success(sellerOutput()), failure("seller"))
                .thenThrow(validation(DomainErrorCode.INVALID_EMAIL, "email"));

        mvc.perform(post("/sellers").contentType(MediaType.APPLICATION_JSON).content(validSellerJson()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("ana@example.com"));
        mvc.perform(post("/sellers").contentType(MediaType.APPLICATION_JSON).content(validSellerJson()))
                .andExpect(status().isUnprocessableContent());

        mvc.perform(post("/sellers").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\":\"SELLER\",\"name\":\"Ana\",\"email\":\"invalid\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].code").value("INVALID_EMAIL"));
    }

    @Test
    void shouldFindUpdateActivateAndDeactivateSellerWithErrors() throws Exception {
        when(findSeller.execute(SELLER_ID)).thenReturn(Result.success(sellerOutput()), failure("sellerId"));
        when(updateSeller.execute(any(), any())).thenReturn(Result.success(sellerOutput()), failure("seller"));
        when(activateSeller.execute(SELLER_ID)).thenReturn(Result.success(), failure("sellerId"));
        when(deactivateSeller.execute(SELLER_ID)).thenReturn(Result.success(), failure("sellerId"));

        mvc.perform(get("/sellers/{id}", SELLER_ID)).andExpect(status().isOk());
        mvc.perform(get("/sellers/{id}", SELLER_ID)).andExpect(status().isNotFound());
        mvc.perform(put("/sellers/{id}", SELLER_ID).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated\",\"email\":\"updated@example.com\"}"))
                .andExpect(status().isOk());
        mvc.perform(put("/sellers/{id}", SELLER_ID).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated\"}"))
                .andExpect(status().isUnprocessableContent());
        mvc.perform(patch("/sellers/{id}/activate", SELLER_ID)).andExpect(status().isNoContent());
        mvc.perform(patch("/sellers/{id}/activate", SELLER_ID)).andExpect(status().isNotFound());
        mvc.perform(patch("/sellers/{id}/deactivate", SELLER_ID)).andExpect(status().isNoContent());
        mvc.perform(patch("/sellers/{id}/deactivate", SELLER_ID)).andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateMeetingAndValidatePeriod() throws Exception {
        when(createMeeting.execute(any()))
                .thenReturn(Result.success(meetingOutput()), failure("meeting"))
                .thenThrow(validation(DomainErrorCode.INVALID_FIELD, "startTime"));

        mvc.perform(post("/meetings").contentType(MediaType.APPLICATION_JSON).content(validMeetingJson()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.durationInSeconds").value(3600));
        mvc.perform(post("/meetings").contentType(MediaType.APPLICATION_JSON).content(validMeetingJson()))
                .andExpect(status().isUnprocessableContent());

        mvc.perform(post("/meetings").contentType(MediaType.APPLICATION_JSON).content("""
                {"externalId":"x","meetingDate":"2025-01-15T12:00:00",
                 "startTime":"2099-01-15T11:00:00","endTime":"2099-01-15T12:00:00",
                 "status":"COMPLETED","external":false,"recordingUrl":"https://example.com/a",
                 "sellerId":"%s","clientId":"%s"}
                """.formatted(SELLER_ID, CLIENT_ID)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray());
    }

    @Test
    void shouldFindAndUpdateMeetingWithErrors() throws Exception {
        when(findMeeting.execute(MEETING_ID)).thenReturn(Result.success(meetingOutput()), failure("meetingId"));
        when(updateMeeting.execute(any(), any())).thenReturn(Result.success(meetingOutput()), failure("meeting"));

        mvc.perform(get("/meetings/{id}", MEETING_ID))
                .andExpect(status().isOk()).andExpect(jsonPath("$.status").value("COMPLETED"));
        mvc.perform(get("/meetings/{id}", MEETING_ID)).andExpect(status().isNotFound());
        mvc.perform(put("/meetings/{id}", MEETING_ID).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\":\"CANCELLED\",\"external\":true}"))
                .andExpect(status().isOk());
        mvc.perform(put("/meetings/{id}", MEETING_ID).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\":\"CANCELLED\"}"))
                .andExpect(status().isUnprocessableContent());
    }

    @Test
    void shouldCreateTranscriptionAndValidateInput() throws Exception {
        when(createTranscription.execute(any()))
                .thenReturn(Result.success(transcriptionOutput()), failure("transcription"))
                .thenThrow(validation(DomainErrorCode.INVALID_FIELD, "transcriptJson"));

        mvc.perform(post("/transcriptions").contentType(MediaType.APPLICATION_JSON)
                        .content(validTranscriptionJson()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rankingConfidence").value("HIGH"));
        mvc.perform(post("/transcriptions").contentType(MediaType.APPLICATION_JSON)
                        .content(validTranscriptionJson()))
                .andExpect(status().isUnprocessableContent());

        mvc.perform(post("/transcriptions").contentType(MediaType.APPLICATION_JSON).content("""
                {"meetingId":"%s","rawText":"raw","cleanText":"clean","formattedText":"[]",
                 "confidence":2.0,"processedAt":"2025-01-15T12:00:00",
                 "finishedAt":"2025-01-15T12:01:00"}
                """.formatted(MEETING_ID)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("transcriptJson"));
    }

    @Test
    void shouldFindAndUpdateTranscriptionWithErrors() throws Exception {
        when(findTranscription.execute(TRANSCRIPTION_ID)).thenReturn(
                Result.success(transcriptionOutput()), failure("transcriptionId")
        );
        when(updateTranscription.execute(any(), any())).thenReturn(
                Result.success(transcriptionOutput()), failure("transcription")
        );

        mvc.perform(get("/transcriptions/{id}", TRANSCRIPTION_ID)).andExpect(status().isOk());
        mvc.perform(get("/transcriptions/{id}", TRANSCRIPTION_ID)).andExpect(status().isNotFound());
        mvc.perform(put("/transcriptions/{id}", TRANSCRIPTION_ID).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cleanText\":\"updated\",\"confidence\":0.8}"))
                .andExpect(status().isOk());
        mvc.perform(put("/transcriptions/{id}", TRANSCRIPTION_ID).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cleanText\":\"updated\"}"))
                .andExpect(status().isUnprocessableContent());
    }

    @Test
    void shouldServeAllInsightEndpointsWithSuccessAndErrors() throws Exception {
        when(findInsight.execute(INSIGHT_ID)).thenReturn(Result.success(insightOutput()), failure("Insight"));
        when(findInsightsByTranscription.execute(TRANSCRIPTION_ID))
                .thenReturn(Result.success(List.of(insightOutput())), failure("transcription"));
        when(findChurnRisks.execute(CLIENT_ID))
                .thenReturn(Result.success(List.of(insightOutput())), failure("client"));
        when(findPriorityInsights.execute(SELLER_ID))
                .thenReturn(Result.success(List.of(insightOutput())), failure("seller"));

        mvc.perform(get("/insights/{id}", INSIGHT_ID))
                .andExpect(status().isOk()).andExpect(jsonPath("$.polaridade").value("NEGATIVO"));
        mvc.perform(get("/insights/{id}", INSIGHT_ID)).andExpect(status().isNotFound());
        mvc.perform(get("/insights/transcription/{id}", TRANSCRIPTION_ID))
                .andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(INSIGHT_ID.toString()));
        mvc.perform(get("/insights/transcription/{id}", TRANSCRIPTION_ID))
                .andExpect(status().isUnprocessableContent());
        mvc.perform(get("/insights/churn-risks/client/{id}", CLIENT_ID)).andExpect(status().isOk());
        mvc.perform(get("/insights/churn-risks/client/{id}", CLIENT_ID))
                .andExpect(status().isUnprocessableContent());
        mvc.perform(get("/insights/priority/seller/{id}", SELLER_ID)).andExpect(status().isOk());
        mvc.perform(get("/insights/priority/seller/{id}", SELLER_ID))
                .andExpect(status().isUnprocessableContent());
    }

    @Test
    void shouldServeAllInsightTagEndpointsWithSuccessAndErrors() throws Exception {
        when(listTags.execute()).thenReturn(Result.success(List.of(insightTagOutput())), failure("tags"));
        when(findTagById.execute(TAG_ID)).thenReturn(Result.success(insightTagOutput()), failure("tag"));
        when(findTagByName.execute("churn")).thenReturn(Result.success(insightTagOutput()), failure("tag"));

        mvc.perform(get("/insight-tags"))
                .andExpect(status().isOk()).andExpect(jsonPath("$[0].nome").value("churn"));
        mvc.perform(get("/insight-tags")).andExpect(status().isUnprocessableContent());
        mvc.perform(get("/insight-tags/{id}", TAG_ID)).andExpect(status().isOk());
        mvc.perform(get("/insight-tags/{id}", TAG_ID)).andExpect(status().isNotFound());
        mvc.perform(get("/insight-tags/name/churn")).andExpect(status().isOk());
        mvc.perform(get("/insight-tags/name/churn")).andExpect(status().isNotFound());
    }

    @Test
    void shouldServeAllProductEndpointsWithSuccessAndErrors() throws Exception {
        when(findProductById.execute(PRODUCT_ID)).thenReturn(Result.success(produtoOutput()), failure("product"));
        when(findProductByName.execute("Decoder Pro"))
                .thenReturn(Result.success(produtoOutput()), failure("product"));
        when(findProductsByCategory.execute("Software"))
                .thenReturn(Result.success(List.of(produtoOutput())), failure("category"));
        when(findProductsByLine.execute("Enterprise"))
                .thenReturn(Result.success(List.of(produtoOutput())), failure("line"));

        mvc.perform(get("/produtos/{id}", PRODUCT_ID))
                .andExpect(status().isOk()).andExpect(jsonPath("$.nome").value("Decoder Pro"));
        mvc.perform(get("/produtos/{id}", PRODUCT_ID)).andExpect(status().isNotFound());
        mvc.perform(get("/produtos/name/{name}", "Decoder Pro")).andExpect(status().isOk());
        mvc.perform(get("/produtos/name/{name}", "Decoder Pro")).andExpect(status().isNotFound());
        mvc.perform(get("/produtos/categoria/Software")).andExpect(status().isOk());
        mvc.perform(get("/produtos/categoria/Software")).andExpect(status().isUnprocessableContent());
        mvc.perform(get("/produtos/linha/Enterprise")).andExpect(status().isOk());
        mvc.perform(get("/produtos/linha/Enterprise")).andExpect(status().isUnprocessableContent());
    }

    @Test
    void shouldUseGenericExceptionHandlerWithoutLeakingDetails() throws Exception {
        when(findProductById.execute(PRODUCT_ID)).thenThrow(new RuntimeException("database password"));

        mvc.perform(get("/produtos/{id}", PRODUCT_ID))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Internal server error"));
    }

    private static <T> Result<T> failure(String field) {
        return Result.failure(DomainError.of(DomainErrorCode.NOT_FOUND, field));
    }

    private static DomainValidationException validation(DomainErrorCode code, String field) {
        return new DomainValidationException(List.of(DomainError.of(code, field)));
    }

    private static String validClientJson() {
        return """
                {"externalId":"client-ext","corporateReason":"Meeting Decoder Ltda",
                 "fantasyName":"Meeting Decoder","cnpj":"12345678000190","cnae":"6201501",
                 "segment":"Tecnologia","city":"Sao Paulo","state":"SP","country":"Brasil",
                 "type":"CUSTOMER","billingMin":100000,"billingMax":500000,
                 "npsNote":9.0,"npsDate":"2025-01-15T12:00:00"}
                """;
    }

    private static String validSellerJson() {
        return """
                {"type":"SELLER","name":"Ana Seller","email":"ana@example.com"}
                """;
    }

    private static String validMeetingJson() {
        return """
                {"externalId":"meeting-ext","meetingDate":"2025-01-15T12:00:00",
                 "startTime":"2025-01-15T11:00:00","endTime":"2025-01-15T12:00:00",
                 "status":"COMPLETED","external":false,
                 "recordingUrl":"https://example.com/recording.mp3",
                 "sellerId":"%s","clientId":"%s"}
                """.formatted(SELLER_ID, CLIENT_ID);
    }

    private static String validTranscriptionJson() {
        return """
                {"meetingId":"%s","rawText":"raw text","cleanText":"clean text",
                 "formattedText":"{\\"text\\":\\"clean text\\"}","confidence":0.91,
                 "processedAt":"2025-01-15T12:00:00","finishedAt":"2025-01-15T12:02:00"}
                """.formatted(MEETING_ID);
    }
}
