package com.noelh.mediscreendiabetes.controller;

import com.noelh.mediscreendiabetes.enumeration.RiskLevelEnum;
import com.noelh.mediscreendiabetes.service.DiabetesService;
import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DiabetesControllerTest {

    @Mock
    private DiabetesService diabetesService;

    @InjectMocks
    private DiabetesController diabetesController;

    @Test
    public void getDiabetesRiskLevel_Should_Return_Ok(){
        //Given
        when(diabetesService.getDiabetesRiskLevel(1L)).thenReturn(RiskLevelEnum.NONE);

        ResponseEntity<RiskLevelEnum> expectedResult = new ResponseEntity<>(RiskLevelEnum.NONE,HttpStatus.OK);
        //When
        ResponseEntity<RiskLevelEnum> result = diabetesController.getDiabetesRiskLevel(1L);

        //Then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void getDiabetesRiskLevel_Should_Return_NotFound(){
        //Given
        Request request = Request.create(Request.HttpMethod.GET, "url", new HashMap<>(), null, new RequestTemplate());
        byte[] b = new byte[0];
        when(diabetesService.getDiabetesRiskLevel(1L)).thenThrow(new FeignException.NotFound("message", request,b,null));

        ResponseEntity<RiskLevelEnum> expectedResult = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        //When
        ResponseEntity<RiskLevelEnum> result = diabetesController.getDiabetesRiskLevel(1L);

        //Then
        assertThat(result).isEqualTo(expectedResult);
    }

}