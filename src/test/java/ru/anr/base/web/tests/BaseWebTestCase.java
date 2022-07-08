/**
 *
 */
package ru.anr.base.web.tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.springframework.beans.factory.annotation.Value;
import ru.anr.base.tests.BaseTestCase;

/**
 * Base test case class for Using Selenide framework in tests.
 *
 * @author Alexey Romanchuk
 * @created Nov 26, 2014
 */
//@WebAppConfiguration(value = "src/test/webapp")
//@SpringBootTest(value = "server.port:8080")
public class BaseWebTestCase extends BaseTestCase {

    /**
     * JUnit test rule for browser screenshots
     */
    //public ScreenShooter shots = ScreenShooterExtension.failedTests().to("target/screenshots");

    //@RegisterExtension
    //static ScreenShooterExtension screenshots = new ScreenShooterExtension(false)
    //        .to("./target/screenshots");

    /**
     * test port
     */
    @Value("${server.port}")
    protected int port;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        new PhantomJSDriver();
        System.setProperty("selenide.browser", PhantomJSDriver.class.getName());
        Configuration.reportsFolder = "target/screenshots";
    }
}
