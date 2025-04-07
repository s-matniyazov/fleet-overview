package fleetoverview.data.response;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 28 янв. 2025
 **/
/**
 * instead of use specific methods of ApiResponse
* */
@Deprecated
public class ActionResponse extends ApiResponse {
    private final String actionMessage;
    private final int actionCode;

    public ActionResponse(int status, String message, String actionMessage, int actionCode) {
        super(status, message);
        this.actionMessage = actionMessage;
        this.actionCode = actionCode;
    }
    public String getActionMessage() {
        return actionMessage;
    }
    public int getActionCode() {
        return actionCode;
    }
}
