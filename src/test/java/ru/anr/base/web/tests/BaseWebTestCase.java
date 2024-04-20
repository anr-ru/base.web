package ru.anr.base.web.tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;
import ru.anr.base.tests.BaseTestCase;

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

        System.setProperty("selenide.browser", "Chrome");

        Configuration.reportsFolder = "target/screenshots";
        Configuration.downloadsFolder = "target/downloads";
        Configuration.webdriverLogsEnabled = true;
        Configuration.timeout = 10000;
        Configuration.holdBrowserOpen = false;
    }
}
