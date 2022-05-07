package com.virtualclassroom.service.news;

import com.virtualclassroom.model.News;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NewsService {
    List<News> getAllNews();
    List<News> getNewsById(Long id);
    void addNews(News news);
    News getNewsByNewsId(Long id);
    List<News> getByClassId(Long id);
    Page<News> findPaginated(Long classroomId, int pageId, int pageSize);
}
