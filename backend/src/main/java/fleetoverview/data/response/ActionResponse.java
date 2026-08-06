package fleetoverview.data.response;

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
