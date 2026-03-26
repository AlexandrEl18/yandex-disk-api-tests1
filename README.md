# Yandex Disk API Tests

Пример проекта автотестов для API Яндекс.Диска.

Проект использует тестовое окружение (полигон) и проверяет работу методов:
GET, POST, PUT, DELETE.

---

## Структура проекта

assertions — кастомные проверки (ApiAssertions)  
client — BaseClient, DiskClient  
config — BaseConfig, RequestSpecConfig, ResponseSpecConfig  
models — ErrorsResponse  
steps — FolderSteps  
tests — FolderPositiveTest, FolderNegativeTest, FolderEdgeTest  
utils — TestData, TokenProvider, AllureUtils  
resources — sample.txt  
.env — токен

---

## Технический стек

Java 17  
JUnit 5  
RestAssured  
Gradle

Дополнительно используются:
Faker (генерация данных), Allure (отчеты), Lombok

---

## Настройка

Создать файл .env в корне проекта:

YANDEX_TOKEN=ваш_oauth_token

---

## Запуск

./gradlew clean test

Отчет:
build/reports/tests/test/index.html

---

## Что проверяется

Позитивные тесты:
- создание папки
- получение папки
- удаление папки
- загрузка файла
- переименование папки

Негативные тесты:
- работа с несуществующими папками
- создание с некорректным путём
- дубликаты

Edge-case тесты:
- пробелы
- unicode
- спецсимволы
- длинные имена

---

## Особенности

- используются уникальные данные (Faker)
- есть автоочистка после тестов (@AfterEach)

---

## Автор

AlexandrEl18
GitHub: https://github.com/AlexandrEl18