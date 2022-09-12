package com.noelh.mediscreendiabetes.proxies;

import com.noelh.mediscreendiabetes.beans.NoteBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "MediscreenNote", url = "localhost:8082/note")
public interface MediscreenNoteProxy {

    @GetMapping("/patientId/{patientId}")
    List<NoteBean> getNoteListByPatientId(@PathVariable("patientId") Long patientId);
}
