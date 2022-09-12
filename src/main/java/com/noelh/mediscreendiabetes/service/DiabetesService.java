package com.noelh.mediscreendiabetes.service;

import com.noelh.mediscreendiabetes.enumeration.RiskLevelEnum;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DiabetesService {

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

    public String getDiabetesRiskLevel(Long id) {
        return RiskLevelEnum.NONE.toString();
    }
}
