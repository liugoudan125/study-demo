package com.beiming.demo.model.dto;


import lombok.Data;

import java.time.LocalDateTime;
import java.io.Serializable;


/**
 * 系统文件表(SysFile)表实体类
 *
 * @author makejava
 * @since 2025-10-09 15:36:17
 */
@Data
public class SysFileDTO implements Serializable {

    /**
     * 主键ID
     **/
    private Long id;

    /**
     * 文件原始名称
     **/
    private String fileName;

    /**
     * 在oss中的桶名称
     **/
    private String bucket;

    /**
     * 文件在oss中的key
     **/
    private String objectKey;

    /**
     * 文件唯一标识（MD5或SHA256）
     **/
    private String fileDigest;

    /**
     * 文件大小（字节）
     **/
    private Long fileSize;

    /**
     * 文件类型（如 image/jpeg,application/pdf）
     **/
    private String mimeType;

    /**
     * OSS 文件存储地址
     **/
    private String ossUrl;

    /**
     * 上传者用户ID
     **/
    private Long uploadUserId;

    /**
     * 文件上传时间
     **/
    private LocalDateTime uploadAt;

    /**
     * 创建时间
     **/
    private LocalDateTime createdAt;

    /**
     * 最后更新时间
     **/
    private LocalDateTime updatedAt;

}

