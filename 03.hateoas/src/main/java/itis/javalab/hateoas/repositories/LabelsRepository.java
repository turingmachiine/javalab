package itis.javalab.hateoas.repositories;

import itis.javalab.hateoas.models.Label;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelsRepository extends JpaRepository<Label, Long> {
}
