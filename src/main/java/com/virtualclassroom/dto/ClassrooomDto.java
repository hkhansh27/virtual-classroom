package com.virtualclassroom.dto;

import com.virtualclassroom.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Data
public class ClassrooomDto implements Serializable {
    public Long id;
    public String nameClass;
    public String descriptionClass;
    public String codeClass;
    public Set<User> teachers;
    public Set<User> students;
    public int quanityStudent;
}
