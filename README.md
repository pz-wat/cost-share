# COST-SHARE

### Wymagania do uruchomienia backendu:
- Java 11+
- MySQL 8.0+

### Uruchamianie backendu:

```
git clone https://github.com/pz-wat/cost-share.git
```
1. W ``` backend/src/main/resources/db ``` znajdują się dwa skrypty. Trzeba uruchomić user.sql na użytkowniku root mysql'a
2. Potem wejść na utworzonego użytkownika costshare (hasło/login : costshare) i tam uruchomić skrypt schema.sql
3. Wejść z terminala w folder backendu i uruchomić program za pomocą:
 - Dla cmd Windowsa:
```
mvnw.cmd spring-boot:run
```
- Dla Linux/ Ubuntu for Windows:
```
./mvnw spring-boot:run
```

### REST API Endpoints:
1. http://localhost:8080/api/auth/signin
- Body:
```
{
    "username": "jakisusername",
    "password": "jakieshaslo"
}
```
2. http://localhost:8080/api/auth/signup
- Body (username -> znakow min: 3 max: 20 | email -> znakow max: 50 | password -> znakow min: 6 max: 40):
```
{
    "username": "jakisusername",
    "email": "jakisemail@example.com",
    "password": "jakieshaslo"
}
```

Jakby coś nie działało/pytania to piszcie
