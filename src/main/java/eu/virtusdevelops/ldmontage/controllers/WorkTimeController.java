package eu.virtusdevelops.ldmontage.controllers;

import eu.virtusdevelops.ldmontage.dto.WorkTimeDTO;
import eu.virtusdevelops.ldmontage.mappers.WorkTimeDTOMapper;
import eu.virtusdevelops.ldmontage.requests.WorkTimeEndRequest;
import eu.virtusdevelops.ldmontage.requests.WorkTimeStartRequest;
import eu.virtusdevelops.ldmontage.services.WorkTimeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/worktime")
public class WorkTimeController {

    private final WorkTimeService workTimeService;
    private final WorkTimeDTOMapper workTimeDTOMapper;
    // delete
    // update
    // patch


    @PostMapping("/start")
    public ResponseEntity<WorkTimeDTO> startWork(
            @Valid WorkTimeStartRequest request) {
        var data = workTimeService.startWork(request);

        return ResponseEntity.ok(workTimeDTOMapper.apply(data));
    }

    @PostMapping("/end")
    public ResponseEntity<WorkTimeDTO> stopWork(
            @Valid WorkTimeEndRequest request) {
        var data = workTimeService.endWorkTime(request);

        return ResponseEntity.ok(workTimeDTOMapper.apply(data));
    }


    // admin stuff (delete update patch)

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    @PutMapping("/{id}")
    public ResponseEntity<WorkTimeDTO> update(
            @RequestBody WorkTimeDTO workTimeDTO,
            @PathVariable Long id
    ) {
        // update everything

        return ResponseEntity.ok(workTimeDTO);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    @PatchMapping("/{id")
    public ResponseEntity<WorkTimeDTO> patch(
            @RequestBody WorkTimeDTO workTimeDTO,
            @PathVariable Long id
    ) {
        // only change updated thingies, ignore empty

        return ResponseEntity.ok(workTimeDTO);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        workTimeService.deleteWorkTime(id);


        return ResponseEntity.noContent().build();
    }
}
