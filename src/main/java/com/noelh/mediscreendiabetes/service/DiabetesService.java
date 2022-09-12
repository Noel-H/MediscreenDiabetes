package com.noelh.mediscreendiabetes.service;

import com.noelh.mediscreendiabetes.enumeration.RiskLevelEnum;
import com.noelh.mediscreendiabetes.proxies.MediscreenNoteProxy;
import com.noelh.mediscreendiabetes.proxies.MediscreenPatientProxy;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DiabetesService {

    private final MediscreenPatientProxy mediscreenPatientProxy;

    private final MediscreenNoteProxy mediscreenNoteProxy;

    private static final List<String> KEYWORD = Arrays.asList(
            "Hémoglobine A1C",
            "Microalbumine",
            "Taille",
            "Poids",
            "Fumeur",
            "Anormal",
            "Cholestérol",
            "Vertige",
            "Rechute",
            "Réaction",
            "Anticorps");

    public DiabetesService(MediscreenPatientProxy mediscreenPatientProxy, MediscreenNoteProxy mediscreenNoteProxy){
        this.mediscreenPatientProxy = mediscreenPatientProxy;
        this.mediscreenNoteProxy = mediscreenNoteProxy;
    }

    public String getDiabetesRiskLevel(Long id) {
        System.out.println(
                mediscreenPatientProxy.getPatientById(id)+
                " + "
                +
                mediscreenNoteProxy.getNoteListByPatientId(id));
        return RiskLevelEnum.NONE.toString();
    }
}
