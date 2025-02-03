# Сервис банковских рекомендаций
## Описание
Сервис предоставляет персонализированные рекомендации банковских продуктов на основе анализа транзакций клиентов. Включает в себя REST API и Telegram-бот для удобного взаимодействия с пользователями.
## Основные возможности
- Создание и управление динамическими правилами рекомендаций.
- Анализ транзакций пользователей.
- Предоставление персонализированных рекомендаций.
- Статистика использования рекомендаций.
- Telegram-бот для получения рекомендаций.
- Кэширование результатов для оптимизации производительности.
## Технологический стек
- Java 17.
- Spring Boot.
- Spring Data JDBC.
- H2 Database.
- HikariCP.
- Lombok.
- Apache Commons Lang 3.
- Java Telegram Bot API (pengrad).
- SLF4J (для логирования. Включён автоматически через зависимости Spring Boot).
- Mockito.
- JUnit.
- OpenAPI (Swagger).
- Caffeine.
## Зависимости
- spring-boot-starter-web: для создания RESTful API.
- spring-boot-starter-data-jdbc: для работы с реляционными базами данных.
- spring-boot-starter-cache: для кэширования с помощью Spring.
- spring-boot-starter-test: для тестирования приложений Spring.
- mockito-core и mockito-junit-jupiter: для unit-тестирования с моками.
- junit: для написания и выполнения модульных тестов.
- commons-lang3: утилиты для работы со строками, объектами, числами и другими стандартными Java типами.
- lombok: для автоматической генерации кода.
- springdoc-openapi-starter-webmvc-ui: для автоматической генерации документации OpenAPI.
- caffeine: для кэширования данных.
- java-telegram-bot-api (pengrad): для создания Telegram-ботов.
  
## Структура проекта
src/main/java/pro/sky/recommendations/

├── database/ # Конфигурация базы данных. 

├── management/ # Управление приложением. 

├── recommendation/ # Динамические правила рекомендаций.

├── stats/ # Статистика использования. 

├── tg_bot/ # Telegram бот. 

├── top_recommendations/ # Статические правила рекомендаций.

└── user_recommendation/ # Рекомендации для пользователей.

## API Endpoints
### Управление динамическими правилами рекомендаций
- POST /rule - Создание нового правила рекомендации.
- GET /rule/{rule_id} - Получение правила по ID.
- GET /rule - Получение всех правил.
- DELETE /rule/{rule_id} - Удаление правила.
  
### Рекомендации для пользователей
- GET /recommendation/{user_id} - Получение рекомендаций для пользователя.
### Управление системой
- POST /management/clear-cache - Очистка кэша.
- GET /management/info - Информация о системе.
### Статистика
- GET /rule/stats - Получение статистики использования рекомендаций.
## Telegram Bot
Бот поддерживает следующие команды:
- /start - Начало работы с ботом.
- /recommend <Имя Фамилия> - Получение персональных рекомендаций.
## Настройка и запуск
### Требования
- JDK 17 или выше.
- Maven.
### Конфигурация
1. Создайте файл application.properties в директории `src/main/resources/`, если он ещё не существует.
2. Добавьте необходимые настройки в файл application.properties:
```properties
// Настройки для базы данных (замените на путь к вашей базе данных)
application.recommendation-db.url=jdbc:h2:file:./db/recommendation

// Токен Telegram-бота (замените на реальный токен, который вы получите после создания бота в BotFather)
telegram.bot.token=your_telegram_bot_token 
```
### Запуск
Соберите проект с помощью Maven:
 ```bash 
mvn clean install
```
Запустите приложение:
```bash 
java -jar target/recommendations-service.jar
``` 
## Авторы
Powered by ©AYE.team
## Лицензия
Все права защищены ©AYE.team
