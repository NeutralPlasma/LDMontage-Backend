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


    public Work getWork(long id){
        var work = workRepository.findById(id);
        if(work.isEmpty())
            throw new WorkNotFoundException(id);
        return work.get();
    }


    public Work createWork(WorkCreateRequest request){
        var work = Work.builder()
                .title(request.title())
                .build();
        work = workRepository.save(work);
        return work;
    }


    public void deleteWork(long id){
        var work = workRepository.findById(id);
        if(work.isEmpty())
            throw new WorkNotFoundException(id);

        workRepository.delete(work.get());
    }
}
