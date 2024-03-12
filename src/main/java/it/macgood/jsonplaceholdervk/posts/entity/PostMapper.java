package it.macgood.jsonplaceholdervk.posts.entity;

import it.macgood.jsonplaceholdervk.albums.entity.Album;
import it.macgood.jsonplaceholdervk.albums.entity.AlbumRequest;
import it.macgood.jsonplaceholdervk.albums.entity.AlbumResponse;

public class PostMapper {
    public static PostRequest toPostRequest(Post post) {
        return new PostRequest(post.userId(), post.title(), post.body());
    }

    public static Post toPost(PostRequest request) {
        return new Post(request.userId(), null, request.title(), request.body());
    }
    public static PostResponse toPostResponse(Post post) {
        return new PostResponse(post.userId(), post.id(), post.title(), post.body());
    }
}
