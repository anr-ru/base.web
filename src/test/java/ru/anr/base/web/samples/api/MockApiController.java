package ru.anr.base.web.samples.api;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;
import ru.anr.base.domain.api.APICommand;
import ru.anr.base.facade.web.api.AbstractAPIController;
import ru.anr.base.facade.web.api.CommandUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * A mock API controller sample - uses static data and emulates the API backend.
 *
 * @author Alexey Romanchuk
 * @created Nov 11, 2014
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
        APICommand cmd = CommandUtils.buildAPI("datas", "v1");
        return process(cmd).getRawModel();
    }

    @RequestMapping(value = "/value", method = RequestMethod.GET)
    public String doGetValue() {
        APICommand cmd = CommandUtils.buildAPI("some", "v1");
        return process(cmd).getRawModel();
    }

    /**
     * Uploading a file
     *
     * @param file An uploaded file
     * @return Some response
     * @throws IOException if case of error
     */
    @RequestMapping(value = "/files", method = POST)
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        APICommand cmd = CommandUtils.buildAPI("files", "v1").context(
                "contentType", file.getContentType(),
                "originalFilename", UriUtils.decode(
                        Objects.requireNonNull(file.getOriginalFilename()),
                        StandardCharsets.UTF_8.toString()),
                "size", file.getSize(),
                "data", file.getBytes());
        return process(cmd).getRawModel();
    }
}
