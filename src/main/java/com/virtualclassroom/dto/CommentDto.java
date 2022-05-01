package com.virtualclassroom.dto;

import com.virtualclassroom.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@Data
public class CommentDto implements Serializable{
    public Long id;
    public String content;
    public Date timestamp;
    public Set<User> teachers;
    public Set<User> students;
    public int qualityComment;
}
