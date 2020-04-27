package edu.northeastern.cs5200.repositories;

import edu.northeastern.cs5200.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {



}
