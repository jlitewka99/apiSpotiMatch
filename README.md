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

## List current user pairs

`GET /me/pairs`

Response:
```json
[
    {
        "id": 5,
        "leftUserId": 1,
        "rightUserId": 2,
        "timestamp": null
    },
    {
        "id": 6,
        "leftUserId": 3,
        "rightUserId": 1,
        "timestamp": null
    }
]
```


## List user messages

`GET /users/messages/{userId}`

Response:
```json
[
    {
        "id": 4,
        "senderId": 1,
        "recipientId": 2,
        "pairId": 3,
        "content": "siemano",
        "timestamp": "2022-05-18T22:44:31.916536Z"
    },
    {
        "id": 5,
        "senderId": 1,
        "recipientId": 2,
        "pairId": 3,
        "content": "hej",
        "timestamp": "2022-05-18T22:46:36.645308Z"
    }
]
```

## Get Pair By ID (TESTING ONLY)

`GET /pairs/{id}`

Response:
```json
{
    "id": 3,
    "leftUserId": 1,
    "rightUserId": 2,
    "timestamp": "2022-05-18T22:43:44.083846Z"
}
```

## Add Pair (TESTING ONLY)

`POST /pairs`

Payload:

```json
{
    "leftUserId": 1,
    "rightUserId": 2 
}
```

## CHAT WEBSOCKETS

### Initial connection

Connect to websocket via: `/ws` (SockJS)  
In headers specify JWT bearer token in `Authorization`, example:

```js
const Stomp = require("stompjs");
var SockJS = require("sockjs-client");
SockJS = new SockJS("http://localhost:8081/ws");
stompClient = Stomp.over(SockJS);

const auth = {Authorization: "Bearer " + token}
stompClient.connect(auth, onConnected, onError);
```

### Subscribing to queue

When connected you can subscribe to your queue via `/user/{UserID}/queue/messages`  
You can obtain UserID via `GET /me` endpoint.  
You don't need to pass Bearer token once you were verified in Initial connection.

Example:

```js
stompClient.subscribe(
    "/user/" + 1 + "/queue/messages",
    onMessageReceived
);
```

### Sending a message

In order to send a message you need to send it to `/app/chat` endpoint.  
Example:

```js
  const sendMessage = () => {
    const message = {
        recipientId: 2,
        content: "Example message content"
    };
    stompClient.send("/app/chat", {}, JSON.stringify(message));
};
```