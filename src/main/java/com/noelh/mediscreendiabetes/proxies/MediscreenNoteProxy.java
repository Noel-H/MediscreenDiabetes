package com.noelh.mediscreendiabetes.proxies;

import com.noelh.mediscreendiabetes.beans.NoteBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * MediscreenNote Proxy Interface
 */
@FeignClient(name = "MediscreenNote", url = "${MediscreenNoteUrl}")
public interface MediscreenNoteProxy {

    @GetMapping("/patientId/{patientId}")
    List<NoteBean> getNoteListByPatientId(@PathVariable("patientId") Long patientId);
}
