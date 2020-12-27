package ru.itis.bvb.mongo.hateoas.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.itis.bvb.mongo.hateoas.models.User;

public interface UsersRepository extends PagingAndSortingRepository<User, String> {
    User findByName(String name);
}
