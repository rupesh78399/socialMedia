package com.example.socialmedia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @PostMapping("/create")
    public Post createPost(@RequestBody Post post) {
        post.setCreatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    @PostMapping("/like/{id}")
    public ResponseEntity<?> likePost(@PathVariable Long id){

        Post post = postRepository.findById(id).orElse(null);

        if(post == null){
            return ResponseEntity.badRequest().body("Post not found");
        }

        post.setLikes(post.getLikes() + 1);

        postRepository.save(post);

        return ResponseEntity.ok(post);
    }

    @PostMapping("/comment")
    public ResponseEntity<?> commentPost(@RequestBody Comment comment){

        comment.setCreatedAt(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
        System.out.println("PostId = " + comment.getPostId());
        System.out.println("Comment = " + comment.getComment());
        return ResponseEntity.ok(commentRepository.save(comment));
    }

    @GetMapping("/comments/{postId}")
    public List<Comment> getComments(@PathVariable Long postId){

        return commentRepository.findByPostIdOrderByCreatedAtDesc(postId);

    }

    @GetMapping("/getPost")
    public Object getPosts() {

        return postRepository.findAll();
    }

    @DeleteMapping("/deletePost/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {

        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Post not found");
        }

        postRepository.delete(optionalPost.get());

        return ResponseEntity.ok("Post deleted successfully");
    }
}
