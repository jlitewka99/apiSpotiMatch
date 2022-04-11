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
