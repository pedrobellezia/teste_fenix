package com.example.fenix.postlikes;

import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.List;

@Service
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;

    public PostLikeService(PostLikeRepository postLikeRepository) {
        this.postLikeRepository = postLikeRepository;
    }

    public List<PostLike> findAll() {
        return postLikeRepository.findAll();
    }

    public PostLike findById(UUID id) {
        return postLikeRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "PostLike not found"));
    }

    public PostLike create(PostLike postLike) {
        postLike.setId(null);
        return postLikeRepository.save(postLike);
    }

    public PostLike update(UUID id, PostLike postLike) {
        PostLike existingPostLike = findById(id);
        
        if (postLike.getUser() != null) {
            existingPostLike.setUser(postLike.getUser());
        }

        if (postLike.getPost() != null) {
            existingPostLike.setPost(postLike.getPost());
        }

        return postLikeRepository.save(existingPostLike);
    }

    public void delete(UUID id) {
        if (!postLikeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "PostLike not found");
        }
        postLikeRepository.deleteById(id);
    }
}