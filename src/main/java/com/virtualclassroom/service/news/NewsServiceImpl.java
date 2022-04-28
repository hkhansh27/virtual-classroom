package com.virtualclassroom.service.news;

import com.virtualclassroom.model.News;
import com.virtualclassroom.repository.NewsRepository;
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
    public List<News> getByClassId(Long id) {
        return newsRepository.findByClassId(id);
    }

}
