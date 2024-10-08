package eu.virtusdevelops.ldmontage.domain.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@AllArgsConstructor
@Data
public class GenericApiError {

    private int status;
    private HttpStatus error;
    private HashMap<String, List<String>> errors;


    public GenericApiError(HttpStatus status) {
        this.status = status.value();
        this.error = status;
        errors = new HashMap<>();
    }

    public GenericApiError(HttpStatus status, String error) {
        this.status = status.value();
        this.error = status;
        errors = new HashMap<>();
        addError(error);
    }

    public GenericApiError(HttpStatus status, List<String> errors) {
        this.status = status.value();
        this.error = status;
        this.errors = new HashMap<>();
        for (String error : errors)
            addError(error);
    }

    public void addError(String key, String error) {
        errors.computeIfAbsent(key, s -> new ArrayList<>());
        errors.get(key).add(error);
    }

    public void addError(String error) {
        addError("general", error);
    }

}