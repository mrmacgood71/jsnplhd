package it.macgood.jsonplaceholdervk.posts.entity;

public record Comment(
        Integer postId,
        Integer id,
        String name,
        String email,
        String body
) {
}
