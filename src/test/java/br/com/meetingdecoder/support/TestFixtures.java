package br.com.meetingdecoder.support;

import br.com.meetingdecoder.application.dto.client.ClientOutput;
import br.com.meetingdecoder.application.dto.insight.InsightOutput;
import br.com.meetingdecoder.application.dto.insighttag.InsightTagOutput;
import br.com.meetingdecoder.application.dto.meeting.MeetingOutput;
import br.com.meetingdecoder.application.dto.produto.ProdutoOutput;
import br.com.meetingdecoder.application.dto.seller.SellerOutput;
import br.com.meetingdecoder.application.dto.transcription.TranscriptionOutput;
import br.com.meetingdecoder.domain.insight.enums.Polaridade;
import br.com.meetingdecoder.domain.insight.model.Insight;
import br.com.meetingdecoder.domain.insight.model.InsightTag;
import br.com.meetingdecoder.domain.insight.model.Produto;
import br.com.meetingdecoder.domain.insight.valueobject.ScoreConfiabilidade;
import br.com.meetingdecoder.domain.insight.valueobject.Sentimento;
import br.com.meetingdecoder.domain.sale.enums.ClientType;
import br.com.meetingdecoder.domain.sale.enums.SellerType;
import br.com.meetingdecoder.domain.sale.model.Client;
import br.com.meetingdecoder.domain.sale.model.Seller;
import br.com.meetingdecoder.domain.sale.valueobject.BillingRange;
import br.com.meetingdecoder.domain.sale.valueobject.ClientId;
import br.com.meetingdecoder.domain.sale.valueobject.Email;
import br.com.meetingdecoder.domain.sale.valueobject.NpsSnapshot;
import br.com.meetingdecoder.domain.sale.valueobject.SellerId;
import br.com.meetingdecoder.domain.transcription.enums.MeetingStatus;
import br.com.meetingdecoder.domain.transcription.model.Meeting;
import br.com.meetingdecoder.domain.transcription.model.Transcription;
import br.com.meetingdecoder.domain.transcription.valueobject.MeetingId;
import br.com.meetingdecoder.domain.transcription.valueobject.MeetingPeriod;
import br.com.meetingdecoder.domain.transcription.valueobject.Participants;
import br.com.meetingdecoder.domain.transcription.valueobject.RecordingUrl;
import br.com.meetingdecoder.domain.transcription.valueobject.TranscriptJson;
import br.com.meetingdecoder.domain.transcription.valueobject.TranscriptionConfidence;
import br.com.meetingdecoder.domain.transcription.valueobject.TranscriptionId;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public final class TestFixtures {
    public static final UUID CLIENT_ID = UUID.fromString("10000000-0000-0000-0000-000000000001");
    public static final UUID SELLER_ID = UUID.fromString("20000000-0000-0000-0000-000000000002");
    public static final UUID MEETING_ID = UUID.fromString("30000000-0000-0000-0000-000000000003");
    public static final UUID TRANSCRIPTION_ID = UUID.fromString("40000000-0000-0000-0000-000000000004");
    public static final UUID INSIGHT_ID = UUID.fromString("50000000-0000-0000-0000-000000000005");
    public static final UUID PRODUCT_ID = UUID.fromString("60000000-0000-0000-0000-000000000006");
    public static final UUID TAG_ID = UUID.fromString("70000000-0000-0000-0000-000000000007");
    public static final LocalDateTime NOW = LocalDateTime.of(2025, 1, 15, 12, 0);

    private TestFixtures() {
    }

    public static Client client() {
        return Client.create(
                ClientId.of(CLIENT_ID), "client-ext", "Meeting Decoder Ltda", "Meeting Decoder",
                "12345678000190", "6201501", "Tecnologia", "Sao Paulo", "SP", "Brasil",
                ClientType.CUSTOMER, BillingRange.of(100_000, 500_000),
                NpsSnapshot.of(9.0, NOW)
        );
    }

    public static Seller seller() {
        return Seller.create(
                SellerId.of(SELLER_ID), null, SellerType.SELLER,
                "Ana Seller", Email.of("ana@example.com")
        );
    }

    public static Meeting meeting() {
        return Meeting.create(
                MeetingId.of(MEETING_ID), "meeting-ext", NOW,
                MeetingPeriod.of(NOW.minusHours(1), NOW),
                MeetingStatus.COMPLETED, false,
                RecordingUrl.of("https://example.com/recording.mp3"),
                Participants.of(SellerId.of(SELLER_ID), ClientId.of(CLIENT_ID))
        );
    }

    public static Transcription transcription() {
        return Transcription.create(
                TranscriptionId.of(TRANSCRIPTION_ID), MeetingId.of(MEETING_ID),
                "raw text", "clean text", TranscriptJson.of("{\"text\":\"clean text\"}"),
                TranscriptionConfidence.of(0.91), NOW, NOW.plusMinutes(2)
        );
    }

    public static Produto produto() {
        return Produto.restore(
                br.com.meetingdecoder.domain.insight.valueobject.ProdutoId.of(PRODUCT_ID),
                "Decoder Pro", "Software", "Meeting analytics", "Enterprise",
                new br.com.meetingdecoder.domain.insight.valueobject.FaixaPreco(
                        BigDecimal.valueOf(100), BigDecimal.valueOf(500)),
                Instant.parse("2025-01-15T12:00:00Z"), null
        );
    }

    public static InsightTag insightTag() {
        return InsightTag.restore(
                br.com.meetingdecoder.domain.insight.valueobject.InsightTagId.of(TAG_ID),
                "churn", Instant.parse("2025-01-15T12:00:00Z")
        );
    }

    public static Insight insight() {
        return Insight.restore(
                br.com.meetingdecoder.domain.insight.valueobject.InsightId.of(INSIGHT_ID),
                TranscriptionId.of(TRANSCRIPTION_ID),
                Sentimento.of(Polaridade.NEGATIVO, ScoreConfiabilidade.alta()),
                "Cliente demonstrou risco de churn", "Trecho da reuniao",
                ScoreConfiabilidade.alta(), List.of(), List.of(), List.of(),
                Instant.parse("2025-01-15T12:00:00Z"), null
        );
    }

    public static ClientOutput clientOutput() {
        return ClientOutput.from(client());
    }

    public static SellerOutput sellerOutput() {
        return SellerOutput.from(seller());
    }

    public static MeetingOutput meetingOutput() {
        return MeetingOutput.from(meeting());
    }

    public static TranscriptionOutput transcriptionOutput() {
        return TranscriptionOutput.from(transcription());
    }

    public static ProdutoOutput produtoOutput() {
        return ProdutoOutput.from(produto());
    }

    public static InsightTagOutput insightTagOutput() {
        return InsightTagOutput.from(insightTag());
    }

    public static InsightOutput insightOutput() {
        return InsightOutput.from(insight());
    }
}
