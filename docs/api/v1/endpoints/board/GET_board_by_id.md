# User Boards

    GET boards/:boardId

## Description
Gets the board by the given boardId.

***

## Requires authentication

***

## Url Parameters

- **boardId** _(required)_ — The boardId as a parameter.

***

## Return format
A JSON object representing the board.

***

## Return codes

- **200 Success** — Successfully retrieved the board.
- **404 Not Found** — Board not found.

***

## Example
**Request**

    GET boards/253

**Return**
``` json
{
   "boardId":253,
   "name":"BoardSpec_WelcomeBoard",
   "userId":176
}
```