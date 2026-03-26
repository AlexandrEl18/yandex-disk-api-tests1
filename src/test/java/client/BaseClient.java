package client;

import io.restassured.specification.RequestSpecification;

public class BaseClient {
    protected RequestSpecification spec;

    public BaseClient(RequestSpecification spec) {
        this.spec = spec;
    }
}