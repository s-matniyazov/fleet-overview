package fleetoverview.data.request;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 15 июль 2025
 **/
public record TerminationRequest(
        int driverId,
        String reason
) {
}
