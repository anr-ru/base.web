package ru.anr.base.web.tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.springframework.beans.factory.annotation.Value;
import ru.anr.base.tests.BaseTestCase;

import java.util.logging.Level;

/**
 * Base test case class for Using a Selenide framework in tests.
 *
 * @author Alexey Romanchuk
 * @created Nov 26, 2014
 */
public class BaseWebTestCase extends BaseTestCase {

    /**
     * The test port
     */
    @Value("${server.port}")
    protected int port;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        System.setProperty("selenide.browser", PhantomJSDriver.class.getName());
        Configuration.reportsFolder = "target/screenshots";
        Configuration.downloadsFolder = "target/downloads";
        Configuration.webdriverLogsEnabled = true;
        Configuration.timeout = 10000;
        Configuration.holdBrowserOpen = false;
    }
}
