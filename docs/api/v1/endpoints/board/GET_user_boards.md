# User Boards

    GET user/:userId/boards

## Description
Gets all user boards for the user.

***

## Requires authentication

***

## Url Parameters

- **userId** _(required)_ — The userId as a parameter.

***

## Return format
A JSON array containing all user boards.

***

## Return codes

- **200 Success** — Successfully retrieved User boards.

***

## Example
**Request**

    GET user/164/boards

**Return**
``` json
[
   {
      "boardId":242,
      "name":"BoardA",
      "userId":164
   },
   {
      "boardId":243,
      "name":"BoardB",
      "userId":164
   },
   {
      "boardId":244,
      "name":"BoardC",
      "userId":164
   }
]
```