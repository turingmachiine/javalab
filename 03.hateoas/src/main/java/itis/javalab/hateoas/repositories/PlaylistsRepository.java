package itis.javalab.hateoas.repositories;

import itis.javalab.hateoas.models.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistsRepository extends JpaRepository<Playlist, Long> {
}
