package itis.javalab.hateoas.services;

import itis.javalab.hateoas.models.FileInfo;
import itis.javalab.hateoas.models.Song;

import java.io.File;

public interface SongService {
    Song convertSongToMp3(Long songId);
}
