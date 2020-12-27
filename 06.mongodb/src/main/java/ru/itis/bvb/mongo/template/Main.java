package ru.itis.bvb.mongo.template;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import ru.itis.bvb.mongo.template.config.SimpleMongoConfig;
import ru.itis.bvb.mongo.template.models.Text;
import ru.itis.bvb.mongo.template.models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SimpleMongoConfig.class);
        MongoTemplate template = applicationContext.getBean(MongoTemplate.class);
        //save query
        User user = User.builder()
                .name("javagenerator123")
                .age(25)
                .hobby(new ArrayList<>())
                .build();
        template.save(user, "users");
        //find query
        List<Text> textList = template.find(
                new Query(where("title").regex("\\.*n\\.*")),
                Text.class,"texts");
        for (Text var: textList) {
            System.out.println(var.getTitle());
        }
    }
}
