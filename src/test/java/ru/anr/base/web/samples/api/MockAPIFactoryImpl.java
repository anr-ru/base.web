/**
 * 
 */
package ru.anr.base.web.samples.api;

import org.springframework.stereotype.Component;

import ru.anr.base.domain.api.APICommand;
import ru.anr.base.services.api.MockJSONAPICommandFactoryImpl;

/**
 * Implementation of mock APIs.
 *
 *
 * @author Alexey Romanchuk
 * @created Nov 26, 2014
 *
 */
@Component("mockAPIFactory")
public class MockAPIFactoryImpl extends MockJSONAPICommandFactoryImpl {

    /**
     * {@inheritDoc}
     */
    @Override
    public APICommand process(APICommand cmd) {

        APICommand r = null;

        switch (cmd.getCommandId()) {
            case "datas":
                // Just load a prepared response from a file
                r = generateResponse(loadFromFile("json/datas.bin"), cmd);
                break;
            case "files":
                log("Uploaded size:\n{}", cmd.getContexts().get("size"));
                r = generateResponse("{\"code:\"0}", cmd);
            default:
                r = super.process(cmd);
                break;
        }
        return r;
    }
}
