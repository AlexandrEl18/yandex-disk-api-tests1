package utils;

import io.github.cdimascio.dotenv.Dotenv;

public class TokenProvider {

    private static final Dotenv dotenv = Dotenv.load();

    public static String getToken() {
        return dotenv.get("YANDEX_TOKEN").trim();
    }
}