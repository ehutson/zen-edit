package com.acoustic.zenedit.rest.mapper;

import org.mapstruct.Mapper;
import org.springframework.context.annotation.Configuration;

import com.acoustic.zenedit.entity.Document;
import com.acoustic.zenedit.rest.dto.CreateDocumentRequest;
import com.acoustic.zenedit.rest.dto.DocumentDto;

@Configuration
@Mapper(componentModel = "spring")
public interface DocumentMapper {
    DocumentDto toDocumentDto(Document document);

    Document toDocument(CreateDocumentRequest createDocumentRequest);
}
