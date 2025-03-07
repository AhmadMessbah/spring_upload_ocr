package com.spring_upload_ocr.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class ImageView {
    private String id;
    private String imageBase64;
    private String extractedText;

    public ImageView(String id, String imageBase64, String extractedText) {
        this.id = id;
        this.imageBase64 = imageBase64;
        this.extractedText = extractedText;
    }

}