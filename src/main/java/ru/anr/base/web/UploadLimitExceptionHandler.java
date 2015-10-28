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
public class UploadLimitExceptionHandler extends BaseServiceImpl {

    /**
     * Max file size
     */
    @Value("${multipart.maxFileSize}")
    private String maxFileSize;

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(UploadLimitExceptionHandler.class);

    /**
     * Constructor with a specific exception type to be checked
     * 
     * @param specific
     *            The exception class to expect Size Limit
     * @param errorMsgCode
     *            The code for a general error message
     * @param limitExceedsMsgCode
     *            The code for the message in case of size limit's exceeding
     */
    public UploadLimitExceptionHandler(String specific, String errorMsgCode, String limitExceedsMsgCode) {

        super();
        try {

            this.specific = Class.forName(specific);
            this.errorMsgCode = errorMsgCode;
            this.limitExceedsMsgCode = limitExceedsMsgCode;

        } catch (ClassNotFoundException ex) {
            throw new ApplicationException(ex);
        }
    }

    /**
     * A specific exception type
     */
    private Class<?> specific;

    /**
     * The code for a general upload error message
     */
    private String errorMsgCode;

    /**
     * The code for the message in case of size limit's exceeding
     */
    private String limitExceedsMsgCode;

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

        logger.error("The exception caugth: " + request.getContextPath(), exception);

        String errorMessage = text(errorMsgCode);

        Throwable cause = new ApplicationException(exception).getMostSpecificCause();
        if (specific.isInstance(cause)) {
            errorMessage = text(limitExceedsMsgCode, maxFileSize);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<String>(UriUtils.encodePath(errorMessage, StandardCharsets.UTF_8.toString()),
                headers, PAYLOAD_TOO_LARGE);
    }
}
