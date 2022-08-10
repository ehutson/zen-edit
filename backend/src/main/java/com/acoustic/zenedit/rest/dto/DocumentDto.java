package com.acoustic.zenedit.rest.dto;

import lombok.Data;

@Data
public class DocumentDto {
    private Long id;
    private String name;
    private String title;
    private String content;
}
