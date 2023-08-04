package com.myblogrestapi;

import com.myblogrestapi.Controller.PostController;
import com.myblogrestapi.Entity.PostDTO;
import com.myblogrestapi.Exception.ResourceNotFoundException;
import com.myblogrestapi.Repository.PostRepository;
import com.myblogrestapi.Service.PostService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@SpringBootTest(classes = PostControllerMockTest.class)
public class PostControllerMockTest {

    @Mock
    private PostService postService;
    @InjectMocks
    private PostController postController;

    @Test
    void testCreatePost() {
        // given
        PostDTO postDto = new PostDTO();
        postDto.setTitle("Test Title");
        postDto.setContent("Test Content");

        PostDTO resultDto = new PostDTO();
        resultDto.setId(1L);
        resultDto.setTitle(postDto.getTitle());
        resultDto.setContent(postDto.getContent());

        given(postService.createNewpost(postDto)).willReturn(resultDto);

        // when
        ResponseEntity<PostDTO> response = postController.createPost(postDto);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(resultDto);
    }

    @Test
    void testGetAllPosts() {
        // given
        List<PostDTO> postList = new ArrayList<>();
        PostDTO post1 = new PostDTO();
        post1.setId(1L);
        post1.setTitle("Test Title 1");
        post1.setContent("Test Content 1");
        postList.add(post1);

        PostDTO post2 = new PostDTO();
        post2.setId(2L);
        post2.setTitle("Test Title 2");
        post2.setContent("Test Content 2");
        postList.add(post2);

        given(postService.getAllPosts()).willReturn(postList);

        // when
        ResponseEntity<List<PostDTO>> response = postController.getAllPosts();

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(postList);
    }
    @Test
    void testGetPostById() {
        // given
        Long id = 1L;
        PostDTO postDto = new PostDTO();
        postDto.setId(id);
        postDto.setTitle("Test Title");
        postDto.setContent("Test Content");

        given(postService.getPostById(id)).willReturn(postDto);

        // when
        ResponseEntity<PostDTO> response = postController.getPostById(id);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(postDto);
    }
    @Test
    void testUpdatePostById() {
        // given
        Long id = 1L;
        PostDTO postDto = new PostDTO();
        postDto.setTitle("Updated Title");
        postDto.setContent("Updated Content");

        PostDTO resultDto = new PostDTO();
        resultDto.setId(id);
        resultDto.setTitle(postDto.getTitle());
        resultDto.setContent(postDto.getContent());

        given(postService.updatePostById(id, postDto)).willReturn(resultDto);

        // when
        ResponseEntity<PostDTO> response = postController.updatePostById(id, postDto);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(resultDto);
    }
    @Test
    void testDeletePostById() throws ResourceNotFoundException {
        // given
        Long id = 1L;
        doNothing().when(postService).deletePostById(id);

        // when
        ResponseEntity<String> response = postController.deletePostById(id);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isEqualTo("Post deleted Successfully");
    }

}
