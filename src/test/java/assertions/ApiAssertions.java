package assertions;

import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

public class ApiAssertions {

    public static ApiAssertions assertThat(Response response) {
        return new ApiAssertions(response);
    }

    private final Response response;

    public ApiAssertions(Response response) {
        this.response = response;
    }

    public ApiAssertions statusCode(int code) {
        response.then().statusCode(code);
        return this;
    }

    public ApiAssertions message(String message) {
        response.then().body("message", equalTo(message));
        return this;
    }

    public ApiAssertions error(String error) {
        response.then().body("error", equalTo(error));
        return this;
    }

    public ApiAssertions name(String name) {
        response.then().body("name", equalTo(name));
        return this;
    }

    public ApiAssertions anyOfStatus(int... codes) {
        response.then().statusCode(org.hamcrest.Matchers.isOneOf(codes));
        return this;
    }
}