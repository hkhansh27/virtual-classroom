package com.virtualclassroom.service.news;

import com.virtualclassroom.model.Classroom;
import com.virtualclassroom.model.News;
import com.virtualclassroom.repository.ClassroomRepository;
import com.virtualclassroom.repository.NewsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NewsServicelmpl implements NewsService {
    private final NewsRepository newsRepository;
    private final ClassroomRepository classroomRepository;
    NewsServicelmpl(NewsRepository newsRepository, ClassroomRepository classroomRepository){
        this.newsRepository = newsRepository;
        this.classroomRepository = classroomRepository;
    }

    @Override
    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    @Override
    public List<News> getNewsByUsername(String userName) {
        return newsRepository.findByUserName(userName);
    }

    @Override
    public void addNews(News news) {
        newsRepository.save(news);
    }

    @Override
    public Optional<News> findByNewsId(Long id) {
        return newsRepository.findById(id);
    }

    public Classroom get(Long id) {
        return classroomRepository.findById(id).get();
    }

}
