package com.example.fenix.postlikes;

import java.util.UUID;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List;

@RestController
@RequestMapping("/post-likes")
public class PostLikeController {

    private final PostLikeService postLikeService;

    public PostLikeController(PostLikeService postLikeService) {
        this.postLikeService = postLikeService;
    }

    @GetMapping
    public List<PostLike> list() {
        return postLikeService.findAll();
    }

    @GetMapping("/{id}")
    public PostLike getById(@PathVariable UUID id) {
        return postLikeService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostLike create(@RequestBody PostLike postLike) {
        return postLikeService.create(postLike);
    }

    @PutMapping("/{id}")
    public PostLike update(@PathVariable UUID id, @RequestBody PostLike postLike) {
        return postLikeService.update(id, postLike);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        postLikeService.delete(id);
    }
}