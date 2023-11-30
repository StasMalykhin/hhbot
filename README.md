<div align="center"> <h3 align="center"> Telegram/HHBot New Vacancies! </h3> </div>


Телеграм бот для уведомления о новых вакансиях по Java в Москве и Санкт-Петербурге, опубликованных на сайте HeadHunter.ru.
___
#### ОБРАТИТЕ ВНИМАНИЕ, ЧТО ЭТОТ ПРОЕКТ НЕ СМОЖЕТ ЗАПУСТИТЬСЯ
Из-за ограничений безопасности этот репозиторий не содержит токенов.

Если вы хотите запустить этот код, выполните следующие действия.

Добавить значения следующих свойств в файл конфигурации application.properties:
- токен для API HH (headhunter.accessToken);
- свойства Телеграм бота (telegrambot.botUsername, telegrambot.botToken, telegrambot.webHookPath);
- свойства БД (spring.datasource.url, spring.datasource.username, spring.datasource.password).

___
### Используемые технологии
Java 17, Spring (Boot, Data JPA, Scheduled), PostgreSQL, Lombok, API HeadHunter.

___
### Функции
- Каждые 10 минут проверяет на сайте HeadHunter наличие новых вакансий по Java в Москве и Cанкт-Петербурге. Найденные вакансии отправляются пользователю в чат.

___
### Авторы
* Stanislav Malykhin - [StasMalykhin](https://github.com/StasMalykhin)

___
### Лицензия
This project is Apache License 2.0 - see the [LICENSE](LICENSE) file for details

___
### Внесение изменений
Не стесняйтесь предлагать новые функции через [github issue](https://github.com/StasMalykhin/hhbot/issues/new).
Обратите внимание, что новые функции должны быть предварительно одобрены до внедрения.
