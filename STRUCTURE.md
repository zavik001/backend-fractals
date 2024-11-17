├── Dockerfile                                     # Конфигурация Docker
├── docker-compose.yml                             # Запуск микросервисов через Docker Compose
├── src/
│   ├── main/
│   │   │└── fractals/
│   │   │    ├── config/                           # Конфигурационные файлы (Kafka, Security)
│   │   │    ├── controller/                       # REST контроллеры
│   │   │    ├── dto/                              # DTO для передачи данных
│   │   │    ├── entity/                           # JPA сущности
│   │   │    ├── repository/                       # Репозитории (Spring Data JPA)
│   │   │    ├── service/                          # Логика генерации фракталов
│   │   │    └── FractalsApplication.java          # Главный класс приложения
│   │   ├── resources/
│   │   │   ├── static/                            # Статические файлы (CSS, JS, изображения)
│   │   │   ├── templates/                         # Thymeleaf-шаблоны
│   │   │   ├── application.properties             # Конфигурация Spring Boot
│   │   │   └── application-prod.properties        # Продакшн-конфигурация
│   │   └── webapp/
│   │       └── js/                                # Скрипты для фронтенда
│   └── test/                                      # Тесты (юнит, интеграционные)
│       └── ...
└── logs/                                          # Логи для отладки
