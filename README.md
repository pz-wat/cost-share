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

Ogólnie grupy są oparte o accessCode, żeby się nie bawić w jakieś zaproszenia do znajomych itp.
Czyli każda utworzona grupa ma unikalny accessCode, który przykładowo admin może skopiować i wsyłać do reszty na messengerze, dsc etc.
Coś takiego jak na Teamsach jest.

Czyli ktoś przykładowo dostaje accessCode. Wpisuje go -> klika dołącz do grupy -> bierzesz grupę z endpointu 1. -> dodajesz chłopa endpointem 5.

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
#### Expenses

Wszystkie sie tak zaczynaja: http://localhost:8080/api

1. GET: /expense/{expenseId}
- amount to ogólna kwota wydatku
- owedAmount to już konkretna kwota jaka konkretny użytkownik jest winny
- paid to flaga (boolean), która określa osoba, która zapłaciła za dany wydatek (czyli true = ten który stworzył wydatek i on nic nie jest winny)
- settled to flaga (boolean), która określa czy dana osoba uregulowała dług

- Response body:
```
{
    "id": ,
    "name": "",
    "amount": ,
    "dateCreated": ,
    "groupId": ,
    "users": [
    {
     "id": ,
     "username": ,
     "owedAmount": ,
     "paid": ,
     "settled": 
     }
    ]
}
```

2. GET: /group/{groupId}/expense
- Lista wydatków takich jak w pkt 1. dla danej grupy od id {groupId}

3. GET: /group/{groupId}/user/{userId}/expense
- Lista wydatków takich jak w pkt 1. dla danej grupy dla danego użytkonika (tych w których brał udział dla danej grupy)

4. POST: /group/{groupId}/user/{userId}/expense
- userId określa tego który tworzy wydatek czyli on płacił za coś

-RequestBody (userIds to lista id osób które są winne pieniądze:
```
{
    "groupId": ,
    "name": "",
    "amount": ,
    "userIds": [1, 2, 3]
}
```
- ResponseBody: taki jak w pkt 1. czyli nowo utworzony wydatek

5. PUT: /expense/{expenseId}/user/{userId}/settle
- Zmienia flagę settled użytkownikowi (czyli oznaczamy go jak że uregulował dług)
- ResponseBody:
```
{
   "message": "User with provided id settled successfully!"
}
```
6. DELETE: /expense/{expenseId}
```
{
   "message": "Expense deleted successfully!"
}
```
Jakby coś nie działało/pytania to piszcie
