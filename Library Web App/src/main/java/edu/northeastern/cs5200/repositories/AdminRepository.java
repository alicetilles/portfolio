package edu.northeastern.cs5200.repositories;

import edu.northeastern.cs5200.models.Admin;
import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<Admin, Integer> {
}
