//package com.healthcare.medVault.controller;
//
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.Map;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api/profiles")
//@CrossOrigin(origins = "http://localhost:3000")
//public class FileController {
//
//    private static final String UPLOAD_DIR = "/uploads/";
//
//    @PostMapping("/upload")
//    public ResponseEntity<Map<String, String>> uploadDocument(
//            @RequestParam("file") MultipartFile file,
//            @RequestParam("documentType") String documentType) {
//
//        if (file.isEmpty()) {
//            return ResponseEntity.badRequest().body(Map.of("error", "File is empty!"));
//        }
//
//        try {
//            String uploadDir = System.getProperty("user.dir") + UPLOAD_DIR;
//
//            // Ensure directory exists
//            File dir = new File(uploadDir);
//            if (!dir.exists()) {
//                dir.mkdirs();
//            }
//
//            // Generate unique filename
//            String originalFilename = file.getOriginalFilename();
//            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
//            String uniqueFilename = documentType + "_" + UUID.randomUUID() + fileExtension;
//
//            // Save file
//            String filePath = uploadDir + uniqueFilename;
//            file.transferTo(new File(filePath));
//
//            String url = UPLOAD_DIR + uniqueFilename;
//            return ResponseEntity.ok(Map.of("url", url));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.internalServerError()
//                    .body(Map.of("error", "Error uploading file: " + e.getMessage()));
//        }
//    }
//
//    @GetMapping("/document/{filename:.+}")
//    public ResponseEntity<Resource> getDocument(@PathVariable String filename) {
//        try {
//            String uploadDir = System.getProperty("user.dir") + UPLOAD_DIR;
//            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
//            Resource resource = new UrlResource(filePath.toUri());
//
//            if (resource.exists() && resource.isReadable()) {
//                MediaType mediaType = getMediaTypeForFileName(filename);
//
//                return ResponseEntity.ok()
//                        .contentType(mediaType)
//                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
//                        .body(resource);
//            } else {
//                return ResponseEntity.notFound().build();
//            }
//        } catch (MalformedURLException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    private MediaType getMediaTypeForFileName(String fileName) {
//        String extension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
//
//        switch (extension) {
//            case "pdf":
//                return MediaType.APPLICATION_PDF;
//            case "jpg":
//            case "jpeg":
//                return MediaType.IMAGE_JPEG;
//            case "png":
//                return MediaType.IMAGE_PNG;
//            case "gif":
//                return MediaType.IMAGE_GIF;
//            case "txt":
//                return MediaType.TEXT_PLAIN;
//            case "html":
//                return MediaType.TEXT_HTML;
//            case "json":
//                return MediaType.APPLICATION_JSON;
//            case "xml":
//                return MediaType.APPLICATION_XML;
//            case "zip":
//                return MediaType.parseMediaType("application/zip");
//            case "doc":
//            case "docx":
//                return MediaType.parseMediaType("application/msword");
//            case "xls":
//            case "xlsx":
//                return MediaType.parseMediaType("application/vnd.ms-excel");
//            case "ppt":
//            case "pptx":
//                return MediaType.parseMediaType("application/vnd.ms-powerpoint");
//            default:
//                return MediaType.APPLICATION_OCTET_STREAM;
//        }
//    }
//}
package com.healthcare.medVault.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@RestController
@RequestMapping("/api/profiles")
@CrossOrigin(origins = "http://localhost:3000")
public class FileController {

    private static final String UPLOAD_DIR = "/uploads/";

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadDocument(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "File is empty!"));
        }

        try {
            String uploadDir = System.getProperty("user.dir") + "/uploads/";

            // Ensure directory exists
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Save file
            String filePath = uploadDir + file.getOriginalFilename();
            file.transferTo(new File(filePath));

            String url = "/uploads/" + file.getOriginalFilename();
            return ResponseEntity.ok(Map.of("url", url));

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error uploading file: " + e.getMessage()));
        }
    }

    @GetMapping("/document/{filename:.+}")
    public ResponseEntity<Resource> getDocument(@PathVariable String filename) {
        try {
            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                // Determine content type based on file extension
                MediaType mediaType = getMediaTypeForFileName(filename);

                return ResponseEntity.ok()
                        .contentType(mediaType)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Helper method
    private MediaType getMediaTypeForFileName(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();

        switch (extension) {
            case "pdf":
                return MediaType.APPLICATION_PDF;
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG;
            case "png":
                return MediaType.IMAGE_PNG;
            case "gif":
                return MediaType.IMAGE_GIF;
            case "txt":
                return MediaType.TEXT_PLAIN;
            case "html":
                return MediaType.TEXT_HTML;
            case "json":
                return MediaType.APPLICATION_JSON;
            case "xml":
                return MediaType.APPLICATION_XML;
            case "zip":
                return MediaType.parseMediaType("application/zip");
            case "doc":
            case "docx":
                return MediaType.parseMediaType("application/msword");
            case "xls":
            case "xlsx":
                return MediaType.parseMediaType("application/vnd.ms-excel");
            case "ppt":
            case "pptx":
                return MediaType.parseMediaType("application/vnd.ms-powerpoint");
            default:
                return MediaType.APPLICATION_OCTET_STREAM; // fallback for unknown types
        }
    }
}