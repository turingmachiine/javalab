package ru.itis.bvb.mongo.hateoas.services;

import ru.itis.bvb.mongo.hateoas.models.Text;

public interface TextsService {

    Text setStatus(String textId);
}
