package com.spring_upload_ocr.controller;

import com.spring_upload_ocr.entity.ImageEntity;
import com.spring_upload_ocr.repository.ImageRepository;
import com.spring_upload_ocr.service.OcrService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.spring_upload_ocr.common.ImageView;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ImageController {

    private final ImageRepository imageRepository;

    private final OcrService ocrService;

    public ImageController(ImageRepository imageRepository, OcrService ocrService) {
        this.imageRepository = imageRepository;
        this.ocrService = ocrService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "لطفاً یک عکس آپلود کنید");
        return "index";
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file, Model model) {
        try {
            byte[] imageBytes = file.getBytes();
            String extractedText = ocrService.extractTextFromImage(imageBytes);
            ImageEntity imageEntity = ImageEntity.builder().imageData(imageBytes).extractedText(extractedText).build();
            imageRepository.save(imageEntity);

            model.addAttribute("message", "عکس با موفقیت آپلود شد");
            model.addAttribute("extractedText", extractedText);
        } catch (Exception e) {
            model.addAttribute("message", "خطا در آپلود عکس: " + e.getMessage());
        }
        return "index";
    }

    @GetMapping("/images")
    public String getAllImages(Model model) {
        List<ImageEntity> images = imageRepository.findAll();
        // تبدیل داده باینری به Base64
        List<ImageView> imageViews = images.stream()
                .map(image -> ImageView
                        .builder()
                        .id(image.getId())
                        .imageBase64("data:image/jpeg;base64," + Base64.getEncoder().encodeToString(image.getImageData()))
                        .extractedText(image.getExtractedText())
                        .build()
                )
                .collect(Collectors.toList());
        model.addAttribute("images", imageViews);
        return "images";
    }
}
