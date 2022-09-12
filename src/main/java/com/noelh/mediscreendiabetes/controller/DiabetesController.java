package com.noelh.mediscreendiabetes.controller;

import com.noelh.mediscreendiabetes.enumeration.RiskLevelEnum;
import com.noelh.mediscreendiabetes.service.DiabetesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/diabetes")
public class DiabetesController {

    private final DiabetesService diabetesService;

    public DiabetesController(DiabetesService diabetesService){
        this.diabetesService = diabetesService;
    }

    @GetMapping("/check/{id}")
    public ResponseEntity<RiskLevelEnum> getDiabetesRiskLevel(@PathVariable("id") Long id) {
        log.info("GET /diabetes/check/{}", id);
        try {
            return ResponseEntity.ok(diabetesService.getDiabetesRiskLevel(id));
        } catch (Exception e) {
            log.error("GET /diabetes/check/{} : [ERROR] = {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

}
