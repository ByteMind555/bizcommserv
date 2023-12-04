package com.airnz.tpx.communicationserv.ft;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * The FT is not extensive with just the happy path.
 */
public class MailTests {

    private String SEND_EMAIL_PAYLOAD = "{\n" +
            "    \"message_from\": {\n" +
            "        \"id\": \"diwakar.david@gmail.com\",\n" +
            "        \"name\": \"diwakar\"\n" +
            "    },\n" +
            "    \"message_recipient\": {\n" +
            "        \"recipient_to\": [\n" +
            "            {\n" +
            "                \"id\": \"testUser2@gmail.com\",\n" +
            "                \"name\": \"testUser2\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"recipient_cc\": [\n" +
            "            {\n" +
            "                \"id\": \"testUser1@gmail.com\",\n" +
            "                \"name\": \"testUser1\"\n" +
            "            }\n" +
            "        ]\n" +
            "    },\n" +
            "    \"message_body\": {\n" +
            "        \"subject\": \"Test Email\",\n" +
            "        \"contents\": [\n" +
            "            {\n" +
            "                \"content_type\": \"TEXT\",\n" +
            "                \"content_details\": \"This is a test Email. Merry Christmas Team :)\"\n" +
            "            }\n" +
            "        ]\n" +
            "    }\n" +
            "}";
    @Test
    public void testSendMail() {
        RestAssured.baseURI = "http://localhost:8080/";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(SEND_EMAIL_PAYLOAD)
                .post("mails/send");

        // Validate status code
        assertEquals(201, response.getStatusCode());

        // Validate response body or specific fields. Commented as values may vary
        assertEquals("SENT", response.jsonPath().getString("mail_location"));
        LinkedHashMap linkedHashMap = response.jsonPath().getJsonObject("content");
        LinkedHashMap contentMap= (LinkedHashMap) linkedHashMap.get("message_from");
        assertEquals("diwakar.david@gmail.com", contentMap.get("id"));
    }

    @Test
    public void testGetAllMails() {
        //Send a mail first and then verify through search
        testSendMail();
        RestAssured.baseURI = "http://localhost:8080/";
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "bearer testUser2@gmail.com");
        headers.put("mailbox", "INBOX");

        Response response = given()
                .when()
                .headers(headers)
                .get("mails/search");
        assertEquals(200, response.getStatusCode());
        ArrayList jsonResponse = response.jsonPath().getJsonObject("messages");
        assertTrue(jsonResponse.size() > 0);
    }

    @Test
    public void testDraftMails() {
        RestAssured.baseURI = "http://localhost:8080/";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(SEND_EMAIL_PAYLOAD)
                .post("mails/drafts");

        assertEquals("DRAFT", response.jsonPath().getString("mail_location"));
        LinkedHashMap linkedHashMap = response.jsonPath().getJsonObject("content");
        LinkedHashMap contentMap= (LinkedHashMap) linkedHashMap.get("message_from");
    }

}
