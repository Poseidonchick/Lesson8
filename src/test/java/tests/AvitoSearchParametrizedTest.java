package tests;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class AvitoSearchParametrizedTest {
    @ValueSource(strings = {"iphone", "ipad"})
    @ParameterizedTest(name = "Тест для поиска на авито слова {0}")
    public void avitoSearchParametrizedValueSourceTest(String searchRequest){
        open("https://www.avito.ru/moskva");
        $("input[type='text']").click();
        $("input[type='text']").setValue(searchRequest).pressEnter();
        $("div[data-marker='item']").shouldHave(text(searchRequest));
    }


    @CsvSource(value = {
            "iphone | iphone",
            "ipad | ipad"},
    delimiter = '|')
    @ParameterizedTest(name = "Тест для поиска на авито слова {0} и проверка слова {1}")
    public void avitoSearchParametrizedCsvSourceTest(String searchRequest, String searchResponse){
        open("https://www.avito.ru/moskva");
        $("input[type='text']").click();
        $("input[type='text']").setValue(searchRequest).pressEnter();
        $("div[data-marker='item']").shouldHave(text(searchResponse));
    }


    @EnumSource(SearchWords.class)
    @ParameterizedTest(name = "Тест для поиска на авито слова {0}")
    public void avitoSearchParametrizedEnumSourceTest(SearchWords searchWords){
        open("https://www.avito.ru/moskva");
        $("input[type='text']").click();
        $("input[type='text']").setValue(searchWords.name()).pressEnter();
        $("div[data-marker='item']").shouldHave(text(searchWords.name()));
    }


    static Stream<Arguments>avitoSearchParametrizedMethodSourceTest(){
        return Stream.of(
                Arguments.of("iphone", "iphone"),
                Arguments.of("ipad", "ipad")
        );
    }

    @MethodSource
    @ParameterizedTest(name = "Тест для поиска на авито слова {0} и проверки текста {1}")
    public void avitoSearchParametrizedMethodSourceTest(String searchRequest, String searchResponse){
        open("https://www.avito.ru/moskva");
        $("input[type='text']").click();
        $("input[type='text']").setValue(searchRequest).pressEnter();
        $("div[data-marker='item']").shouldHave(text(searchResponse));
    }
}
