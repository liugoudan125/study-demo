package com.beiming;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller
 */
@RestController
public class Controller {

    @GetMapping("download")
    public ResponseEntity<byte[]> download(@RequestParam(value = "objectName") String objectName) throws IOException {
        String bucketName = "caiyan-voice-test";

        try (InputStream inputStream = OssUtils.getObject(bucketName, objectName)) {
            byte[] bytes = inputStream.readAllBytes();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentDispositionFormData("attachment", objectName);
            httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
        }
    }
}
