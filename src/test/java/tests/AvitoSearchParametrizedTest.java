package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;



public class AvitoSearchParametrizedTest {
    @BeforeEach
    public void setUp(){
        open("https://www.avito.ru/moskva");
    }

    @AfterEach
    public void tearDown(){
        closeWebDriver();
    }


    @ValueSource(strings = {"iphone", "ipad", "airpods", "macbook"})
    @ParameterizedTest(name = "Тест для поиска на авито слова {0}")
    @Tag("critical")
    public void avitoSearchParametrizedValueSourceTest(String searchRequest){
        $("input[type='text']").click();
        $("input[type='text']").setValue(searchRequest).pressEnter();
        $("div[data-marker='item']").shouldHave(text(searchRequest));
    }


    @CsvSource(value = {
            "iphone | iphone",
            "ipad | ipad",
            "airpods | airpods",
            "macbook | macbook"},
    delimiter = '|')
    @ParameterizedTest(name = "Тест для поиска на авито слова {0} и проверка слова {1}")
    @Tag("critical")
    public void avitoSearchParametrizedCsvSourceTest(String searchRequest, String searchResponse){
        $("input[type='text']").click();
        $("input[type='text']").setValue(searchRequest).pressEnter();
        $("div[data-marker='item']").shouldHave(text(searchResponse));
    }


    @EnumSource(SearchWords.class)
    @ParameterizedTest(name = "Тест для поиска на авито слова {0}")
    @Tag("critical")
    public void avitoSearchParametrizedEnumSourceTest(SearchWords searchWords){
        $("input[type='text']").click();
        $("input[type='text']").setValue(searchWords.name()).pressEnter();
        $("div[data-marker='item']").shouldHave(text(searchWords.name()));
    }


    static Stream<Arguments>avitoSearchParametrizedMethodSourceTest(){
        return Stream.of(
                Arguments.of("iphone", "iphone"),
                Arguments.of("ipad", "ipad"),
                Arguments.of("airpods", "airpods"),
                Arguments.of("ipad", "macbook")
        );
    }

    @MethodSource
    @ParameterizedTest(name = "Тест для поиска на авито слова {0} и проверки текста {1}")
    @Tag("critical")
    public void avitoSearchParametrizedMethodSourceTest(String searchRequest, String searchResponse){
        $("input[type='text']").click();
        $("input[type='text']").setValue(searchRequest).pressEnter();
        $("div[data-marker='item']").shouldHave(text(searchResponse));
    }
}
