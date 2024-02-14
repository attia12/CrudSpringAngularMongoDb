package com.example.metaData.repositories;

import com.example.metaData.models.Documentation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface DocuumentationRepository extends MongoRepository<Documentation,String> {
}
