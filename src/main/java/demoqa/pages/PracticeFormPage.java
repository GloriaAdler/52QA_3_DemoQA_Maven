package demoqa.pages;

import demoqa.core.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.Arrays;
import java.util.List;

public class PracticeFormPage extends BasePage {
    public PracticeFormPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }


    @FindBy(id = "firstName")
    WebElement firstName;
    @FindBy(id = "lastName")
    WebElement lastName;
    @FindBy(id = "userEmail")
    WebElement userEmail;
    @FindBy(id = "userNumber")
    WebElement userNumber;

    public PracticeFormPage enterPersonalData(String name, String surName, String email, String number) {
        type(firstName, name);
        type(lastName, surName);
        type(userEmail, email);
        type(userNumber, number);
        System.out.printf("✅ Personal data: [%s], [%s], [%s], [%s]%n", name, surName, email, number);
        return this;
    }

    public PracticeFormPage selectGender(String gender) {
        //* Проверка на `null`
        if (gender == null) {
            throw new IllegalArgumentException("⛔ Gender can`t be `null`");
        }
        //* Проверка на пустое поле ""
        if ( gender.isEmpty()) {
            throw new IllegalArgumentException("⛔ Gender can`t be `empty`");
        }
        //* Проверка на пробелы или спецсимволы форматирования: " ", "%s"
        if ( gender.isBlank()) {
            throw new IllegalArgumentException("⛔ Gender can`t be `blank`");
        }
        //* Список допустимых значений
        List<String> validGenders = Arrays.asList("Male", "Female", "Other");

        if (!validGenders.contains(gender)) {
            throw new IllegalArgumentException("⛔ Invalid gender: [" + gender + "]. Allowed values: " + validGenders);
        }
        try {
            String xpathGender = "//label[.='" + gender + "']";
            WebElement genderLocator = driver.findElement(By.xpath(xpathGender));
            click(genderLocator);
            System.out.printf("✅ Gender: [%s]%n", gender);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("⛔ Gender element not found: [" + gender + "]. " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("❌ Error selecting gender: [" + gender + "]. " + e);
        }
        return this;
    }

    @FindBy(id = "dateOfBirthInput")
    WebElement dateOfBirthInput;

    public PracticeFormPage chooseDateAsString(String date) {
        //type(dateOfBirthInput, date);
        click(dateOfBirthInput);

        if (System.getProperty("os.name").contains("Mac")) {
            dateOfBirthInput.sendKeys(Keys.COMMAND, "a");
        } else {
            dateOfBirthInput.sendKeys(Keys.CONTROL, "a");
        }
        dateOfBirthInput.sendKeys(date);
        dateOfBirthInput.sendKeys(Keys.ENTER);
        System.out.printf("✅ Date: [%s]%n", date);
        return this;
    }

    @FindBy(id = "subjectsInput")
    WebElement subjectsInput;

    public PracticeFormPage enterSubjects(String[] subjects) {

        for (String subject : subjects) {
            type(subjectsInput, subject);
            subjectsInput.sendKeys(Keys.ENTER);
            System.out.printf("✅ Subject: [%s]%n", subject);
        }
        return this;
    }

    public PracticeFormPage chooseHobbies(String[] hobbies) {
        for (String hobby : hobbies) {
            By hobbyLocator = By.xpath("//label[.='" + hobby + "']");
            WebElement element = driver.findElement(hobbyLocator);
            click(element);
            System.out.printf("✅ Hobby: [%s]%n", hobby);
        }
        return this;
    }

    @FindBy(id = "uploadPicture")
    WebElement uploadPicture;

    public PracticeFormPage uploadPicture(String imgPath) {
        uploadPicture.sendKeys(imgPath);
        System.out.printf("✅ Image: [%s]%n", imgPath);
        // Получаем имя загруженного файла
        String uploadedFileName = uploadPicture.getAttribute("value");
        // Ожидаем, что файл успешно загрузится и отобразится
        Assert.assertTrue(uploadedFileName.contains(getFileName(imgPath)), "Ошибка: Загруженный файл не соответствует ожидаемому!");
        System.out.printf("✅ Файл [%s] успешно загружен!%n", uploadedFileName);
        return this;
    }

    private String getFileName(String filePath) {
        return filePath.substring(filePath.lastIndexOf("\\") + 1);}

    @FindBy(id = "currentAddress")
    WebElement currentAddress;

    public PracticeFormPage enterCurrentAddress(String address) {
        type(currentAddress, address);
        System.out.printf("✅ Address: [%s]%n", address);
        return this;
    }

    @FindBy(id = "state")
    WebElement stateContainer;

    @FindBy(id = "react-select-3-input")
    WebElement stateInput;

    public PracticeFormPage enterState(String state) {
        // type(stateContainer, state);
        click(stateContainer);
        stateInput.sendKeys(state);
        stateInput.sendKeys(Keys.ENTER);
        System.out.printf("✅ Address state: [%s]%n", state);
        return this;
    }

    @FindBy(id = "city")
    WebElement cityContainer;

    @FindBy(id = "react-select-4-input")
    WebElement cityInput;

    public PracticeFormPage enterCity(String city) {
        click(cityContainer);
        cityInput.sendKeys(city);
        cityInput.sendKeys(Keys.ENTER);
        System.out.printf("✅ Address city: [%s]%n", city);
        return this;
    }

    @FindBy(id = "submit")
    WebElement submitButton;

    public PracticeFormPage submitForm() {
        click(submitButton);
        return this;
    }

    @FindBy(id = "example-modal-sizes-title-lg")
    WebElement registrationModal;

    public PracticeFormPage verifySuccessRegistration(String textToCheck) {
        shouldHaveText(registrationModal, textToCheck, 5000);
        System.out.printf("✅ Registration success: [%s]%n", textToCheck);
        return this;
    }
}
