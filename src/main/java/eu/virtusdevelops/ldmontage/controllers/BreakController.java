package eu.virtusdevelops.ldmontage.controllers;

import eu.virtusdevelops.ldmontage.dto.BreakDTO;
import eu.virtusdevelops.ldmontage.mappers.BreakDTOMapper;
import eu.virtusdevelops.ldmontage.requests.BreakEndRequest;
import eu.virtusdevelops.ldmontage.requests.BreakStartRequest;
import eu.virtusdevelops.ldmontage.services.WorkBreakService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/break")
@RequiredArgsConstructor
public class BreakController {

    private final WorkBreakService breakService;
    private final BreakDTOMapper breakDTOMapper;


    @PostMapping("/start")
    public ResponseEntity<BreakDTO> startBreak(
            @Valid @RequestBody BreakStartRequest request
    ) {
        var breakObj = breakService.startBreak(request);
        return ResponseEntity.ok(breakDTOMapper.apply(breakObj));
    }


    @PostMapping("/{break_id}/stop")
    public ResponseEntity<BreakDTO> startBreak(
            @PathVariable(name = "break_id") Long breakId,
            @Valid @RequestBody BreakEndRequest request
    ) {
        var breakObj = breakService.stopBreak(breakId, request);
        return ResponseEntity.ok(breakDTOMapper.apply(breakObj));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBreak(
            @PathVariable Long id
    ){
        breakService.deleteBreak(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BreakDTO> put(
            @PathVariable Long id,
            @Valid @RequestBody BreakDTO data
    ){
        breakService.updateBreak(id, data);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BreakDTO> patch(
            @PathVariable Long id,
            @Valid @RequestBody BreakDTO data
    ){
        breakService.patchBreak(id, data);
        return ResponseEntity.noContent().build();
    }
}
