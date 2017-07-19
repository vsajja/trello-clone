# Login User

    POST login

## Description
Attempt to login a trello-clone user.

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

## Return Codes

- **200 Success** — User has been successfully logged in.
- **400 Bad Request** — Invalid parameters.
- **401 Unauthorized** — Invalid credentials.

***

## Example
**Request**

    POST login

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