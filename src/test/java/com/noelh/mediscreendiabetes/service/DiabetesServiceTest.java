package com.noelh.mediscreendiabetes.service;

import com.noelh.mediscreendiabetes.beans.NoteBean;
import com.noelh.mediscreendiabetes.beans.PatientBean;
import com.noelh.mediscreendiabetes.enumeration.RiskLevelEnum;
import com.noelh.mediscreendiabetes.proxies.MediscreenNoteProxy;
import com.noelh.mediscreendiabetes.proxies.MediscreenPatientProxy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DiabetesServiceTest {

    @Mock
    private MediscreenPatientProxy mediscreenPatientProxy;

    @Mock
    private MediscreenNoteProxy mediscreenNoteProxy;

    @InjectMocks
    private DiabetesService diabetesService;

    @Test
    public void getDiabetesRiskLevel_Should_Return_NONE(){
        //Given
        PatientBean patientBean = new PatientBean();
        patientBean.setSex('M');
        patientBean.setDateOfBirth(LocalDate.of(1980,8,8));

        NoteBean noteBean = new NoteBean();
        noteBean.setNoteOfThePractitioner("Test");
        NoteBean noteBean2 = new NoteBean();
        noteBean2.setNoteOfThePractitioner("Other test");
        List<NoteBean> noteBeanList = new ArrayList<>();
        noteBeanList.add(noteBean);
        noteBeanList.add(noteBean2);

        when(mediscreenPatientProxy.getPatientById(1L)).thenReturn(patientBean);
        when(mediscreenNoteProxy.getNoteListByPatientId(1L)).thenReturn(noteBeanList);

        //When
         RiskLevelEnum result = diabetesService.getDiabetesRiskLevel(1L);

        //Then
        assertThat(result).isEqualTo(RiskLevelEnum.NONE);
    }

    @Test
    public void getDiabetesRiskLevel_Should_Return_BORDERLINE(){
        //Given
        PatientBean patientBean = new PatientBean();
        patientBean.setSex('M');
        patientBean.setDateOfBirth(LocalDate.of(1980,8,8));

        NoteBean noteBean = new NoteBean();
        noteBean.setNoteOfThePractitioner("Hémoglobine A1C");
        NoteBean noteBean2 = new NoteBean();
        noteBean2.setNoteOfThePractitioner("Microalbumine");
        List<NoteBean> noteBeanList = new ArrayList<>();
        noteBeanList.add(noteBean);
        noteBeanList.add(noteBean2);

        when(mediscreenPatientProxy.getPatientById(1L)).thenReturn(patientBean);
        when(mediscreenNoteProxy.getNoteListByPatientId(1L)).thenReturn(noteBeanList);

        //When
        RiskLevelEnum result = diabetesService.getDiabetesRiskLevel(1L);

        //Then
        assertThat(result).isEqualTo(RiskLevelEnum.BORDERLINE);
    }

    @Test
    public void getDiabetesRiskLevel_Should_Return_IN_DANGER(){
        //Given
        PatientBean patientBean = new PatientBean();
        patientBean.setSex('M');
        patientBean.setDateOfBirth(LocalDate.of(2010,8,8));

        NoteBean noteBean = new NoteBean();
        noteBean.setNoteOfThePractitioner("Hémoglobine A1C Taille");
        NoteBean noteBean2 = new NoteBean();
        noteBean2.setNoteOfThePractitioner("Microalbumine");
        List<NoteBean> noteBeanList = new ArrayList<>();
        noteBeanList.add(noteBean);
        noteBeanList.add(noteBean2);

        when(mediscreenPatientProxy.getPatientById(1L)).thenReturn(patientBean);
        when(mediscreenNoteProxy.getNoteListByPatientId(1L)).thenReturn(noteBeanList);

        //When
        RiskLevelEnum result = diabetesService.getDiabetesRiskLevel(1L);

        //Then
        assertThat(result).isEqualTo(RiskLevelEnum.IN_DANGER);
    }

    @Test
    public void getDiabetesRiskLevel_Should_Return_IN_DANGER_With_F_And_Four_Keyword(){
        //Given
        PatientBean patientBean = new PatientBean();
        patientBean.setSex('F');
        patientBean.setDateOfBirth(LocalDate.of(2010,8,8));

        NoteBean noteBean = new NoteBean();
        noteBean.setNoteOfThePractitioner("Hémoglobine A1C Taille Poids");
        NoteBean noteBean2 = new NoteBean();
        noteBean2.setNoteOfThePractitioner("Microalbumine");
        List<NoteBean> noteBeanList = new ArrayList<>();
        noteBeanList.add(noteBean);
        noteBeanList.add(noteBean2);

        when(mediscreenPatientProxy.getPatientById(1L)).thenReturn(patientBean);
        when(mediscreenNoteProxy.getNoteListByPatientId(1L)).thenReturn(noteBeanList);

        //When
        RiskLevelEnum result = diabetesService.getDiabetesRiskLevel(1L);

        //Then
        assertThat(result).isEqualTo(RiskLevelEnum.IN_DANGER);
    }

    @Test
    public void getDiabetesRiskLevel_Should_Return_IN_DANGER_With_Eight_Keyword_And_More_Than_Thirty_Years_Old(){
        //Given
        PatientBean patientBean = new PatientBean();
        patientBean.setSex('F');
        patientBean.setDateOfBirth(LocalDate.of(1980,8,8));

        NoteBean noteBean = new NoteBean();
        noteBean.setNoteOfThePractitioner("Hémoglobine A1C Taille Poids Anormal");
        NoteBean noteBean2 = new NoteBean();
        noteBean2.setNoteOfThePractitioner("Microalbumine Vertige Réaction Rechute");
        List<NoteBean> noteBeanList = new ArrayList<>();
        noteBeanList.add(noteBean);
        noteBeanList.add(noteBean2);

        when(mediscreenPatientProxy.getPatientById(1L)).thenReturn(patientBean);
        when(mediscreenNoteProxy.getNoteListByPatientId(1L)).thenReturn(noteBeanList);

        //When
        RiskLevelEnum result = diabetesService.getDiabetesRiskLevel(1L);

        //Then
        assertThat(result).isEqualTo(RiskLevelEnum.IN_DANGER);
    }



    @Test
    public void getDiabetesRiskLevel_Should_Return_EARLY_ONSET(){
        //Given
        PatientBean patientBean = new PatientBean();
        patientBean.setSex('M');
        patientBean.setDateOfBirth(LocalDate.of(2010,8,8));

        NoteBean noteBean = new NoteBean();
        noteBean.setNoteOfThePractitioner("Hémoglobine A1C Taille");
        NoteBean noteBean2 = new NoteBean();
        noteBean2.setNoteOfThePractitioner("Microalbumine, Poids, Anormal");
        List<NoteBean> noteBeanList = new ArrayList<>();
        noteBeanList.add(noteBean);
        noteBeanList.add(noteBean2);

        when(mediscreenPatientProxy.getPatientById(1L)).thenReturn(patientBean);
        when(mediscreenNoteProxy.getNoteListByPatientId(1L)).thenReturn(noteBeanList);

        //When
        RiskLevelEnum result = diabetesService.getDiabetesRiskLevel(1L);

        //Then
        assertThat(result).isEqualTo(RiskLevelEnum.EARLY_ONSET);
    }

    @Test
    public void getDiabetesRiskLevel_Should_Return_EARLY_ONSET_With_F_And_Nine_Keyword(){
        //Given
        PatientBean patientBean = new PatientBean();
        patientBean.setSex('F');
        patientBean.setDateOfBirth(LocalDate.of(1980,8,8));

        NoteBean noteBean = new NoteBean();
        noteBean.setNoteOfThePractitioner("Hémoglobine A1C Taille Réaction Rechute Anticorps");
        NoteBean noteBean2 = new NoteBean();
        noteBean2.setNoteOfThePractitioner("Microalbumine, Poids, Anormal Vertige");
        List<NoteBean> noteBeanList = new ArrayList<>();
        noteBeanList.add(noteBean);
        noteBeanList.add(noteBean2);

        when(mediscreenPatientProxy.getPatientById(1L)).thenReturn(patientBean);
        when(mediscreenNoteProxy.getNoteListByPatientId(1L)).thenReturn(noteBeanList);

        //When
        RiskLevelEnum result = diabetesService.getDiabetesRiskLevel(1L);

        //Then
        assertThat(result).isEqualTo(RiskLevelEnum.EARLY_ONSET);
    }

    @Test
    public void getDiabetesRiskLevel_Should_Return_EARLY_ONSET_With_F_And_Seven_Keyword(){
        //Given
        PatientBean patientBean = new PatientBean();
        patientBean.setSex('F');
        patientBean.setDateOfBirth(LocalDate.of(1998,8,8));

        NoteBean noteBean = new NoteBean();
        noteBean.setNoteOfThePractitioner("Hémoglobine A1C Taille Réaction");
        NoteBean noteBean2 = new NoteBean();
        noteBean2.setNoteOfThePractitioner("Microalbumine, Poids, Anormal Vertige");
        List<NoteBean> noteBeanList = new ArrayList<>();
        noteBeanList.add(noteBean);
        noteBeanList.add(noteBean2);

        when(mediscreenPatientProxy.getPatientById(1L)).thenReturn(patientBean);
        when(mediscreenNoteProxy.getNoteListByPatientId(1L)).thenReturn(noteBeanList);

        //When
        RiskLevelEnum result = diabetesService.getDiabetesRiskLevel(1L);

        //Then
        assertThat(result).isEqualTo(RiskLevelEnum.EARLY_ONSET);
    }

}