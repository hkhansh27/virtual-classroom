package com.virtualclassroom.service.news;

import com.virtualclassroom.model.Classroom;
import com.virtualclassroom.model.News;

import java.util.List;
import java.util.Optional;

public interface NewsService {
    List<News> getAllNews();
    void addNews(News news);

    List<News> getByClassId(Long id);
}
