package com.pin2.pedrobino.entities.user;

import com.pin2.pedrobino.support.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends BaseRepository<User> {
    User findByEmail(String email);
}