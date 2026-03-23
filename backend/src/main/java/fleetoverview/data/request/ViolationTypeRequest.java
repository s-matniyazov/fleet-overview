package fleetoverview.data.request;

public record ViolationTypeRequest(
        String type,
        String sectionViolCode,
        String description
) {}
