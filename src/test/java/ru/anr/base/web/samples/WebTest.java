/**
 * 
 */
package ru.anr.base.web.samples;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.springframework.test.context.ContextConfiguration;

import ru.anr.base.web.tests.BaseWebDriverTestCase;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;

/**
 * Sample JUnit test for demonstrating WebDriver extension Selenide.
 *
 *
 * @author Alexey Romanchuk
 * @created Nov 24, 2014
 *
 */
@ContextConfiguration(classes = WebTest.class)
public class WebTest extends BaseWebDriverTestCase {

    /**
     * By removing @ignore annotation is's possible to load Glassfish instance.
     * 
     * @throws IOException
     *             if error occurs
     */
    @Test
    @Ignore
    public void boot() throws IOException {

        byte[] x = new byte[1000];
        System.in.read(x);
    }

    /**
     * Use case: loading a main page and checking localization and absence of
     * JavaScript errors
     */
    @Test
    public void load() {

        Selenide.open("http://localhost:8080");

        Selenide.assertNoJavascriptErrors();

        Assert.assertEquals("Hello, world!", Selenide.$(By.id("txt")).getText());

        Selenide.open("http://localhost:8080/?locale=ru_RU");
        Selenide.$(By.id("txt")).shouldHave("Привет, мир!");

        Assert.assertEquals("Привет, мир!", Selenide.$(By.id("txt")).getText());

        Selenide.$(By.id("txt")).shouldHave(Condition.text("Привет, мир!"));
    }

    /**
     * Checking loading of JSON fragments. They can be used to perform static
     * testing of web application without fully implemented API backend.
     */
    @Test
    public void loadJSON() {

        Selenide.open("http://localhost:8080/api/v1/datas");

        Assert.assertEquals("[ { \"age\": 0, \"id\": \"motorola-xoom-with-wi-fi\", \"imageUrl\": "
                + "\"resources/img/motorola-xoom-with-wi-fi.0.jpg\", \"name\": \"Motorola XOOM\\u2122 with Wi-Fi\", "
                + "\"snippet\": \"The Next, Next Generation\\r\\n\\r\\nExperience the future with Motorola "
                + "XOOM with Wi-Fi," + " the world's first tablet powered by Android 3.0 (Honeycomb).\" }, "
                + "{ \"age\": 1, \"id\": \"motorola-xoom\", \"imageUrl\": \"resources/img/motorola-xoom.0.jpg\", "
                + "\"name\": \"MOTOROLA XOOM\\u2122\", \"snippet\": \"The Next, Next Generation\\n\\nExperience the "
                + "future with MOTOROLA XOOM, the world's first tablet powered by Android 3.0 (Honeycomb).\" }, "
                + "{ \"age\": 2, \"carrier\": \"AT&T\", \"id\": \"motorola-atrix-4g\", \"imageUrl\": "
                + "\"resources/img/motorola-atrix-4g.0.jpg\", \"name\": \"MOTOROLA ATRIX\\u2122 4G\", "
                + "\"snippet\": \"MOTOROLA ATRIX 4G the world's most powerful smartphone.\" } ]", Selenide
                .getFocusedElement().getText());
    }

    /**
     * Checking navigation via Angular Route
     */
    @Test
    public void angularNavigation() {

        Selenide.open("http://localhost:8080/#/phones/motorola-xoom-with-wi-fi");

        Assert.assertEquals("motorola-xoom-with-wi-fi", Selenide.$(By.id("details")).getText());
    }
}
