package itis.javalab.hateoas;

import itis.javalab.hateoas.models.Artist;
import itis.javalab.hateoas.models.FileInfo;
import itis.javalab.hateoas.models.Playlist;
import itis.javalab.hateoas.models.Song;
import itis.javalab.hateoas.services.PlaylistsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class AddSongTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlaylistsService playlistsService;

    @BeforeEach
    public void setUp() {
        when(playlistsService.addSong(1L, 1L)).thenReturn(makeSong());
    }

    private Song makeSong() {
        return Song.builder().id(1L).title("New Song").length(0)
                .playlists(asList(makePlaylist()))
                .artists(asList(makeArtists()))
                .fileInfo(makeFileInfo())
                .build();
    }

    private FileInfo makeFileInfo() {
        return FileInfo.builder().filename("test.mp3").filePath("test/test")
                .type("mp3").build();
    }

    private Artist makeArtists() {
        return Artist.builder()
                .id(1L)
                .name("Drake")
                .lifeName("Aubrey Graham")
                .city("Toronto")
                .country("Canada")
                .isActive(true)
                .build();
    }

    private Playlist makePlaylist() {
        return Playlist.builder().title("New Playlist").description("descr").id(1L).build();
    }

    @Test
    public void addSongTest() throws Exception {
        mockMvc.perform(get("/playlists/1/add_song/1")).andExpect(status().isOk())
                .andDo(document("add_song", responseFields(
                        fieldWithPath("title").description("Title"),
                        fieldWithPath("length").description("Length")
                )));
    }
}
