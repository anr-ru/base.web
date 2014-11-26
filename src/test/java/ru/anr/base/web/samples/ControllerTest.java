/**
 * 
 */
package ru.anr.base.web.samples;

import org.junit.Ignore;
import org.junit.runner.RunWith;

import ru.anr.base.BaseParent;
import de.helwich.junit.JasmineTest;
import de.helwich.junit.JasmineTestRunner;

/**
 * Description ...
 *
 *
 * @author Alexey Romanchuk
 * @created Nov 24, 2014
 *
 */

@RunWith(JasmineTestRunner.class)
@JasmineTest(src = { "lib/angular", "controllers" }, test = { "ControllersSpec" }, browser = true, //
srcDir = "/src/main/webapp/WEB-INF/resources/js")
@Ignore
public class ControllerTest extends BaseParent {

    /**
     * Nashord is unable to load angular source
     */
}
