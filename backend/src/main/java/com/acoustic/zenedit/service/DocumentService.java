package com.acoustic.zenedit.service;

import java.util.List;
import java.util.Optional;

import com.acoustic.zenedit.entity.Document;
import com.acoustic.zenedit.entity.User;

public interface DocumentService {
    List<Document> getDocumentsForUser(User user);

    Optional<Document> getDocumentById(Long id);

    Document saveDocument(Document document);

    void deleteDocument(Document document);

    Document validateAndGetDocumentById(Long id);
}
