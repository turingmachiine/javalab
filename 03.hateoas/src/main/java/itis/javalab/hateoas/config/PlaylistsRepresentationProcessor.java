package itis.javalab.hateoas.config;

import itis.javalab.hateoas.controllers.PlaylistsController;
import itis.javalab.hateoas.models.Playlist;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PlaylistsRepresentationProcessor implements RepresentationModelProcessor<EntityModel<Playlist>> {

    @Override
    public EntityModel<Playlist> process(EntityModel<Playlist> model) {
        Playlist playlist = model.getContent();
        if (playlist != null) {
            model.add(Link.of(String.format("/playlists/%s/add_song/{song-id}", playlist.getId())).withRel("addSong"));
        }
        return model;
    }
}
