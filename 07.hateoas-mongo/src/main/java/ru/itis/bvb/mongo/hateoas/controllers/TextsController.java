package ru.itis.bvb.mongo.hateoas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.itis.bvb.mongo.hateoas.services.TextsService;


@RepositoryRestController
public class TextsController {

    @Autowired
    private TextsService textsService;


    @RequestMapping(value = "/texts/{text-id}/setImportant",method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> setExpired(@PathVariable("text-id") String textId){
        return ResponseEntity.ok(
                EntityModel.of(textsService.setStatus(textId))
        );
    }
}
