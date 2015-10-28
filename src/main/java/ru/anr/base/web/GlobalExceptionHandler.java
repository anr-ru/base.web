package ru.anr.base.web;

import static org.springframework.http.HttpStatus.PAYLOAD_TOO_LARGE;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.util.UriUtils;

import ru.anr.base.ApplicationException;
import ru.anr.base.services.BaseServiceImpl;

/**
 * Global error handler for exceptions.
 *
 * @author Dmitry Philippov
 * @created Sep 22, 2015
 * 
 */
@ControllerAdvice
public class GlobalExceptionHandler extends BaseServiceImpl {

    /**
     * Max file size
     */
    @Value("${multipart.maxFileSize}")
    private String maxFileSize;

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Constructor with a specific exception type to be checked
     * 
     * @param specific
     *            The exception class to expect Size Limit
     */
    public GlobalExceptionHandler(String specific) {

        super();
        try {
            this.specific = Class.forName(specific);
        } catch (ClassNotFoundException ex) {
            throw new ApplicationException(ex);
        }
    }

    /**
     * A specific exception type
     */
    private Class<?> specific;

    /**
     * A special case when the {@link MultipartException} is thrown
     * 
     * @param request
     *            request
     * @param exception
     *            exception
     * @return response
     * @throws UnsupportedEncodingException
     *             if encoded to unsupported encoding
     */
    @ExceptionHandler(value = { MultipartException.class })
    @ResponseBody
    public ResponseEntity<String> processMultipartException(HttpServletRequest request, Exception exception)
            throws UnsupportedEncodingException {

        logger.debug("Caught exception: " + request.getContextPath(), exception);
        String errorMessage = text("internal.server.error");
        Throwable cause = exception.getCause();
        while (cause != null) {
            if (specific.isInstance(cause)) {
                errorMessage = text("size.limit.exceeded.exception", maxFileSize);
                break;
            }
            cause = cause.getCause();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        // headers.add(CONTENT_ENCODING, StandardCharsets.UTF_8.toString());
        return new ResponseEntity<String>(UriUtils.encodePath(errorMessage, StandardCharsets.UTF_8.toString()),
                headers, PAYLOAD_TOO_LARGE);
    }
}
