package br.com.meetingdecoder.domain.enums;

public enum CurrentSystem {

    SAP,
    ORACLE,

    TOTVS_PROTHEUS,
    TOTVS_RM,
    TOTVS_DATASUL,

    SENIOR,
    SANKHYA,
    CIGAM,

    MICROSOFT_DYNAMICS,

    LEGACY_SYSTEM,
    CUSTOM_SYSTEM,
    EXCEL_SPREADSHEETS,

    NO_SYSTEM,

    OTHER;

    public boolean isCompetitor() {
        return switch (this) {
            case SAP,
                 ORACLE,
                 SANKHYA,
                 SENIOR,
                 CIGAM,
                 MICROSOFT_DYNAMICS -> true;

            default -> false;
        };
    }

    public boolean isLegacy() {
        return switch (this) {
            case LEGACY_SYSTEM,
                 CUSTOM_SYSTEM,
                 EXCEL_SPREADSHEETS -> true;

            default -> false;
        };
    }

    public boolean isTotvs() {
        return switch (this) {
            case TOTVS_PROTHEUS,
                 TOTVS_RM,
                 TOTVS_DATASUL -> true;

            default -> false;
        };
    }

    public boolean requiresMigration() {
        return isLegacy() || this == NO_SYSTEM;
    }
}
