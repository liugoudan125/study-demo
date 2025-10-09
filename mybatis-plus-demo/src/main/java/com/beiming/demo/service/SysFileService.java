package com.beiming.demo.service;

import com.beiming.demo.model.dto.SysFileDTO;

import java.util.List;

/**
 * 系统文件表(SysFile)业务层接口
 *
 * @author makejava
 * @since 2025-10-09 15:36:17
 */
public interface SysFileService {

    List<SysFileDTO> list();

    SysFileDTO getById(Long id);
}
