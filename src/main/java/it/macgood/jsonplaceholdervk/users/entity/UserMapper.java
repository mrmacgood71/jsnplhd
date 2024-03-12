package it.macgood.jsonplaceholdervk.users.entity;

public class UserMapper {
    public static UserRequest toUserRequest(User user) {
        return new UserRequest(user.name(), user.username(), user.email(), user.address(), user.phone(), user.website(), user.company());
    }

    public static User toUser(UserRequest request) {
        return new User(null, request.name(), request.username(), request.email(), request.address(), request.phone(), request.website(), request.company());
    }

    public static UserResponse toUserResponse(User user) {
        return new UserResponse(user.id(), user.name(), user.username(), user.email(), user.address(), user.phone(), user.website(), user.company());
    }
}
