package com.acoustic.zenedit.rest.dto;

import lombok.Data;

@Data
public class CreateDocumentRequest {
    private String name;
    private String title;
    private String content;
}
