package itis.javalab.hateoas.controllers;

import itis.javalab.hateoas.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RepositoryRestController
public class SongsController {
    @Autowired
    private SongService songService;


    @RequestMapping(value = "/songs/{song-id}/convert", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> convertSongToMp3(@PathVariable("song-id") Long songId) {
        return ResponseEntity.ok(EntityModel.of(songService.convertSongToMp3(songId)));
    }
}
