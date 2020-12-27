package itis.javalab.hateoas;

import itis.javalab.hateoas.models.Artist;
import itis.javalab.hateoas.services.ArtistsService;
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

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class EndCareerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArtistsService artistsService;

    @BeforeEach
    public void setUp() {
        when(artistsService.endCareer(1L)).thenReturn(endArtist());
    }

    @Test
    public void endCarrerTest() throws Exception {
        mockMvc.perform(post("/artists/1/end"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isActive").value(endArtist().getIsActive()))
                .andDo(document("end_career", responseFields(
                        fieldWithPath("name").description("Имя артиста"),
                        fieldWithPath("isActive").description("Активен ли артист"),
                        fieldWithPath("lifeName").description("Real name"),
                        fieldWithPath("country").description("Country"),
                        fieldWithPath("city").description("City")
                )));
    }
    private Artist endArtist() {
        return Artist.builder()
                .id(1L)
                .name("Drake")
                .lifeName("Aubrey Graham")
                .city("Toronto")
                .country("Canada")
                .isActive(false)
                .build();
    }

}
