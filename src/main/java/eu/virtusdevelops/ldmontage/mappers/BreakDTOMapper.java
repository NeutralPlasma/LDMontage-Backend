package eu.virtusdevelops.ldmontage.mappers;

import eu.virtusdevelops.ldmontage.domain.work.Break;
import eu.virtusdevelops.ldmontage.dto.BreakDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class BreakDTOMapper implements Function<Break, BreakDTO> {
    @Override
    public BreakDTO apply(Break breakObj) {
        return new BreakDTO(

        );
    }
}
