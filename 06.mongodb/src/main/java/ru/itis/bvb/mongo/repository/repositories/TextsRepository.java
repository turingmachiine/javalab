package ru.itis.bvb.mongo.repository.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.bvb.mongo.repository.models.Text;

import java.util.List;

public interface TextsRepository extends MongoRepository<Text, String> {
    @Query(value = "{$or: [{'title': { $regex: ?0 }} ,{'text': { $regex: ?0 } }]}")
    List<Text> find(String string);
}
