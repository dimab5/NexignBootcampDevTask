# Разработка – практическое задание Nexign Bootcamp
## 1) Структура приложения и схема взаимодействия сервисов
![Снимок экрана 2024-05-12 184824](https://github.com/dimab5/NexignBootcampDevTask/assets/113174105/095c00c8-c6d5-464a-b557-c67d295d4ba3)

[Общая схема.pdf](https://github.com/dimab5/NexignBootcampDevTask/files/15286999/FINAL.pdf) (то же самое файлом)

### В структрупе приложения были выделены 3 микросервиса, которые взаимодействуют через кафку:
1) [CdrGenerator](DevelopmentTask/CdrGenerator) - микросервис, который отвечает за генерацию "входных данных". Он генерирует Cdr данные, каждые 10 записывает в файл, записывает в базу данных, а также отправляет их в микросервис [Brt](DevelopmentTask/Brt) через кафку в топик `cdr_switch_to_brt`.
2) [Brt](DevelopmentTask/Brt) - микросервис, который авторизирует пользователей (проверяет, является ли пользователь абонентом "Ромашки") и снимает деньги с пользователей.
Brt читает данные из топика `cdr_switch_to_brt`, авторизирует абонентов, отправляет обновленные данные в микросервис [Hrs](DevelopmentTask/Hrs) через топик `cdr_enriched_brt_to_hrs`, получает обратно информацию о том, сколько денег нужно снять с абонентов и производит снятие денег с баланса.
3) [Hrs](DevelopmentTask/Hrs) - микросервис, который производит вычисления суммы, которую нужно списать с абонента, в соответствии с его тарифом. Hrs считывает из топика `cdr_enriched_brt_to_hrs` информацию о звонках, вычисляет длительность звонка и производит тарификацию. В топик `cost_hrs_to_brt` отправляются данные о сумме, которую нужно списать с абонентов, у которых классический тариф (нет помесячной платы, фиксированная стоимость минуты звонка). В топик `tariff_hrs_to_brt` отправляются данные о сумме, которую нужно списать с абонентов с помесяным тарифом.

## 2) Схемы баз данных
![image](https://github.com/dimab5/NexignBootcampDevTask/assets/113174105/f0f88c3e-7c04-49bc-b401-491f1c59037e)

[Схема баз данных.pdf](https://github.com/dimab5/NexignBootcampDevTask/files/15287065/ERD.final.pdf) (то же самое файлом)

### Описание:
Было принято логичное и рациональное решение создать собственную базу данных для каждого микросервиса, чтобы одно приложение (микросервис) был подключен и делал запросы ровно в одну бд. 
### Подробнее про структуру и сами таблицы:
1) Микросервис [CdrGenerator](DevelopmentTask/CdrGenerator) содержит базу данных, состоящую из двух таблиц. Таблица `clients` содержит всех существующих клиентов, которые будут учавствовать в звонках при их генерации. В таблицу `history` записываются все сгенерированные записи из cdr.
2) Микросервис [Brt](DevelopmentTask/Brt) содержит базу данных, состоящую из трех таблиц. Таблица `clients` содержит абонентов оператора "Ромашка". В таблицу `history` записываются пришедшие данные о звонках, но только абонентов оператора "Ромашка". Также таблица `history` содержит стоимость звонка и статус отправки стоимости из hrs (можно использовать эту таблицу для просмотра логов). В таблицу `tariff_payments_history' из записываются приходящие из [Hrs](DevelopmentTask/Hrs) данные о помесячных списаниях.
3) Микросервис [Hrs](DevelopmentTask/Hrs) содержит базу данных, состоящую из трех таблиц. Таблица `tariffs` содержит список всех существующих тарифов. Соответсвенно, если появится новый тариф его просто следует добавить в эту таблицу и записать правила тарификации в json в соответсвтии с его структурой (структура json с правилами тарификации также есть в сфеме таблицы). В таблицу `history` записывается история списаний. Таблица `traffic` нужна для хранения абонентов с помесячным тарифом для списания с этих абонентов определенной суммы в конце месяца (сумма прописана в таблице `tariffs` в tariff_rules). Также в ней хранится количество потраченных минут, чтобы знать, когда бесплатные минуты закончатся.

## 3) [Ссылка на Swagger](api.yaml)

## 4) Схема запуска сервисов в ручном режиме
1) Перед запуском сервисов требуется через докер запустить контейнеры: `zookeper`, `kafka`, `postgres_cdr`, `postgres_brt`, `postgres_hrs`.
2) После этого запустить сначала сервисы [Brt](DevelopmentTask/Brt), [Hrs](DevelopmentTask/Hrs), запустив соответствующие SpringBootApplication: [BrtApplication](DevelopmentTask/Brt/src/main/java/com/brt/program/BrtApplication.java), [HrsApplication](DevelopmentTask/Hrs/src/main/java/com/hrs/program/HrsApplication.java).
3) После запустить сервис [CdrGenerator](DevelopmentTask/CdrGenerator/src/main/java/com/cdrGenerator/program/CdrGeneratorApplication.java).

После проделанных пунктов запустятся три микросервиса. `CdrGenerator` сразу после запуска отправит POST запрос на генерацию данных. Таким образом данные сгенерируются, отправятся в Brt и так далее. То есть весь цикл приложения пройдет успешно и деньги снимутся с баланса абонентов. Чтобы повторно сгенерировать данные нужно отправить POST запрос по пути `http://localhost:8080/generate` и снова данные пройдут весь этот путь. Тело запроса и авторизация не требуется.

**К сожалению, сервисы запускаются только в ручном режиме, когда запуска осуществляется через docker-compose почему-то не работает кафка и не получится протестировать всё приложение если запускать .jar через docker-compose, получится протестировать только `CRM`, то есть поотправлять запросы от абонента или от менеджера. Чтобы проверить, что приложение работает полностью следует запустить сервисы в ручном режиме.**

## 5) Данные для авторизации
В проекте используется `Basic` авторизация для абонента и для менеджера. 
1) Для того, чтобы авторизировать как абонент логин/пароль `номер телефона/номер телефона`.
2) Для того, чтобы авторизироваться как менеджер логин/пароль `admin/admin`.

## 6) Данные для подключение к базам данных 
1) База данных в сервисе [CdrGenerator](DevelopmentTask/CdrGenerator): `jdbc:postgresql://localhost:5432/postgres` `user`:**postgres**, `password`:**postgres**.
2) База данных в сервисе [Brt](DevelopmentTask/Brt): `jdbc:postgresql://localhost:6432/postgres` `user`:**postgres**, `password`:**postgres**.
3) База данных в сервисе [Hrs](DevelopmentTask/Hrs): `jdbc:postgresql://localhost:7432/postgres` `user`:**postgres**, `password`:**postgres**.
   




