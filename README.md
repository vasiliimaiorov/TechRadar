# Распределение работы backend
Чернова Елизавета - сущности TechSolution and Characteristics и все с ними связанное

Канукова Ева - User and Score

## Для backend
```
mvn clean package -DskipTests
```
```
docker-compose -f docker-compose-dev.yaml up --build -d
```
```
docker-compose down
```
Если запуск через IDEA -> edit configuration 
1. Compose files: `./docker-compose-dev.yaml`
2. Modify options -> `build -> always`
3. Before Launch -> + -> run maven goal -> command line: `clean package -DskipTests`

## Для остальных
Скачать Docker Desktop.

Запуск осуществляется командой ниже `в папке t1 !!!` При запущенном Docker Desktop. После запуска может быть придется немного (много) подождать.
```
docker-compose -f docker-compose.yaml up --build -d
```

Остановить всю эту шнягу (в той же папке):
```
docker-compose down
```

Документация API в Swagger доступна по URL:

```
http://localhost:8080/swagger-ui/index.html
```
Первым делом посылаем запрос на /api/auth/login

**Пользователи:**
* обычный 
  * login: user
  * password: 123
* админ
  * login: admin
  * password: 123

Копируем accessToken из ответа и вставляем поле authorize в начале страницы.
Теперь будут доступны остальные эндпоинты с авторизацией.


для удобства вставки

{
"login": "user",
"password": "123"
}