# Register User

    POST register

## Description
Registers a trello-clone user.

***

## Requires authentication
TODO

***

## Parameters

- **username** _(required)_ — The username for the User.
- **password** _(required)_ — The password for the User.

***

## Return format
A JSON object containing the registered user.

***

## Return codes

- **200 Success** — The user has been successfully registered.
- **400 Bad Request** — Invalid parameters.
- **409 Conflict** — The user already exists.

***

## Example
**Request**

    POST register

**Parameters**
``` json
{
   "username":"test_user_vsajja",
   "password":"test_user_vsajja_password"
}
```

**Return**
``` json
{
   "userId":62,
   "username":"test_user_vsajja",
   "password":"$2a$06$20VwvmQ7A5gYVIpWzngQ5eWbT.wPcUr/ICjJFvSduzWa80VDFyVo."
}
```