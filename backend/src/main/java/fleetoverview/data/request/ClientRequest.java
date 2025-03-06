package fleetoverview.data.request;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 10 февр. 2025
 **/
public record ClientRequest(
        int id,
        String name,
        String description,
        String shortName,
        String phone
) {
}
