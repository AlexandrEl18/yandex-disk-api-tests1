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
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import static assertions.ApiAssertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class FolderPositiveTest {

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
    @DisplayName("Создание папки")
    void createFolder() {
        String folder = TestData.folderName();
        Response response = steps.createFolder(folder);
        createdFolders.add(folder);
        assertThat(response).statusCode(201);
    }

    @Test
    @DisplayName("Получение созданной папки")
    void getFolder() {
        String folder = TestData.folderName();
        steps.createFolder(folder);
        createdFolders.add(folder);

        Response response = steps.getFolder(folder);
        assertThat(response).statusCode(200).name(folder);
    }

    @Test
    @DisplayName("Удаление папки")
    void deleteFolder() {
        String folder = TestData.folderName();
        steps.createFolder(folder);
        createdFolders.add(folder);

        steps.deleteFolder(folder);

        createdFolders.remove(folder);

        Response response = steps.getFolder(folder);
        assertThat(response).statusCode(404);
    }

    @Test
    @DisplayName("Создание нескольких папок")
    void createMultipleFolders() {
        for (int i = 0; i < 3; i++) {
            String folder = TestData.folderName() + "_" + i;
            Response response = steps.createFolder(folder);
            createdFolders.add(folder);
            assertThat(response).statusCode(201);
        }
    }

    @Test
    @DisplayName("Цикл создания, получения и удаления папки")
    void createGetDeleteCycle() {
        String folder = TestData.folderName();
        steps.createFolder(folder);
        createdFolders.add(folder);

        assertThat(steps.getFolder(folder)).statusCode(200);

        steps.deleteFolder(folder);
        createdFolders.remove(folder);

        assertThat(steps.getFolder(folder)).statusCode(404);
    }

    @Test
    @DisplayName("Загрузка файла в существующую папку")
    void uploadFileToExistingFolder() {
        String folder = TestData.folderName();
        steps.createFolder(folder);
        createdFolders.add(folder);

        File file = new File("src/test/resources/sample.txt");
        Response response = steps.uploadFile(folder + "/sample.txt", file);
        assertThat(response).statusCode(201);
    }

    @Test
    @DisplayName("Переименование существующей папки")
    void updateExistingFolderName() {
        String folder = TestData.folderName();
        steps.createFolder(folder);
        createdFolders.add(folder);

        String newName = "new_" + TestData.folderName();
        Response response = steps.updateFolderName(folder, newName);
        assertThat(response).statusCode(201);

        createdFolders.remove(folder);
        createdFolders.add(newName);

        assertThat(steps.getFolder(folder)).statusCode(404);
        assertThat(steps.getFolder(newName)).statusCode(200);
    }
}