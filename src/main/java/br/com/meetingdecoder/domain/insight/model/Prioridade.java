package br.com.meetingdecoder.domain.insight.model;

/**
 * Enum representing the priority level of a recommended action.
 * Used to triage and prioritize customer actions and responses.
 */
public enum Prioridade {
    /**
     * Critical priority — requires immediate attention.
     */
    CRITICA,

    /**
     * High priority — should be addressed soon.
     */
    ALTA,

    /**
     * Medium priority — normal handling timeline.
     */
    MEDIA,

    /**
     * Low priority — can be addressed in due course.
     */
    BAIXA
}
