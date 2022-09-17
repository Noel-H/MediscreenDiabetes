package com.noelh.mediscreendiabetes.service;

import com.noelh.mediscreendiabetes.beans.NoteBean;
import com.noelh.mediscreendiabetes.beans.PatientBean;
import com.noelh.mediscreendiabetes.enumeration.RiskLevelEnum;
import com.noelh.mediscreendiabetes.proxies.MediscreenNoteProxy;
import com.noelh.mediscreendiabetes.proxies.MediscreenPatientProxy;
import feign.FeignException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;

/**
 * Diabetes Service
 */
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

    /**
     * Diabetes service constructor
     * @param mediscreenPatientProxy is a proxy to the patient api
     * @param mediscreenNoteProxy is a proxy to the note api
     */
    public DiabetesService(MediscreenPatientProxy mediscreenPatientProxy, MediscreenNoteProxy mediscreenNoteProxy){
        this.mediscreenPatientProxy = mediscreenPatientProxy;
        this.mediscreenNoteProxy = mediscreenNoteProxy;
    }

    /**
     * Get a risk level of diabetes by an id
     * @param id used to get the risk level
     * @return a risk level
     * @throws FeignException.NotFound when the id is not found
     */
    public RiskLevelEnum getDiabetesRiskLevel(Long id) throws FeignException.NotFound {

        PatientBean patient = mediscreenPatientProxy.getPatientById(id);

        List<NoteBean> noteList = mediscreenNoteProxy.getNoteListByPatientId(id);

        char sex = patient.getSex();

        int age = Period.between(patient.getDateOfBirth(),LocalDate.now()).getYears();

        int foundedKeyword = countKeywordFound(noteList);

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

    /**
     * Count the number of keyword found in a list of note
     * @param noteList used to check the keyword
     * @return the number of keyword found
     */
    private int countKeywordFound(List<NoteBean> noteList) {
        int foundedKeyword = 0;

        for (String keyword : KEYWORD) {
            if (noteList.stream().anyMatch(noteBean -> noteBean.getNoteOfThePractitioner().toLowerCase().contains(keyword.toLowerCase()))){
                foundedKeyword++;
            }
        }

        return foundedKeyword;
    }

    /**
     * Check if the patient is in danger
     * @param sex used to check if the patient is in danger
     * @param age used to check if the patient is in danger
     * @param foundedKeyword the number of keyword found previously
     * @return true if the given information match the criteria or false if it doesn't
     */
    private boolean isPatientInDanger(char sex, int age, int foundedKeyword){
        if (sex == 'M' && age < 30 && foundedKeyword == 3){
            return true;
        }

        if (sex == 'F' && age < 30 && foundedKeyword == 4){
            return true;
        }

        return age >= 30 && foundedKeyword == 8;
    }

    /**
     * Check if the patient is borderline
     * @param age used to check if the patient is borderline
     * @param foundedKeyword the number of keyword found previously
     * @return true if the given information match the criteria or false if it doesn't
     */
    private boolean isPatientBorderline(int age, int foundedKeyword){
        return age >= 30 && foundedKeyword == 2;
    }

    /**
     * Check if the patient is early onset
     * @param sex used to check if the patient is early onset
     * @param age used to check if the patient is early onset
     * @param foundedKeyword the number of keyword found previously
     * @return true if the given information match the criteria or false if it doesn't
     */
    private boolean isPatientEarlyOnset(char sex, int age, int foundedKeyword){
        if (sex == 'M' && age < 30 && foundedKeyword == 5){
            return true;
        }

        if (sex == 'F' && age < 30 && foundedKeyword == 7){
            return true;
        }

        return sex == 'F' && age >= 30 && foundedKeyword >= 8;
    }
}
