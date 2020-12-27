package itis.javalab.hateoas.repositories;

import itis.javalab.hateoas.models.Artist;
import itis.javalab.hateoas.models.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@RepositoryRestResource
public interface SongsRepository extends JpaRepository<Song, Long> {

}
