package itis.javalab.hateoas.services;

import itis.javalab.hateoas.models.FileInfo;
import itis.javalab.hateoas.models.Song;
import itis.javalab.hateoas.repositories.FileInfoRepository;
import itis.javalab.hateoas.repositories.SongsRepository;
import itis.javalab.hateoas.util.JaveConverter;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class SongServiceImpl implements SongService {

    @Autowired
    private FileInfoRepository fileInfoRepository;
    @Autowired
    private SongsRepository songsRepository;

    @Override
    public Song convertSongToMp3(Long songId) {
        Song song = songsRepository.findById(songId).orElseThrow(IllegalAccessError::new);
        File result = JaveConverter.convertToMp3(song.getFileInfo().getSourceFile());
        String resultFilename = result.getName();
        FileInfo newFileInfo = FileInfo.builder()
                .filePath(result.getPath())
                .filename(resultFilename)
                .type("mp3")
                .build();
        fileInfoRepository.save(newFileInfo);
        song.setFileInfo(newFileInfo);
        songsRepository.save(song);
        return song;
    }
}
