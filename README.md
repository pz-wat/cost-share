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

#### Security
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

#### Groups

Wszystkie sie tak zaczynaja: http://localhost:8080/api

1. GET: /group/{accessCode}
(Grupa po accessCode)
- Response body:
```
{
    "id": ,
    "name": ,
    "accessCode": ,
    "date": ,
    "isUserAdmin": ,
    "groupExpenses": [
    {
     "id": ,
     "name": ,
     "date": 
     }
    ],
    "groupUsers": [
    {
     "id": ,
     "username": 
     }
    ],
}
```

2. GET: /user/{userId}/group
(Lista grup użytkownika userId)
- Response body to lista tego co w punkcie 1

3. GET: /user/{userId}/group/{groupId}
(Konkretna grupa dla konkretnego użytkownika)
- Response body takie jak w punkcie 1

4. POST: /user/{userId}/group
(Utworzenie nowej grupy dla której twórca {userId} automatycznie staje się adminem)
- Request body
```
{
    "userId": {userId},
    "name": "nazwagrupy"
}
```
- Response body takie jak w punkcie 1 (czyli szczegółny nowo utworzonej grupy)

5. POST: /user/{userId}/group/{groupId}
(Dodanie nowego użytkownika {userId} do konkretnej istniejącej grupy {groupId})
- Response body
```
{
   "message": "User added to group successfully!"
}
```

6. DELETE: /user/{userId}/group/{groupId}
(Usunięcie użytkownika {userId} z grupy {groupId})
- Response body
```
{
   "message": "User removed from group successfully!"
}
```

7. DELETE: /group/{groupId}
(Usunięcie grupy {groupId})
- Response body
```
{
   "message": "Group deleted successfully!"
}
```


Jakby coś nie działało/pytania to piszcie
