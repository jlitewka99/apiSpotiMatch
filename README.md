# Spotimatch-API


## Creating a user

`POST /register`

Payload:
```json
{
  "email": "test@gmail.com",
  "password": "password123"
}
```

Response:
```json
{
    "id": 1,
    "email": "test@gmail.com"
}
```


## Authenticating

`POST /auth`

Payload:  
```json
{
    "email": "test@gmail.com",
    "password": "password123"
}
```

Reponse:
```json
{
    "jwt": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzaDQiLCJleHAiOjE2NDk3MTk2NTIsImlhdCI6MTY0OTcxNjA1Mn0.SOBGleBZnZ-nBRMRtAX703CYZzt1OP-vbZItOs9lrVo"
}
```

Authentication is done by Bearer Token.  
In order to access any endpoint, you need to pass valid token inside request.

## Info about user

`GET /me`

Response:
```json
{
    "id": 1,
    "email": "sh4",
    "password": "test",
    "name": "test123",
    "age": 123,
    "bio": "testBio",
    "picture": null
}
```

Prints all information about current logged user

## Updating user

`PUT /me`

Payload:
```json
{
    "name": "new name",
    "age": "26",
    "bio": "test"
}
```

You can update all info about user except: 
- ID
- Email
- Password


## Fetching user

`GET /user/{id}`

Response:
```json
{
    "id": 1,
    "email": "test@gmail.com"
}
```