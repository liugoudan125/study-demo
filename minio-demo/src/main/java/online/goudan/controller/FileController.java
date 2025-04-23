package online.goudan.controller;

import jakarta.servlet.http.HttpServletResponse;
import online.goudan.utils.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Optional;

/**
 * @author lcl
 * @date 2023/9/4 13:20
 * @desc FileController
 */
@RestController
@RequestMapping("file")
public class FileController {

    @Autowired
    private FileManager fileManager;

    @PostMapping("upload")
    public String upload(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        FileManager.FileResult fileResult = fileManager.putObjectPublic("goudan", multipartFile);
        return fileResult.getOssUrl() + "/" + fileResult.getBucketName() + "/" + fileResult.getFinalFileName();
    }

    @GetMapping("download")
    public void downLoad(@RequestParam("fileId") Long fileId, HttpServletResponse response) throws Exception {
        String bucketName = "goudan";
        String finalFileName = "010101b4169381374453025.png";
        FileManager.FileResult fileResult = fileManager.getObject(bucketName, finalFileName);
        Optional.ofNullable(fileResult.getMediaType())
                .ifPresent(response::setContentType);
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(finalFileName, "utf-8"));
        try (InputStream inputStream = fileResult.getInputStream(); OutputStream outputStream = response.getOutputStream()) {

            byte[] bytes = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
        }
    }
}
