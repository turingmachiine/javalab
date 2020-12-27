package ru.itis.bvb.mongo.hateoas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import ru.itis.bvb.mongo.hateoas.controllers.TextsController;
import ru.itis.bvb.mongo.hateoas.models.Text;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TextRepresentationProcessor implements RepresentationModelProcessor<EntityModel<Text>> {

    @Override
    public EntityModel<Text> process(EntityModel<Text> model) {
        Text text = model.getContent();
        if(text != null && !text.isImportant()){
            model.add(linkTo(methodOn(TextsController.class).setExpired(text.get_id()))
                    .withRel("setImportant"));
        }
        return model;
    }
}
