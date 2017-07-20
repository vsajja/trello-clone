# User Boards

    POST user/:userId/boards

## Description
Creates a user board.

***

## Requires authentication

***

## Url Parameters

- **userId** _(required)_ — The userId as a parameter.

***

## Request Parameters

- **name** _(required)_ — The name of the user board.

***

## Return format
A JSON object of the created user board.

***

## Return codes

- **200 Success** — The user board has been successfully created.
- **400 Bad Request** — Invalid parameters.

***

## Example
**Request**

    POST user/164/boards

**Parameters**
``` json
{
   "name":"WelcomeBoard"
}
```

**Return**
``` json
{
  "boardId":255,
  "name":"WelcomeBoard",
  "userId":164
}
```