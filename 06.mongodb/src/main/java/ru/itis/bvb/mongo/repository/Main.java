package ru.itis.bvb.mongo.repository;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.itis.bvb.mongo.repository.config.RepositoriesConfig;
import ru.itis.bvb.mongo.repository.models.Text;
import ru.itis.bvb.mongo.repository.repositories.TextsRepository;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(RepositoriesConfig.class);
        TextsRepository textsRepository = context.getBean(TextsRepository.class);
        List<Text> texts = textsRepository.find("n");
        for (Text text: texts) {
            System.out.println(text.getTitle());
        }
    }
}
