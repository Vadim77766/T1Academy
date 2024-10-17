Задание 6 UI tests Actions

Задание выполнено в двух вариантах
1) тесты на Selenium
2) тесты на Selenide

Запуск

./gradlew clean
./gradlew ui-tests:test --tests "ru.t1academy.autotests.uitests.task6.SeleniumTest"
./gradlew ui-tests:test --tests "ru.t1academy.autotests.uitests.task6.SelenideTest"
./gradlew ui-tests:allureServe
ctrl + c

++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

Задание 5 UI tests Добавить проверки в тесты, подключение отчетов Allure

Задание выполнено в двух вариантах
1) тесты на Selenium с проверками AssertJ, в том числе несколько кастомных, и отчетом Allure
2) тесты на Selenide с проверками should,  и более детальном отчетом Allure

Запуск

./gradlew clean
./gradlew ui-tests:test --tests "ru.t1academy.autotests.uitests.task5.SeleniumTest"
./gradlew ui-tests:test --tests "ru.t1academy.autotests.uitests.task5.SelenideTest"
./gradlew ui-tests:allureServe
ctrl + c


Задание 4 UI tests

./gradlew clean
./gradlew ui-tests:test --tests "ru.t1academy.autotests.uitests.task4.SeleniumTest"
./gradlew ui-tests:test --tests "ru.t1academy.autotests.uitests.task4.SelenideTest"
./gradlew ui-tests:allureServe
ctrl + c

Задание 2  (Тестирование API с помощью библиотеки Rest Assured)

Задание находится в модуле api-tests

Запуск API тестов:

1) Запуск всех тестов
mvn clean test -Papi-tests

2) Запуск только тестов в классе
mvn clean test -Papi-tests -Dtest=<ClassName>
где <ClassName> один из вариантов : AuthTests , ProductTest, CartTests

Сформировать и просмотреть Allure отчет (только для Maven):

mvn io.qameta.allure:allure-maven:2.14.0:serve

+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

Задание 1  (Сборщики)

Варианты запуска

Maven:
mvn clean test
mvn clean test -Papi-tests
mvn clean test -Pui-tests
mvn clean test -Psmoke

Gradle:
./gradlew test
./gradlew ui-tests:test
./gradlew api-tests:test
./gradlew smoke