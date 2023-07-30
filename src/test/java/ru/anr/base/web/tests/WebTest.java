package ru.anr.base.web.tests;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriUtils;
import ru.anr.base.facade.web.api.RestClient;
import ru.anr.base.web.config.samples.WebApplication;

import java.util.Objects;

/**
 * Sample JUnit test for demonstrating WebDriver extension Selenide.
 *
 * @author Alexey Romanchuk
 * @created Nov 24, 2014
 */
@SpringBootTest(
        classes = WebApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public class WebTest extends BaseWebTestCase {


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
        Selenide.open("http://localhost:" + port + "/api/v1/datas");
        Assertions.assertEquals(
                JSON.replaceAll(" ", ""),
                Objects.requireNonNull(Selenide.getFocusedElement()).getText().replaceAll(" ", ""));
    }

    private static final String CONFIG_JSON = "{\"props\":{\"buildnumber\":\"1\"," +
            "\"version\":\"0.0.0\",\"production\":false,\"profiles\":[\"developers\"]}}";

    /**
     * Use case: checks configuration loading
     */
    @Test
    public void loadConfig() {
        Selenide.open("http://localhost:" + port + "/config");
        Assertions.assertEquals(CONFIG_JSON.replaceAll(" ", ""),
                Objects.requireNonNull(Selenide.getFocusedElement()).getText().replaceAll(" ", ""));
    }

    /**
     * Use case: get favicon
     */
    @Test
    public void loadFavIcon() {
        RestClient r = new RestClient();

        ResponseEntity<String> icon = r.get("/favicon.ico");
        Assertions.assertEquals(icon.getBody(), readAsString("/static/assets/favicon.ico"));
    }

    /**
     * Doing some Angular click.
     */
    @Test
    public void localeCheck() {

        Selenide.open("http://localhost:" + port);

        Assertions.assertEquals("Name", Selenide.$(By.id("txt")).getText());
        Assertions.assertEquals("Hello, world!", Selenide.$(By.id("txt_server")).getText());

        Selenide.$(By.id("lnk_ru")).click();
        Assertions.assertEquals("Имя", Selenide.$(By.id("txt")).getText());
        Assertions.assertEquals("Привет, мир!", Selenide.$(By.id("txt_server")).getText());
    }

    /**
     * Sends a file with the specified name
     *
     * @param fileName The name of a file
     */
    private void sendFile(String fileName) {

        RestClient r = new RestClient();

        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", new ClassPathResource(fileName));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<LinkedMultiValueMap<String, Object>> entity =
                new HttpEntity<>(map, headers);

        r.exchange("/api/v1/files", HttpMethod.POST, entity, String.class);
    }

    /**
     * Use case: checks uploading files and checking the upload size restrictions.
     */
    @Test
    public void upload() {
        try {
            sendFile("web-context.xml");
        } catch (HttpClientErrorException ex) {
            Assertions.assertEquals(HttpStatus.PAYLOAD_TOO_LARGE, ex.getStatusCode());
            Assertions.assertEquals("The file size exceeds: 1Kb",
                    UriUtils.decode(ex.getResponseBodyAsString(), "utf-8"));
        }

        // The file with a normal size
        sendFile("logback-test.xml");
    }
}
