package fleetoverview.data.response;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 28 янв. 2025
 **/
public class ApiResponse {
    private int status;
    private String message;

    public ApiResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
    public ApiResponse() {}

    public static ApiResponse success() {
        return new ApiResponse(200, "success");
    }

    public static ApiResponse badRequest(String s) {
        return new ApiResponse(400, s);
    }
    public static ApiResponse forbiddenRequest(String s) {
        return new ApiResponse(401, s);
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
