package itis.javalab.hateoas.config;

import itis.javalab.hateoas.controllers.SongsController;
import itis.javalab.hateoas.models.Song;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SongsRepresentationProcessor implements RepresentationModelProcessor<EntityModel<Song>> {
    @Override
    public EntityModel<Song> process(EntityModel<Song> model) {
        Song song = model.getContent();
        if (song != null && !song.getFileInfo().getFilename().split("\\.")[1].equals("mp3")) {
            model.add(linkTo(methodOn(SongsController.class).convertSongToMp3(song.getId())).withRel("convertToMP3"));
        }
        return model;
    }
}
