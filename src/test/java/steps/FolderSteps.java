package steps;

import client.DiskClient;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import java.io.File;

public class FolderSteps {

    private final DiskClient client;

    public FolderSteps(DiskClient client) {
        this.client = client;
    }

    @Step("Создаем папку: {folder}")
    public Response createFolder(String folder) {
        Response response = client.createFolder(folder);
        attachResponse("Create folder response", response);
        return response;
    }

    @Step("Получаем папку: {folder}")
    public Response getFolder(String folder) {
        Response response = client.getFolder(folder);
        attachResponse("Get folder response", response);
        return response;
    }

    @Step("Удаляем папку: {folder}")
    public Response deleteFolder(String folder) {
        Response response = client.deleteFolder(folder);
        attachResponse("Delete folder response", response);
        return response;
    }

    @Step("Загружаем файл {file.name} в папку {folder}")
    public Response uploadFile(String folder, File file) {
        Response response = client.uploadFile(folder, file);
        attachResponse("Upload file response", response);
        return response;
    }

    @Step("Обновляем имя папки с {folder} на {newName}")
    public Response updateFolderName(String folder, String newName) {
        Response response = client.updateFolderName(folder, newName);
        attachResponse("Update folder name response", response);
        return response;
    }

    private void attachResponse(String name, Response response) {
        Allure.addAttachment(name, response.asPrettyString());
    }
}