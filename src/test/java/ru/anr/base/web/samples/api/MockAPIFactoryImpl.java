/**
 *
 */
package ru.anr.base.web.samples.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.anr.base.domain.api.APICommand;
import ru.anr.base.services.BaseService;
import ru.anr.base.services.api.MockJSONAPICommandFactoryImpl;

/**
 * Implementation of mock APIs.
 *
 * @author Alexey Romanchuk
 * @created Nov 26, 2014
 */
@Component("mockAPIFactory")
public class MockAPIFactoryImpl extends MockJSONAPICommandFactoryImpl {

    @Autowired
    @Qualifier("service")
    private BaseService service;

    /**
     * {@inheritDoc}
     */
    @Override
    public APICommand process(APICommand cmd) {

        APICommand r;

        switch (cmd.getCommandId()) {
            case "datas":
                // Just load a prepared response from a file
                r = generateResponse(readAsString("json/datas.bin"), cmd);
                break;
            case "files":
                log("Uploaded size:\n{}", cmd.getContexts().get("size"));
                r = generateResponse("{\"code\": 0}", cmd);
            case "some":
                r = generateResponse("{\"message\":\"" + service.text("hello.world") + "\"}", cmd);
                break;
            default:
                r = super.process(cmd);
                break;
        }
        return r;
    }
}
