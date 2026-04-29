package com.example.fenix.posts;

import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findById(UUID id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
    }

    public Post create(Post post) {
        post.setId(null);
        if (post.getActive() == null) {
            post.setActive(true);
        }
        return postRepository.save(post);
    }

    public Post update(UUID id, Post post) {
        Post existingPost = findById(id);
        
        if (post.getTitle() != null) {
            existingPost.setTitle(post.getTitle());
        }

        if (post.getBody() != null) {
            existingPost.setBody(post.getBody());
        }

        if (post.getActive() != null) {
            existingPost.setActive(post.getActive());
        }

        if (post.getUser() != null) {
            existingPost.setUser(post.getUser());
        }

        return postRepository.save(existingPost);
    }

    public void delete(UUID id) {
        if (!postRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
        postRepository.deleteById(id);
    }
}