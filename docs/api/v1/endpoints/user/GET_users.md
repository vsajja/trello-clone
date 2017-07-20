# Register User

    GET users

## Description
Get all trello-clone users.

***

## Requires authentication

***

## Parameters

***

## Return format
A JSON array containing all registered users.

***

## Return codes

- **200 Success** — The user has been successfully registered.

***

## Example
**Request**

    GET users

**Return**
``` json
[
   {
      "userId":7,
      "username":"vsajja"
   },
   {
      "userId":17,
      "username":"sean"
   },
   {
      "userId":89,
      "username":"Class_test_user_vsajja"
   }
]
```