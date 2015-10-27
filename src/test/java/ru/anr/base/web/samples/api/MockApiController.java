/**
 * 
 */
package ru.anr.base.web.samples.api;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import ru.anr.base.domain.api.APICommand;
import ru.anr.base.facade.web.api.AbstractAPIController;

/**
 * Mock API controller sample - uses static data and emulate api backend.
 * <p>
 * Usually we get API Command controller implementation as a child of
 * {@link AbstractAPIController} and try use it in web developments with mock
 * {@link ru.anr.base.services.api.APICommandFactory} (a descendant of
 * {@link ru.anr.base.services.api.MockJSONAPICommandFactoryImpl}. This mock
 * factory must be implemented here (ses {@link MockAPIFactoryImpl}).
 *
 *
 * @author Alexey Romanchuk
 * @created Nov 11, 2014
 *
 */
@RequestMapping("/api/v1")
@RestController
public class MockApiController extends AbstractAPIController {

    /**
     * Sample query GET method.
     * 
     * @return Some result value
     */
    @RequestMapping(value = "/datas", method = RequestMethod.GET)
    public String doGet() {

        APICommand cmd = buildAPI("datas", "v1");
        return process(cmd).getRawModel();
    }

    /**
     * Uploading a file
     * 
     * @param file
     *            An uploaded file
     * @return Some response
     * @throws IOException
     *             if case of error
     */
    @RequestMapping(value = "/files", method = POST)
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {

        APICommand cmd = buildAPI("files", "v1").context(//
                "contentType", file.getContentType(), //
                "originalFilename", UriUtils.decode(file.getOriginalFilename(), StandardCharsets.UTF_8.toString()), //
                "size", file.getSize(), //
                "data", file.getBytes());
        return process(cmd).getRawModel();
    }

}
