package itis.javalab.hateoas.models;

import lombok.*;

import javax.persistence.*;
import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString(exclude = {"playlists", "artists"})
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Integer length;

    @ManyToMany
    @JoinTable(
            name = "song_artist",
            joinColumns = @JoinColumn(name = "song_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id")
    )
    private List<Artist> artists;

    @ManyToOne()
    private Album album;

    @ManyToMany(mappedBy = "songs")
    private List<Playlist> playlists;

    @OneToOne
    private FileInfo fileInfo;
}
