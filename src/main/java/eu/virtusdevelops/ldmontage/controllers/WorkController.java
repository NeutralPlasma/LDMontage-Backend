package eu.virtusdevelops.ldmontage.controllers;

import eu.virtusdevelops.ldmontage.domain.work.Work;
import eu.virtusdevelops.ldmontage.dto.WorkDTO;
import eu.virtusdevelops.ldmontage.services.WorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/work")
@RequiredArgsConstructor
public class WorkController {


    // create get, put, patch, delete methods



    private final WorkService workService;


    @GetMapping("/{id}")
    public ResponseEntity<WorkDTO> getWorkById(@PathVariable Long id) {

    }


    @GetMapping
    public ResponseEntity<List<WorkDTO>> getAllWorks() {

    }


    @PutMapping("/{id}")
    public ResponseEntity<WorkDTO> updateWork(@PathVariable Long id, @RequestBody WorkDTO workDetails) {

    }


    @PatchMapping("/{id}")
    public ResponseEntity<WorkDTO> partiallyUpdateWork(@PathVariable Long id, @RequestBody Map<String, Object> updates) {

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWork(@PathVariable Long id) {



        return ResponseEntity.noContent().build();
    }

}
