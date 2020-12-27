package itis.javalab.hateoas.services;

import itis.javalab.hateoas.models.Song;

public interface PlaylistsService {
     Song addSong(Long playlistId, Long songId);
}
