/**
 * 
 */
package ru.anr.base.web.samples.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
