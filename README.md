# Spotimatch-API

## Authenticating

`POST /auth`  
Payload:  
```json
{
    "username": "any",
    "password": "password"
}
```

Authentication is done by Bearer Token.  
In order to access any endpoint, you need to pass valid token inside request
