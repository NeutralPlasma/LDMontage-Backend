package eu.virtusdevelops.ldmontage.controllers;

import eu.virtusdevelops.ldmontage.dto.WorkDTO;
import eu.virtusdevelops.ldmontage.mappers.WorkDTOMapper;
import eu.virtusdevelops.ldmontage.repositories.WorkRepository;
import eu.virtusdevelops.ldmontage.services.WorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/work")
@RequiredArgsConstructor
public class WorkController {
    private final WorkRepository workRepository;

    private final WorkService workService;
    private final WorkDTOMapper workDTOMapper;




    @GetMapping("/{id}")
    public ResponseEntity<WorkDTO> getWorkById(@PathVariable Long id) {
        var work = workService.getWork(id);
        return ResponseEntity.ok(workDTOMapper.apply(workService.getWork(id)));
    }


    @GetMapping
    public ResponseEntity<Page<WorkDTO>> getAllWorks(
            @PageableDefault(size = 50) final Pageable pageable
    ) {
        var works = workRepository.findAll(pageable);
        Page<WorkDTO> workDTOs = works.map(workDTOMapper);
        return ResponseEntity.ok(workDTOs);
    }


    @PutMapping("/{id}")
    public ResponseEntity<WorkDTO> updateWork(@PathVariable Long id, @RequestBody WorkDTO workDetails) {
        return null;
    }


    @PatchMapping("/{id}")
    public ResponseEntity<WorkDTO> partiallyUpdateWork(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return null;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWork(@PathVariable Long id) {
        workService.deleteWork(id);
        return ResponseEntity.noContent().build();
    }

}
