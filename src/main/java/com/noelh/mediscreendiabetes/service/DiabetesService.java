package com.noelh.mediscreendiabetes.service;

import com.noelh.mediscreendiabetes.beans.NoteBean;
import com.noelh.mediscreendiabetes.beans.PatientBean;
import com.noelh.mediscreendiabetes.enumeration.RiskLevelEnum;
import com.noelh.mediscreendiabetes.proxies.MediscreenNoteProxy;
import com.noelh.mediscreendiabetes.proxies.MediscreenPatientProxy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public RiskLevelEnum getDiabetesRiskLevel(Long id) {

        PatientBean patient = mediscreenPatientProxy.getPatientById(id);

        List<NoteBean> noteList = mediscreenNoteProxy.getNoteListByPatientId(id);

        char sex = patient.getSex();

        int age = Period.between(patient.getDateOfBirth(),LocalDate.now()).getYears();
        System.out.println(age);

        int foundedKeyword = countKeywordFound(noteList);
        System.out.println(foundedKeyword);

        if (isPatientInDanger(sex, age, foundedKeyword)){
            return RiskLevelEnum.IN_DANGER;
        }

        if (isPatientBorderline(age, foundedKeyword)){
            return RiskLevelEnum.BORDERLINE;
        }

        if (isPatientEarlyOnset(sex, age, foundedKeyword)){
            return RiskLevelEnum.EARLY_ONSET;
        }

        return RiskLevelEnum.NONE;
    }

    private int countKeywordFound(List<NoteBean> noteList) {
        int foundedKeyword = 0;

        for (String keyword : KEYWORD) {
            if (noteList.stream().anyMatch(noteBean -> noteBean.getNoteOfThePractitioner().contains(keyword))){
                foundedKeyword++;
            }
        }

        return foundedKeyword;
    }

    private boolean isPatientInDanger(char sex, int age, int foundedKeyword){
        if (sex == 'M' && age < 30 && foundedKeyword == 3){

            return true;
        }

        if (sex == 'F' && age < 30 && foundedKeyword == 4){

            return true;
        }

        if (age >= 30 && foundedKeyword == 8){

            return true;
        }

        return false;
    }

    private boolean isPatientBorderline(int age, int foundedKeyword){
        if (age >= 30 && foundedKeyword == 2){

            return true;
        }
        return false;
    }

    private boolean isPatientEarlyOnset(char sex, int age, int foundedKeyword){
        if (sex == 'M' && age < 30 && foundedKeyword == 5){

            return true;
        }

        if (sex == 'F' && age < 30 && foundedKeyword == 7){

            return true;
        }

        if (sex == 'F' && age >= 30 && foundedKeyword >= 8){

            return true;
        }

        return false;
    }
}
