package com.spring_upload_ocr.service;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;

@Service
public class OcrService {

    public String extractTextFromImage(byte[] imageBytes) {
        try {
            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath("src/main/resources/tessdata");
            tesseract.setLanguage("fas");
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
            return tesseract.doOCR(image);
        } catch (TesseractException | java.io.IOException e) {
            e.printStackTrace();
            return "خطا در خواندن متن";
        }
    }
}