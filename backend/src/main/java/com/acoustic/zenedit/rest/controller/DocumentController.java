package com.acoustic.zenedit.rest.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acoustic.zenedit.entity.Document;
import com.acoustic.zenedit.entity.User;
import com.acoustic.zenedit.rest.dto.CreateDocumentRequest;
import com.acoustic.zenedit.rest.dto.DocumentDto;
import com.acoustic.zenedit.rest.dto.DocumentUpdateRequest;
import com.acoustic.zenedit.rest.mapper.DocumentMapper;
import com.acoustic.zenedit.service.DocumentService;
import com.acoustic.zenedit.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/documents")
public class DocumentController {

    private final DocumentService documentService;
    private final UserService userService;

    private final DocumentMapper documentMapper;

    @GetMapping("/user/{id}")
    public List<DocumentDto> getDocumentsForUser(@PathVariable Long id) {
        User user = userService.validateAndGetUserById(id);
        return documentService.getDocumentsForUser(user)
                .stream()
                .map(documentMapper::toDocumentDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public DocumentDto getDocument(@PathVariable Long id) {
        return documentMapper.toDocumentDto(documentService.validateAndGetDocumentById(id));
    }

    @PostMapping("/user/{id}")
    public DocumentDto addDocument(@RequestBody CreateDocumentRequest request, @PathVariable Long id) {
        User user = userService.validateAndGetUserById(id);
        Document document = documentMapper.toDocument(request);
        document.setUser(user);
        return documentMapper.toDocumentDto(documentService.saveDocument(document));
    }

    @PutMapping("/{id}")
    public DocumentDto updateDocument(@RequestBody DocumentUpdateRequest documentUpdateRequest, @PathVariable Long id) {
        Document document = documentService.validateAndGetDocumentById(id);
        document.setTitle(documentUpdateRequest.getTitle());
        document.setContent(documentUpdateRequest.getContent());
        return documentMapper.toDocumentDto(documentService.saveDocument(document));
    }

    @DeleteMapping("/{id}")
    public DocumentDto deleteDocument(@PathVariable Long id) {
        Document document = documentService.validateAndGetDocumentById(id);
        documentService.deleteDocument(document);
        return documentMapper.toDocumentDto(document);
    }
}
