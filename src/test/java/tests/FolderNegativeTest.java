package tests;

import client.DiskClient;
import config.RequestSpecConfig;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import steps.FolderSteps;
import utils.TestData;

import java.util.ArrayList;
import java.util.List;

import static assertions.ApiAssertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class FolderNegativeTest {

    DiskClient client = new DiskClient(RequestSpecConfig.spec());
    FolderSteps steps = new FolderSteps(client);

    private final List<String> createdFolders = new ArrayList<>();

    @AfterEach
    void cleanup() {
        for (String folder : createdFolders) {
            steps.deleteFolder(folder);
        }
        createdFolders.clear();
    }

    @Test
    @DisplayName("GET несуществующей папки")
    void getNonExistingFolder() {
        String folder = "fake_" + TestData.folderName();
        Response response = steps.getFolder(folder);
        assertThat(response).statusCode(404);
    }

    @Test
    @DisplayName("DELETE несуществующей папки")
    void deleteNonExistingFolder() {
        String folder = "fake_" + TestData.folderName();
        Response response = steps.deleteFolder(folder);
        assertThat(response).statusCode(404);
    }

    @Test
    @DisplayName("Создание папки с пустым путём")
    void invalidPath() {
        Response response = steps.createFolder("");
        assertThat(response).statusCode(400);
    }

    @Test
    @DisplayName("Создание уже существующей папки")
    void createExistingFolder() {
        String path = "test_" + TestData.folderName();
        steps.createFolder(path);
        createdFolders.add(path); // чтобы потом удалить

        Response response = steps.createFolder(path);
        assertThat(response).statusCode(409);
    }

    @Test
    @DisplayName("Попытка переименовать несуществующую папку")
    void updateNonExistingFolder() {
        String folder = "fake_" + TestData.folderName();
        String newName = "new_" + TestData.folderName();

        Response response = steps.updateFolderName(folder, newName);
        assertThat(response).statusCode(404);
    }
}