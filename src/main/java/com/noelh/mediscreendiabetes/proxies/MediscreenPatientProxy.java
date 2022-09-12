package com.noelh.mediscreendiabetes.proxies;

import com.noelh.mediscreendiabetes.beans.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "MediscreenPatient", url = "localhost:8081/patient")
public interface MediscreenPatientProxy {

    @GetMapping("/{id}")
    PatientBean getPatientById(@PathVariable("id") Long id);
}
