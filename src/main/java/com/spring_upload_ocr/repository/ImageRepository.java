package com.spring_upload_ocr.repository;

import com.spring_upload_ocr.entity.ImageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageRepository extends MongoRepository<ImageEntity, String> {
}