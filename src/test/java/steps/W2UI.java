package steps;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import hooks.DriverHooks;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static com.codeborne.selenide.Selenide.*;
import static java.lang.Thread.sleep;

public class W2UI  {

    @Given("Открываем сайт {string}")
    public void openWebSite(String url) {
        Selenide.open(url);
    }

    @And("Проверить, что открыта стартовая страница")
    public void проверитьЧтоОткрытаСтартоваяСтраница() {
        String URL = Selenide.webdriver().driver().url();
        String expectedUrl = "https://w2.dwar.ru/#";

        try {
            Assert.assertEquals("Проверка URL-адреса стартовой страницы", expectedUrl, URL);
            System.out.println("Стартовая страница успешно открыта. " + "Название стартовой страницы: " + URL);
        } catch (AssertionError e) {
            System.out.println("Ошибка: Ожидаемый URL-адрес стартовой страницы: " + expectedUrl);
            System.out.println("Фактический URL-адрес: " + URL);
               throw e;
        }
    }

    @And("Проверить, что тайтл страницы = {string}")
    public void проверитьЧтоТайтлСтраницы(String titlePage) {
        String title = Selenide.title();
        try {
            Assert.assertEquals("Проверка тайтла страницы:", titlePage, title);
            System.out.println("Тайл страницы " + titlePage + "\n Схож с тайлом \n" + title);
        } catch (AssertionError e){
            System.out.println("Ошибка: Ожидаемый тайтл: " + titlePage);
            System.out.println("Фактический тайтл: " + title);
        }
    }

    @And("Открыть новость под номером {int}")
    public void открытьНовостьПодНомером(int numberNews) {
        List<SelenideElement> newsItems = $$(By.xpath("//b[@class=\"tbl-mn_new-header\"]"));
        if (numberNews < 1 || numberNews > newsItems.size()) {
            throw new IllegalArgumentException("Номер новости вне диапазона: " + numberNews);
        }

        newsItems.get(numberNews - 1).click();
    }

    @And("Нажать на кнопку с текстом {string}")
    public void нажатьНаКнопкуСТекстом(String buttonText) {
        try {
            SelenideElement button = $(By.xpath("//b/input[@value='" + buttonText + "']"));

            button.shouldBe(Condition.disabled);

            button.click();

            System.out.println("Кнопка с текстом \"" + buttonText + "\" успешно нажата.");
        } catch (NoSuchElementException e) {
            System.out.println("Ошибка: Кнопка с текстом \"" + buttonText + "\" не найдена на странице.");
            throw e;

        }
    }

    @And("перейти на вторую вкладку браузера")
    public void перейтиНаПоследнююВкладкуБраузера() {
        switchTo().window(1);
    }
    @And("Считать кол-во вкладок")
    public void getOpenTabCount() {
        try {
            List<String> tabs = new ArrayList<>(Selenide.webdriver().driver().getWebDriver().getWindowHandles());
            int tabCount = tabs.size();
            if (tabCount < 2) {
                throw new IllegalArgumentException("Количество вкладок: " + tabCount);
            }
        } catch (Exception e) {
            System.out.println("Ошибка при получении количества вкладок: " + e.getMessage());
            throw new IllegalStateException("Не удалось получить количество открытых вкладок", e);
        }
    }

    @And("Нажать на кнопку обсудить на форуме")
    public void нажатьНаКнопку() {
        try {
            SelenideElement button = $(By.xpath("//input[@onclick]"));
            button.click();
        } catch (NoSuchElementException e) {
            throw e;
        }
    }

    @And("проверить, что в блоке отображаются списки:")
    public void проверитьЧтоВБлокеОтображаютсяСписки(DataTable table) {
        List<String> expectedList = table.asList(String.class);
        List<String> actualList = new ArrayList<>();
        List<SelenideElement> elements = $$(By.xpath("//div[@class='menuitem']/a | //div[@class='menuitemsel']"));
        int counter = 0;
        for (SelenideElement element : elements) {
            if (counter < 10) {
                actualList.add(element.getText());
                counter++;
            } else {
                break;
            }
        }
        try {
            Assert.assertEquals("Фактический список не соответствует ожидаемому", expectedList, actualList);
            System.out.println("Фактический список соответствует ожидаемому:");
            System.out.println("Ожидаемый список:");
            for (String value : expectedList) {
                System.out.println("- " + value);
            }
            System.out.println("Фактический список:");
            for (String value : actualList) {
                System.out.println("- " + value);
            }
        } catch (AssertionError e) {
            System.out.println("Фактический список не соответствует ожидаемому:");
            System.out.println("Ожидаемый список:");
            for (String value : expectedList) {
                System.out.println("- " + value);
            }
            System.out.println("Фактический список:");
            for (String value : actualList) {
                System.out.println("- " + value);
            }
            System.out.println("Отличия:");
            for (int i = 0; i < expectedList.size(); i++) {
                if (!expectedList.get(i).equals(actualList.get(i))) {
                    System.out.println("Ожидалось: " + expectedList.get(i) + ", Получено: " + actualList.get(i));
                }
            }
            throw e;
        }
    }
}
