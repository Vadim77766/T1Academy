package ru.t1academy.autotests.uitests.task7;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.t1academy.autotests.uitests.task7.pages.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class SelenideTest extends SelenideBaseTest {

    @Tag("regression")
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    @DisplayName("Tests on page Checkboxes")
    public void testCheckBoxes(boolean isForward) {
        CheckboxesPage page = new CheckboxesPage("checkboxes");

        if(isForward) {
            page.forwardCheckboxesClick();
        } else {
            page.reverseCheckboxesClick();
        }
        CheckboxesAssert.assertThat(page)
                .firstCheckboxIsVisible()
                .secondCheckboxIsVisible()
                .firstCheckboxIsSelected()
                .secondCheckboxIsNotSelected();
    }

    @Tag("smoke")
    @Tag("regression")
    @Test
    @DisplayName("Tests on page Dropdown")
    public void testDropdown() {
        DropdownPage page = new DropdownPage("dropdown");
        String option = "Option 1";
        page.selectOption(option);
        DropdownAssert.assertThat(page)
                .hasSelectedOption(option);
        option = "Option 2";
        page.selectOption(option);
        DropdownAssert.assertThat(page)
                .hasSelectedOption(option);
    }

    @Tag("regression")
    @RepeatedTest(value = 3, failureThreshold = 2)
    @DisplayName("Tests on page Disappearing Elements")
    public void testDisappearingElements() {
        DisappearingElementsPage page = new DisappearingElementsPage("disappearing_elements");
        DisappearingElementsAssert.assertThat(page)
                        .numberOfElementsIsEqual(5);
    }

    @Tag("regression")
    @TestFactory
    @DisplayName("Tests on page Inputs")
    List<DynamicTest> testInputs() {
        String[] data = new String[] {"123", "321", " 123", "123 ", "-123", "123foo", "f123oo", "&*^", "1-1", "-1-1", "  "};
        List<DynamicTest> list = new ArrayList<>();
        for(String input : data) {
            list.add(dynamicTest(String.format("Input \"%s\"", input),
                    () -> {
                        InputsPage page = new InputsPage("inputs");
                        page.sendKeys(input);
                        String expectedValue = input;
                        if(input.indexOf("-",1) >= 1)
                            expectedValue = "";
                        expectedValue = expectedValue.replaceAll("[^0-9\\-]", "");
                        InputsAssert.assertThat(page)
                                        .inputHasValue(expectedValue);
                    }));
        }
        return list;
    }
}
