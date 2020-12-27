package itis.javalab.hateoas.repositories;

import itis.javalab.hateoas.models.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumsRepository extends JpaRepository<Album, Long> {
}
