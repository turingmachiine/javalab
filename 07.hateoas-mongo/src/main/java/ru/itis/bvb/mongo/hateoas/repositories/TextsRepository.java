package ru.itis.bvb.mongo.hateoas.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import ru.itis.bvb.mongo.hateoas.models.QText;
import ru.itis.bvb.mongo.hateoas.models.Text;

import java.util.List;

public interface TextsRepository extends MongoRepository<Text, String>, QuerydslPredicateExecutor<Text>, QuerydslBinderCustomizer<QText> {
    @Query(value = "{$or: [{'title': { $regex: ?0 }} ,{'text': { $regex: ?0 } }]}")
    List<Text> find(String string);

    @Override
    default void customize(QuerydslBindings querydslBindings, QText qText) {
        querydslBindings.bind(qText.title).first(
                (path, value) -> {
                    return path.containsIgnoreCase(value);
                }
        );
        querydslBindings.bind(qText.text).first(
                (path, value) -> {
                    return path.containsIgnoreCase(value);
                }
        );
    }
}
