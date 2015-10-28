/**
 * 
 */
package ru.anr.base.web.tests;

import java.io.UnsupportedEncodingException;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriUtils;

import ru.anr.base.facade.web.api.RestClient;
import ru.anr.base.web.samples.config.WebApplication;

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
@SpringApplicationConfiguration(classes = WebApplication.class)
public class WebTest extends BaseWebTestCase {

    /**
     * Use case: loading the main page and checking its localization and absence
     * of JavaScript errors
     */
    @Test
    public void load() {

        Selenide.open("http://localhost:8080");
        Selenide.assertNoJavascriptErrors();

        Assert.assertEquals("Hello, world!", Selenide.$(By.id("txt")).getText());

        Selenide.open("http://localhost:8080/?locale=ru_RU");

        Assert.assertEquals("Привет, мир!", Selenide.$(By.id("txt")).getText());
        Selenide.$(By.id("txt")).shouldHave(Condition.text("Привет, мир!"));
    }

    /**
     * A JSON to check
     */
    private static final String JSON = "[{\"age\": 0, \"id\": \"motorola-xoom-with-wi-fi\", \"imageUrl\": "
            + "\"resources/img/motorola-xoom-with-wi-fi.0.jpg\", \"name\": \"Motorola XOOM with Wi-Fi\", "
            + "\"snippet\": \"The Next, Next Generation\\r\\n\\r\\nExperience the future with Motorola "
            + "XOOM with Wi-Fi," + " the world's first tablet powered by Android 3.0 (Honeycomb).\" }, "
            + "{ \"age\": 1, \"id\": \"motorola-xoom\", \"imageUrl\": \"resources/img/motorola-xoom.0.jpg\", "
            + "\"name\": \"MOTOROLA XOOM\", \"snippet\": \"The Next, Next Generation\\n\\nExperience the "
            + "future with MOTOROLA XOOM, the world's first tablet powered by Android 3.0 (Honeycomb).\" }, "
            + "{ \"age\": 2, \"carrier\": \"AT&T\", \"id\": \"motorola-atrix-4g\", \"imageUrl\": "
            + "\"resources/img/motorola-atrix-4g.0.jpg\", \"name\": \"MOTOROLA ATRIX 4G\", "
            + "\"snippet\": \"MOTOROLA ATRIX 4G the world's most powerful smartphone.\"}]";

    /**
     * Use case: checks loading JSON fragments
     */
    @Test
    public void loadJSON() {

        Selenide.open("http://localhost:8080/api/v1/datas");
        Assert.assertEquals(JSON.replaceAll(" ", ""), Selenide.getFocusedElement().getText().replaceAll(" ", ""));
    }

    /**
     * Checking navigation via Angular Route
     */
    @Test
    public void angularClick() {

        Selenide.open("http://localhost:8080");
        Selenide.$(By.tagName("h3")).should(Condition.text(""));

        Selenide.$(By.id("lnk")).click();
        Assert.assertEquals(JSON.replaceAll(" ", ""), Selenide.$(By.tagName("h3")).getText().replaceAll(" ", ""));
    }

    /**
     * Sends a file with the specified name
     * 
     * @param fileName
     *            The name of a file
     * @return A response
     */
    private ResponseEntity<String> sendFile(String fileName) {

        RestClient r = new RestClient();

        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", new ClassPathResource(fileName));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<LinkedMultiValueMap<String, Object>> entity =
                new HttpEntity<LinkedMultiValueMap<String, Object>>(map, headers);

        return r.exchange("/api/v1/files", HttpMethod.POST, entity, String.class);

    }

    /**
     * Use case: checks loading JSON fragments
     */
    @Test
    public void upload() {

        try {
            sendFile("web-context.xml");
        } catch (HttpClientErrorException ex) {
            Assert.assertEquals(HttpStatus.PAYLOAD_TOO_LARGE, ex.getStatusCode());
            try {
                Assert.assertEquals("The file size exceeds: 1Kb",
                        UriUtils.decode(ex.getResponseBodyAsString(), "utf-8"));
            } catch (UnsupportedEncodingException e) {
                // ignore
            }
        }

        // The file with a normal size
        sendFile("logback-test.xml");
    }
}
