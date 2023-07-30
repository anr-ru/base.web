/*
 * Copyright 2014-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package ru.anr.base.web;

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

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

import static org.springframework.http.HttpStatus.PAYLOAD_TOO_LARGE;

/**
 * A global error handler for upload exceptions.
 *
 * @author Dmitry Philippov
 * @created Sep 22, 2015
 */
@ControllerAdvice
public class UploadLimitExceptionHandler extends BaseServiceImpl {

    // Max file size
    @Value("${multipart.maxFileSize}")
    private String maxFileSize;

    private static final Logger logger = LoggerFactory.getLogger(UploadLimitExceptionHandler.class);

    /**
     * Constructor with a specific exception type to be checked
     *
     * @param specific            The exception class to expect Size Limit
     * @param errorMsgCode        The code for a general error message
     * @param limitExceedsMsgCode The code for the message in case of size limit's exceeding
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
    private final Class<?> specific;

    /**
     * The code for a general upload error message
     */
    private final String errorMsgCode;

    /**
     * The code for the message in case of size limit's exceeding
     */
    private final String limitExceedsMsgCode;

    /**
     * A handler when a {@link MultipartException} is thrown
     *
     * @param request   The request
     * @param exception The exception
     * @return The response to be passed
     */
    @ExceptionHandler(value = {MultipartException.class})
    @ResponseBody
    public ResponseEntity<String> processMultipartException(HttpServletRequest request, Exception exception) {

        logger.error("The exception caught: {}, {}", request.getContextPath(), exception.getMessage());

        String errorMessage = text(errorMsgCode);

        Throwable cause = new ApplicationException(exception).getMostSpecificCause();
        if (specific.isInstance(cause)) {
            errorMessage = text(limitExceedsMsgCode, maxFileSize);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<>(UriUtils.encodePath(errorMessage, StandardCharsets.UTF_8.toString()),
                headers, PAYLOAD_TOO_LARGE);
    }
}
