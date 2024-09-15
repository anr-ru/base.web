package ru.anr.base.web.tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import ru.anr.base.tests.BaseTestCase;

/**
 * Base test case class for Using a Selenide framework in tests.
 *
 * @author Alexey Romanchuk
 * @created Nov 26, 2014
 */
@Disabled
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

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-debugging-pipe");

        Configuration.browserCapabilities = options;
    }
}
