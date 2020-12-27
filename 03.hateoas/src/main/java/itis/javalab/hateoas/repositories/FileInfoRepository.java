package itis.javalab.hateoas.repositories;

import itis.javalab.hateoas.models.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {
}
