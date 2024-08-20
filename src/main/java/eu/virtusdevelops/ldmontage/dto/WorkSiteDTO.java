package eu.virtusdevelops.ldmontage.dto;

public record WorkSiteDTO(
        long id,
        String title,
        String description,
        double latitude,
        double longitude
) {
}
