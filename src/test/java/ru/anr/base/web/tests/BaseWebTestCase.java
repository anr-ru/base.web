/**
 *
 */
package ru.anr.base.web.tests;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.anr.base.tests.BaseTestCase;

/**
 * Base test case class for Using Selenide framework in tests.
 *
 *
 * @author Alexey Romanchuk
 * @created Nov 26, 2014
 *
 */
@WebAppConfiguration
@SpringBootTest("server.port:8080")
public class BaseWebTestCase extends BaseTestCase {

    /**
     * JUnit test rule for browser screenshots
     */
    //public ScreenShooter shots = ScreenShooter.failedTests().to("target/screenshots");

    /**
     * test port
     */
    @Value("${local.server.port}")
    protected int port;
}
