package it.macgood.jsonplaceholdervk.posts.service;

import it.macgood.jsonplaceholdervk.albums.entity.Album;
import it.macgood.jsonplaceholdervk.albums.entity.AlbumResponse;
import it.macgood.jsonplaceholdervk.photos.Photo;
import it.macgood.jsonplaceholdervk.posts.entity.Comment;
import it.macgood.jsonplaceholdervk.posts.entity.Post;
import it.macgood.jsonplaceholdervk.posts.entity.PostResponse;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface PostsService {

    List<PostResponse> getPosts();
    Optional<PostResponse> getPostById(String id);
    List<PostResponse> getPostsByUserId(String userId);
    List<Comment> getCommentsByPostId(String postId);
    PostResponse sendPost(Post post);
    void putPost(String postId, Post post);
    PostResponse patchPost(String postId, Map<String, Object> postDetails);

    void deletePost(String postId);


}
