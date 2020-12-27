package itis.javalab.hateoas.config;

import itis.javalab.hateoas.controllers.ArtistsController;
import itis.javalab.hateoas.models.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ArtistsRepresentationProcessor implements RepresentationModelProcessor<EntityModel<Artist>> {


    @Override
    public EntityModel<Artist> process(EntityModel<Artist> model) {
        Artist artist = model.getContent();
        if (artist != null && artist.getIsActive()) {
            model.add(linkTo(methodOn(ArtistsController.class).endCareer(artist.getId())).withRel("endCareer"));
        }
        return model;

    }
}
