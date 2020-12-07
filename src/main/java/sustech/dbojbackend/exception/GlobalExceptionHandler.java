package sustech.dbojbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static String getThrowableStackInfo(Throwable e) {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        e.printStackTrace(new java.io.PrintWriter(buf, true));
        String msg = buf.toString();
        try {
            buf.close();
        } catch (Exception t) {
            return e.getMessage();
        }
        return msg;
    }

    @ResponseBody
    @ExceptionHandler(value = globalException.NotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public String handleNotFound(HttpServletRequest request, HttpServletResponse response, globalException.NotFoundException e)
            throws IOException {
        return "" + e.getLocalizedMessage();
    }

    @ResponseBody
    @ExceptionHandler(value = globalException.ForbiddenException.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public String handleForbidden(HttpServletRequest request, HttpServletResponse response, globalException.ForbiddenException e)
            throws IOException {
        return "" + e.getLocalizedMessage();
    }
}
