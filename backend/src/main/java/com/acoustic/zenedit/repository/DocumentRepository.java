package com.acoustic.zenedit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.acoustic.zenedit.entity.Document;
import com.acoustic.zenedit.entity.User;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findAllByUser(User user);
}
