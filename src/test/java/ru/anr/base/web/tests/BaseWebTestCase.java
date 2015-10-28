/**
 * 
 */
package ru.anr.base.web.tests;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import ru.anr.base.tests.BaseTestCase;

import com.codeborne.selenide.junit.ScreenShooter;

/**
 * Base test case class for Using Selenide framework in tests.
 *
 *
 * @author Alexey Romanchuk
 * @created Nov 26, 2014
 *
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@IntegrationTest("server.port:8080")
public class BaseWebTestCase extends BaseTestCase {

    /**
     * JUnit test rule for browser screenshots
     */
    @Rule
    public ScreenShooter shots = ScreenShooter.failedTests().to("target/screenshots");
}