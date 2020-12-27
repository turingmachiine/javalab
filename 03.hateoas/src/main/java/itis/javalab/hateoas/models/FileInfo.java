package itis.javalab.hateoas.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.File;
import java.net.URI;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class FileInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filePath;
    private String type;
    private String filename;

    @Transient
    private File sourceFile;

    @PostLoad
    public void loadFile() {
        sourceFile = new File("/home/baddie/javalab/hateoas/src/main/resources/" + filePath);
    }
}
