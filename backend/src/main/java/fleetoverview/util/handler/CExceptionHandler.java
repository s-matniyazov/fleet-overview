package fleetoverview.util.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.util.exceptions.ForbiddenException;
import fleetoverview.util.exceptions.NotFoundException;

import java.util.logging.Logger;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 27 нояб. 2024
 **/
@ControllerAdvice
public class CExceptionHandler extends ResponseEntityExceptionHandler {

    private final java.util.logging.Logger logger = Logger.getLogger(CExceptionHandler.class.getName());

    // todo: BaseResponse should be returned
    @ExceptionHandler(value = {NotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleNotExists(NotFoundException e) {
        logger.warning(e.getMessage());
        return ResponseEntity.badRequest().body(ApiResponse.badRequest(e.getMessage()));
    }

    @ExceptionHandler(value = {ForbiddenException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<?> handleForbidden(NotFoundException e) {
        logger.warning(e.getMessage());
        return ResponseEntity.badRequest().body(ApiResponse.forbiddenRequest(e.getMessage()));
    }
}
