package itis.javalab.hateoas.repositories;

import itis.javalab.hateoas.models.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

public interface ArtistsRepository extends PagingAndSortingRepository<Artist, Long> {
    @RestResource(path = "new", rel="freshBlood")
    @Query("from Artist artist where artist.city = 'Kazan' and artist.isActive = true ")
    Page<Artist> findAllFresh(Pageable pageable);

    @RestResource(path = "byName", rel="name")
    List<Artist> findAllByNameContainsIgnoreCase(String name);
}
