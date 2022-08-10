package com.acoustic.zenedit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.acoustic.zenedit.entity.Document;
import com.acoustic.zenedit.entity.User;
import com.acoustic.zenedit.exception.DocumentNotFoundException;
import com.acoustic.zenedit.repository.DocumentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;

    @Override
    public List<Document> getDocumentsForUser(final User user) {
        return documentRepository.findAllByUser(user);
    }

    @Override
    public Optional<Document> getDocumentById(final Long id) {
        return documentRepository.findById(id);
    }

    @Override
    public Document saveDocument(final Document document) {
        return documentRepository.save(document);
    }

    @Override
    public void deleteDocument(final Document document) {
        documentRepository.delete(document);
    }

    @Override
    public Document validateAndGetDocumentById(final Long id) {
        return getDocumentById(id)
                .orElseThrow(() -> new DocumentNotFoundException(String.format("Document %d not found", id)));
    }
}
