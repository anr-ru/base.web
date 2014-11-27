/**
 * 
 */
package ru.anr.base.web.tests;

import org.junit.Ignore;
import org.junit.Rule;

import ru.anr.base.tests.AbstractGlassfishWebTestCase;

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
public class BaseWebDriverTestCase extends AbstractGlassfishWebTestCase {

    /**
     * JUnit test rule for browser screenshots
     */
    @Rule
    public ScreenShooter shots = ScreenShooter.failedTests().to("target/screenshots");

}
