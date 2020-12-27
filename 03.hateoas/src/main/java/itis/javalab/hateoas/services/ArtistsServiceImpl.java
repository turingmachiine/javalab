package itis.javalab.hateoas.services;

import itis.javalab.hateoas.models.Artist;
import itis.javalab.hateoas.repositories.ArtistsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArtistsServiceImpl implements ArtistsService {

    @Autowired
    private ArtistsRepository artistsRepository;

    @Override
    public Artist endCareer(Long artistId) {
        Artist artist = artistsRepository.findById(artistId).orElseThrow(IllegalArgumentException::new);
        artist.endCareer();
        artistsRepository.save(artist);
        return artist;
    }
}
