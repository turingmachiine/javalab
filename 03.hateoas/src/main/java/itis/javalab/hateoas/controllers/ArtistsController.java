package itis.javalab.hateoas.controllers;

import itis.javalab.hateoas.services.ArtistsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RepositoryRestController
public class ArtistsController {

    @Autowired
    private ArtistsService artistsService;

    @RequestMapping(value = "/artists/{artist-id}/end", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> endCareer(@PathVariable("artist-id") Long artistId) {
        return ResponseEntity.ok(EntityModel.of(artistsService.endCareer(artistId)));
    }

}
