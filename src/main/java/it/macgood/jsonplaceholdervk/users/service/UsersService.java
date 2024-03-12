package it.macgood.jsonplaceholdervk.users.service;

import it.macgood.jsonplaceholdervk.posts.entity.PostResponse;
import it.macgood.jsonplaceholdervk.users.entity.User;
import it.macgood.jsonplaceholdervk.users.entity.UserResponse;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface UsersService {

    List<UserResponse> getUsers();
    Optional<UserResponse> getUsersById(String id);
    List<PostResponse> getPostsByUserId(String userId);
    UserResponse sendUser(User user);
    void putUser(String userId, User user);
    UserResponse patchUser(String userId, Map<String, Object> userDetails);

    void deleteUser(String userId);


}
