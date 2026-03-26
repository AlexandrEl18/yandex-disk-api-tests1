package utils;

import com.github.javafaker.Faker;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TestData {

    private static final Faker faker = new Faker();

    public static String folderName() {
        return "test_" +
                faker.color().name() + "_" +
                faker.animal().name();
    }


    public static File tempFile() {
        try {
            String fileName = "file_" + faker.color().name() + "_" + faker.animal().name();

            File file = File.createTempFile(fileName, ".txt");

            try (FileWriter writer = new FileWriter(file)) {
                writer.write("Тестовый контент: " + faker.lorem().sentence());
            }

            file.deleteOnExit();
            return file;
        } catch (IOException e) {
            throw new RuntimeException("Не удалось создать временный файл", e);
        }
    }
}