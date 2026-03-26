package config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import utils.TokenProvider;


public class RequestSpecConfig {

        public static RequestSpecification spec() {

            return new RequestSpecBuilder()
                    .setBaseUri("https://cloud-api.yandex.net")
                    .setBasePath("/v1/disk")
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", "OAuth " + TokenProvider.getToken().trim())
                    .build();
        }
    }