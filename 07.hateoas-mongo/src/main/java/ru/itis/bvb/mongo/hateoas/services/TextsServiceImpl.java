package ru.itis.bvb.mongo.hateoas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.bvb.mongo.hateoas.models.Text;
import ru.itis.bvb.mongo.hateoas.repositories.TextsRepository;

@Service
public class TextsServiceImpl implements TextsService {


    @Autowired
    private TextsRepository textsRepository;


    @Override
    public Text setStatus(String textId) {
        Text text = textsRepository.findById(textId).orElseThrow(IllegalArgumentException::new);
        text.setImportant(true);
        textsRepository.save(text);
        return text;
    }


}
