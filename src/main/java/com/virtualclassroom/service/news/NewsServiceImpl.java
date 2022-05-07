package com.virtualclassroom.service.news;

import com.virtualclassroom.model.News;
import com.virtualclassroom.repository.NewsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;

    public NewsServiceImpl(NewsRepository newsRepository){
        this.newsRepository = newsRepository;
    }

    @Override
    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    @Override
    public List<News> getNewsById(Long id) {
        return newsRepository.findByNewsId(id);
    }

    @Override
    public void addNews(News news) {
        newsRepository.save(news);
    }

    @Override
    public News getNewsByNewsId(Long id) {
        return newsRepository.getById(id);
    }

    @Override
    public List<News> getByClassId(Long id) {
        return newsRepository.findByClassId(id);
    }

    @Override
    public Page<News> findPaginated(Long classroomId, int pageId, int pageSize) {
        Pageable pageable = PageRequest.of(pageId - 1, pageSize);
        return this.newsRepository.findByClassroomIdPagination(classroomId, pageable);
    }

    @Override
    public void deleteNews(News news) {
        newsRepository.deleteById(news.getId());
    }

}
