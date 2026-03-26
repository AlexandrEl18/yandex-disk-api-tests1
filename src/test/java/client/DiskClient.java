package client;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.io.File;
import static io.restassured.RestAssured.given;

public class DiskClient extends BaseClient {

    public DiskClient(RequestSpecification spec) {
        super(spec);
    }

    public Response createFolder(String path) {
        return given()
                .spec(spec)
                .queryParam("path", path)
                .when()
                .put("/resources");
    }

    public Response getFolder(String path) {
        return given()
                .spec(spec)
                .queryParam("path", path)
                .when()
                .get("/resources");
    }

    public Response deleteFolder(String path) {
        return given()
                .spec(spec)
                .queryParam("path", path)
                .when()
                .delete("/resources");
    }

    public Response uploadFile(String path, File file) {

        Response hrefResponse = given()
                .spec(spec)
                .queryParam("path", path)
                .queryParam("overwrite", "true")
                .when()
                .get("/resources/upload");

        String href = hrefResponse.jsonPath().getString("href");

        if (href == null || href.isEmpty()) {
            return hrefResponse;
        }

        return given()
                .contentType("application/octet-stream")
                .body(file)
                .when()
                .put(href);
    }


    public Response updateFolderName(String path, String newName) {

        String parent = path.contains("/") ? path.substring(0, path.lastIndexOf("/")) : "";
        String newPath = parent.isEmpty() ? newName : parent + "/" + newName;

        return given()
                .spec(spec)
                .queryParam("from", path)
                .queryParam("path", newPath)
                .when()
                .post("/resources/move");
    }
}