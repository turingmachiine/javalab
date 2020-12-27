package itis.javalab.hateoas;

import itis.javalab.hateoas.models.*;
import itis.javalab.hateoas.repositories.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;

@SpringBootApplication
public class HateoasApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(HateoasApplication.class, args);

        LabelsRepository labelsRepository = context.getBean(LabelsRepository.class);
        Label sonyMusic = Label.builder().name("Sony Music").country("Russia").build();
        Label warnerMusic = Label.builder().name("Warner Music").country("Russia").build();
        Label universalMusic = Label.builder().name("Universal Music").country("Russia").build();
        labelsRepository.saveAll(asList(sonyMusic, warnerMusic, universalMusic));

        ArtistsRepository artistsRepository = context.getBean(ArtistsRepository.class);
        Artist baddie = Artist.builder().name("baddie").label(warnerMusic).city("Kazan").isActive(true).build();
        Artist kanye = Artist.builder().name("Kanye West").label(universalMusic).city("Chicago").isActive(true).build();
        artistsRepository.saveAll(asList(baddie, kanye));

        SongsRepository songsRepository = context.getBean(SongsRepository.class);
        FileInfoRepository fileInfoRepository = context.getBean(FileInfoRepository.class);
        AlbumsRepository albumsRepository = context.getBean(AlbumsRepository.class);
        List<Artist> artists = new ArrayList<>();
        artists.add(baddie);
        Song minimum = Song.builder().artists(artists).title("Минимум приколов")
                .fileInfo(fileInfoRepository.save(FileInfo.builder()
                        .filePath("files/Минимум.wav")
                        .filename("Минимум.wav")
                        .type("mp3").build()))
                .album(albumsRepository.save(Album.builder().album_artists(artists)
                        .label(artists.iterator().next().getLabel()).build()))
                .length(0)
                .build();
        songsRepository.save(minimum);
        artists.clear();
        artists.add(kanye);
        Song facts = Song.builder().title("Facts").artists(artists)
                .fileInfo(fileInfoRepository.save(FileInfo.builder()
                        .filePath("files/Kanye West - Facts.mp3")
                        .filename("Kanye West - Facts.mp3")
                        .type("mp3").build()))
                .album(albumsRepository.save(Album.builder().album_artists(artists)
                        .label(artists.iterator().next().getLabel()).build()))
                .length(0)
                .build();
        songsRepository.save(facts);

        PlaylistsRepository repository = context.getBean(PlaylistsRepository.class);
        Playlist playlist = Playlist.builder().title("New playlist").description("new").build();
        repository.save(playlist);
    }

}
