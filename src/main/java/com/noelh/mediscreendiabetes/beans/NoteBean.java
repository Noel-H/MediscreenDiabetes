package com.noelh.mediscreendiabetes.beans;

import lombok.Data;

/**
 * Note Bean
 */
@Data
public class NoteBean {

    private String id;

    private Long patientId;

    private String noteOfThePractitioner;
}
