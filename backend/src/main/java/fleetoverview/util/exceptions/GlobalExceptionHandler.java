package fleetoverview.util.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ProblemDetail badCredentials(AuthenticationException e) {
        log.warn("Authentication failed: {}", e.getMessage());
        return problem(HttpStatus.UNAUTHORIZED, "Invalid email or password");
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ProblemDetail refresh(InvalidRefreshTokenException e) {
        log.warn("Refresh token rejected: {}", e.getMessage());
        return problem(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ProblemDetail forbidden(ForbiddenException e) {
        log.warn("Forbidden: {}", e.getMessage());
        return problem(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail notFound(NotFoundException e) {
        log.warn("Not found: {}", e.getMessage());
        return problem(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(ExistsException.class)
    public ProblemDetail exists(ExistsException e) {
        log.warn("Conflict: {}", e.getMessage());
        return problem(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail illegal(IllegalArgumentException e) {
        log.warn("Bad request: {}", e.getMessage());
        return problem(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    // --- 5xx: our fault, log everything, return nothing ---

    @ExceptionHandler(DataAccessException.class)
    public ProblemDetail dataAccess(DataAccessException e, HttpServletRequest req) {
        return serverError(e, req, "A database error occurred.");
    }

    @ExceptionHandler(FileException.class)
    public ProblemDetail file(FileException e, HttpServletRequest req) {
        return serverError(e, req, "File storage is currently unavailable.");
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail unhandled(Exception e, HttpServletRequest req) {
        return serverError(e, req, "Unexpected error, please contact support.");
    }

    private ProblemDetail serverError(Exception e, HttpServletRequest req, String msg) {
        String errorId = UUID.randomUUID().toString().substring(0, 8);
        log.error("errorId={} {} {} -> {}", errorId,
                req.getMethod(), req.getRequestURI(), e.getClass().getName(), e);
        ProblemDetail pd = problem(HttpStatus.INTERNAL_SERVER_ERROR, msg);
        pd.setProperty("errorId", errorId);
        return pd;
    }

    private ProblemDetail problem(HttpStatus status, String msg) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(status, msg);
        pd.setProperty("timestamp", Instant.now());
        return pd;
    }
}