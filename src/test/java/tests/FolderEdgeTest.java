package tests;

import client.DiskClient;
import config.RequestSpecConfig;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import steps.FolderSteps;
import utils.TestData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static assertions.ApiAssertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class FolderEdgeTest {

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
    @DisplayName("Создание папки с пробелами")
    void folderWithSpaces() {
        String folder = "test folder " + TestData.folderName();
        createdFolders.add(folder);

        Response response = steps.createFolder(folder);
        assertThat(response).statusCode(201);
    }

    @Test
    @DisplayName("Создание папки с Unicode символами")
    void folderWithUnicode() {
        String folder = "папка_" + TestData.folderName();
        createdFolders.add(folder);

        Response response = steps.createFolder(folder);
        assertThat(response).statusCode(201);
    }

    @Test
    @DisplayName("Создание папки с очень длинным именем")
    void veryLongFolderName() {
        String folder = "a".repeat(200) + "_" + TestData.folderName();
        createdFolders.add(folder);

        Response response = steps.createFolder(folder);
        assertThat(response).statusCode(201);
    }

    @Test
    @DisplayName("Создание папки с специальными символами")
    void specialCharacters() {
        String folder = "!@#$%^&*()_" + TestData.folderName();
        createdFolders.add(folder);

        Response response = steps.createFolder(folder);
        assertThat(response).statusCode(201);

        steps.getFolder(folder).then().statusCode(200);
    }

    @Test
    @DisplayName("Загрузка файла в папку")
    void uploadFileTest() {
        String folder = TestData.folderName();
        createdFolders.add(folder);

        steps.createFolder(folder);

        File file = new File("src/test/resources/sample.txt");
        Response response = steps.uploadFile(folder + "/sample.txt", file);
        assertThat(response).statusCode(201);
    }

    @Test
    @DisplayName("Обновление имени папки")
    void updateFolderNameTest() {
        String folder = TestData.folderName();
        String newName = folder + "_updated";

        steps.createFolder(folder);
        createdFolders.add(newName);
        Response response = steps.updateFolderName(folder, newName);
        assertThat(response).statusCode(201);

        steps.getFolder(newName).then().statusCode(200);
    }
}