package eu.virtusdevelops.ldmontage.controllers;

import eu.virtusdevelops.ldmontage.dto.WorkSiteDTO;
import eu.virtusdevelops.ldmontage.mappers.WorkSiteDTOMapper;
import eu.virtusdevelops.ldmontage.requests.WorkSiteCreateRequest;
import eu.virtusdevelops.ldmontage.services.WorkSiteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/worksite")
@RequiredArgsConstructor
public class WorkSiteController {
    private final WorkSiteService workSiteService;
    private final WorkSiteDTOMapper workSiteDTOMapper;

    // update
    // patch

    @PostMapping("/create")
    public ResponseEntity<WorkSiteDTO> create(
            @RequestBody @Valid WorkSiteCreateRequest request
    ) {
        var worksite = workSiteService.createNew(request);
        return ResponseEntity.ok(workSiteDTOMapper.apply(worksite));
    }

    @PostMapping("/{id}/authorize")
    public ResponseEntity<WorkSiteDTO> authorize(
            @PathVariable Long id,
            @RequestBody UUID userid
    ) {
        var worksite = workSiteService.addAuthorizedWorker(id, userid);
        return ResponseEntity.ok(workSiteDTOMapper.apply(worksite));
    }

    @PostMapping("/{id}/de_authorize")
    public ResponseEntity<WorkSiteDTO> deAuthorize(
            @PathVariable Long id,
            @RequestBody UUID userid
    ) {
        var worksite = workSiteService.removeAuthorizedWorker(id, userid);
        return ResponseEntity.ok(workSiteDTOMapper.apply(worksite));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        workSiteService.deleteWorkSite(id);
        return ResponseEntity.noContent().build();
    }

}
