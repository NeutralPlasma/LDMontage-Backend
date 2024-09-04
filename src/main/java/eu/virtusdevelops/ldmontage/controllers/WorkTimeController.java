package eu.virtusdevelops.ldmontage.controllers;

import eu.virtusdevelops.ldmontage.dto.WorkTimeDTO;
import eu.virtusdevelops.ldmontage.mappers.WorkTimeDTOMapper;
import eu.virtusdevelops.ldmontage.requests.WorkTimeEndRequest;
import eu.virtusdevelops.ldmontage.requests.WorkTimeStartRequest;
import eu.virtusdevelops.ldmontage.services.WorkTimeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            @Valid WorkTimeStartRequest request){
        var data = workTimeService.startWork(request);

        return ResponseEntity.ok(workTimeDTOMapper.apply(data));
    }

    @PostMapping("/end")
    public ResponseEntity<WorkTimeDTO> stopWork(
            @PathVariable long id,
            @Valid WorkTimeEndRequest request){
        var data = workTimeService.endWorkTime(request);

        return ResponseEntity.ok(workTimeDTOMapper.apply(data));
    }


    // admin stuff (delete update patch)
}
