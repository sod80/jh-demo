package com.sod.geeting.repository;

import com.sod.geeting.domain.Greeting;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Greeting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GreetingRepository extends MongoRepository<Greeting, String> {

}
