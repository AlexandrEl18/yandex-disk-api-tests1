package utils;

import io.qameta.allure.Allure;
import io.restassured.response.Response;

public class AllureUtils {

    public static void attachRequest(String name, String request) {
        Allure.addAttachment(name, request);
    }

    public static void attachResponse(String name, Response response) {
        String body = response.getBody().asPrettyString();
        Allure.addAttachment(name, body);
    }
}