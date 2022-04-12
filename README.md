# Spotimatch-API


## Creating a user

`POST /register`

Example payload:
```json
{
  "email": "test@gmail.com",
  "password": "password123"
}
```

Example response:
```json
{
    "id": 1,
    "email": "test@gmail.com"
}
```


## Authenticating

`POST /auth`

Example payload:
```json
{
    "email": "test@gmail.com",
    "password": "password123"
}
```

Example response:
```json
{
    "jwt": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzaDQiLCJleHAiOjE2NDk3MTk2NTIsImlhdCI6MTY0OTcxNjA1Mn0.SOBGleBZnZ-nBRMRtAX703CYZzt1OP-vbZItOs9lrVo"
}
```

Authentication is done by Bearer Token.  
In order to access any endpoint, you need to pass valid token inside request.
