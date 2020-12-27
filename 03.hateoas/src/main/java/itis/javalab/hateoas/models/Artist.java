package itis.javalab.hateoas.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lifeName;
    private Boolean isActive;

    private String country;
    private String city;

    @ManyToMany(mappedBy = "artists")
    private Set<Song> songs;

    @ManyToMany(mappedBy = "album_artists")
    private Set<Album> albums;

    @ManyToOne
    private Label label;

    public void endCareer() {
        if (isActive) {
            isActive = false;
        } else {
            throw new IllegalStateException();
        }
    }
}
