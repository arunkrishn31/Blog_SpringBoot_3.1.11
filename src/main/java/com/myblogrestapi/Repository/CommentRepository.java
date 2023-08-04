package com.myblogrestapi.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myblogrestapi.Entity.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

    List<Comment>findByPostId(Long postId);

}
