package eu.virtusdevelops.ldmontage.controllers;

import eu.virtusdevelops.ldmontage.domain.exceptions.BreakAlreadyEndedException;
import eu.virtusdevelops.ldmontage.domain.exceptions.BreakNotFoundException;
import eu.virtusdevelops.ldmontage.dto.BreakDTO;
import eu.virtusdevelops.ldmontage.mappers.BreakDTOMapper;
import eu.virtusdevelops.ldmontage.repositories.BreakRepository;
import eu.virtusdevelops.ldmontage.requests.BreakEndRequest;
import eu.virtusdevelops.ldmontage.requests.BreakStartRequest;
import eu.virtusdevelops.ldmontage.requests.LoginRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/break")
@RequiredArgsConstructor
public class BreakController {

    private final BreakRepository breakRepository;

    private final BreakDTOMapper breakDTOMapper;


    @PostMapping("/start")
    public ResponseEntity<BreakDTO> startBreak(
            @Valid @RequestBody BreakStartRequest request
    ) {
        // TODO
        // - check if user is currently working
        // - get users active work
        // - check that user isn't already on break
        // - check locations
        // - create new break


        return ResponseEntity.ok(new BreakDTO());
    }


    @PostMapping("/{break_id}/stop")
    public ResponseEntity<BreakDTO> startBreak(
            @PathVariable(name = "break_id") Long breakId,
            @Valid @RequestBody BreakEndRequest request
    ) {
        var breakOpt = breakRepository.findById(breakId);
        if (breakOpt.isEmpty()) {
            throw new BreakNotFoundException(breakId);
        }

        var breakObj = breakOpt.get();
        if(breakObj.getEndTime() != null) {
            throw new BreakAlreadyEndedException(breakId);
        }

        // TODO: update locations
        breakObj.setEndTime(new Date());


        breakObj = breakRepository.save(breakObj);


        return ResponseEntity.ok(breakDTOMapper.apply(breakObj));
    }
}
