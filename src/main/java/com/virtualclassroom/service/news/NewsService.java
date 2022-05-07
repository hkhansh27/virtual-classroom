package com.virtualclassroom.service.news;

import com.virtualclassroom.model.Classroom;
import com.virtualclassroom.model.News;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface NewsService {
    List<News> getAllNews();
    List<News> getNewsById(Long id);
    void addNews(News news);
    News getNewsByNewsId(Long id);
    List<News> getByClassId(Long id);
    Page<News> findPaginated(Long classroomId, int pageId, int pageSize);
    void deleteNews(News news);
}
