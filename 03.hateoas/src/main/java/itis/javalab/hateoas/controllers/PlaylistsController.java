package itis.javalab.hateoas.controllers;

import itis.javalab.hateoas.models.Playlist;
import itis.javalab.hateoas.services.PlaylistsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RepositoryRestController
public class PlaylistsController {

    @Autowired
    private PlaylistsService playlistsService;

    @RequestMapping(value = "/playlists/{playlist-id}/add_song/{song-id}")
    @ResponseBody
    public ResponseEntity<?> addSong(@PathVariable("playlist-id") Long playlistId,
                                     @PathVariable("song-id") Long songId) {
        return ResponseEntity.ok(EntityModel.of(playlistsService.addSong(playlistId, songId)));
    }
}
