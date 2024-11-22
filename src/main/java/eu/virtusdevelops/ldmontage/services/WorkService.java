package eu.virtusdevelops.ldmontage.services;

import eu.virtusdevelops.ldmontage.domain.exceptions.WorkNotFoundException;
import eu.virtusdevelops.ldmontage.domain.work.Work;
import eu.virtusdevelops.ldmontage.repositories.WorkRepository;
import eu.virtusdevelops.ldmontage.requests.WorkCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkService {
    private final WorkRepository workRepository;

    public Work getWork(long id) {
        return workRepository.findById(id)
                .orElseThrow(() -> new WorkNotFoundException(id));
    }

    public Work createWork(WorkCreateRequest request) {
        var work = Work.builder()
                .title(request.title())
                .build();

        return workRepository.save(work);
    }

    public void deleteWork(long id) {
        var work = getWork(id); // Reuse getWork for consistency and avoiding duplicate lookup logic
        workRepository.delete(work);
    }
}
