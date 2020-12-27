package ru.itis.bvb.mongo.hateoas.controllers;

import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.bvb.mongo.hateoas.dto.TextDto;
import ru.itis.bvb.mongo.hateoas.models.Text;
import ru.itis.bvb.mongo.hateoas.repositories.TextsRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class SearchController {
    @Autowired
    private TextsRepository textsRepository;

    @GetMapping("/texts/search")
    public ResponseEntity<List<TextDto>> searchByRequest(@QuerydslPredicate(root = Text.class) Predicate predicate) {
        return ResponseEntity.ok(
                StreamSupport.stream(textsRepository.findAll(predicate).spliterator(), true).map(
                        text ->
                                TextDto.builder()
                        .title(text.getTitle())
                        .text(text.getText())
                        .build()
                ).collect(Collectors.toList())
        );
    }
}
