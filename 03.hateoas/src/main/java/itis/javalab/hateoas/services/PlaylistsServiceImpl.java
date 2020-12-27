package itis.javalab.hateoas.services;

import itis.javalab.hateoas.models.Playlist;
import itis.javalab.hateoas.models.Song;
import itis.javalab.hateoas.repositories.PlaylistsRepository;
import itis.javalab.hateoas.repositories.SongsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaylistsServiceImpl implements PlaylistsService {

    @Autowired
    private PlaylistsRepository playlistsRepository;

    @Autowired
    private SongsRepository songsRepository;

    @Override
    public Song addSong(Long playlistId, Long songId) {
        Song song = songsRepository.findById(songId).orElseThrow(IllegalArgumentException::new);
        Playlist playlist = playlistsRepository.findById(playlistId).orElseThrow(IllegalArgumentException::new);
        if (!playlist.getSongs().contains(song)) {
            playlist.getSongs().add(song);
            playlistsRepository.save(playlist);
        }
        return song;
    }
}
