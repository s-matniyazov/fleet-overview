package fleetoverview.data.response;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 28 янв. 2025
 **/
public class DataResponse<T> extends ApiResponse {
    private final T data;

    public DataResponse(int status, String message, T data) {
        super(status, message);
        this.data = data;
    }
    public T getData() {
        return data;
    }

    public static <T> DataResponse<T> success(T data) {
        return new DataResponse<T>(200, "success", data);
    }
}
