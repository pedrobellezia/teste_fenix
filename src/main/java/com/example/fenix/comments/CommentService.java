package com.example.fenix.comments;

import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public Comment findById(UUID id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));
    }

    public Comment create(Comment comment) {
        comment.setId(null);
        if (comment.getActive() == null) {
            comment.setActive(true);
        }
        return commentRepository.save(comment);
    }

    public Comment update(UUID id, Comment comment) {
        Comment existingComment = findById(id);

        if (comment.getBody() != null) {
            existingComment.setBody(comment.getBody());
        }

        if (comment.getActive() != null) {
            existingComment.setActive(comment.getActive());
        }

        if (comment.getUser() != null) {
            existingComment.setUser(comment.getUser());
        }

        if (comment.getPost() != null) {
            existingComment.setPost(comment.getPost());
        }

        return commentRepository.save(existingComment);
    }

    public void delete(UUID id) {
        if (!commentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found");
        }
        commentRepository.deleteById(id);
    }
}