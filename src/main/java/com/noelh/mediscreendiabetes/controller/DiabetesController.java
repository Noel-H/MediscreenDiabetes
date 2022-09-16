package com.noelh.mediscreendiabetes.controller;

import com.noelh.mediscreendiabetes.enumeration.RiskLevelEnum;
import com.noelh.mediscreendiabetes.service.DiabetesService;
import feign.FeignException;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/diabetes")
@Tag(name = "DiabetesController", description = "Api pour obtenir des informations en rapport avec le diabète.")
public class DiabetesController {

    private final DiabetesService diabetesService;

    public DiabetesController(DiabetesService diabetesService){
        this.diabetesService = diabetesService;
    }

    @ApiOperation("Récupère le niveau de risque de diabète grâce à un id donné")
    @GetMapping("/check/{id}")
    public ResponseEntity<RiskLevelEnum> getDiabetesRiskLevel(@PathVariable("id") Long id) {
        log.info("GET /diabetes/check/{}", id);
        try {
            return ResponseEntity.ok(diabetesService.getDiabetesRiskLevel(id));
        } catch (FeignException.NotFound e) {
            log.error("GET /diabetes/check/{} : [ERROR] = {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

}
