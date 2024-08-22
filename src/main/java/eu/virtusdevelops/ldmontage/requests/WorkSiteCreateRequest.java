package eu.virtusdevelops.ldmontage.requests;

public record WorkSiteCreateRequest(
        String title,
        String description,
        double latitude,
        double longitude,
        long workId
) {
}
