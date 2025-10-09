package com.beiming.demo.service.impl;


import com.beiming.demo.dao.SysFileDao;
import com.beiming.demo.model.SysFile;
import com.beiming.demo.model.dto.SysFileDTO;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.beiming.demo.service.SysFileService;

import java.util.List;

/**
 * 系统文件表(SysFile)业务层接口实现类
 *
 * @author makejava
 * @since 2025-10-09 15:36:17
 */
@RequiredArgsConstructor
@Service
public class SysFileServiceImpl implements SysFileService {

    @Resource
    private SysFileDao sysFileDao;

    @Override
    public List<SysFileDTO> list() {
        List<SysFile> list = sysFileDao.list();
        return list.stream()
                .map(sysFile -> {
                    SysFileDTO sysFileDTO = new SysFileDTO();
                    BeanUtils.copyProperties(sysFile, sysFileDTO);
                    return sysFileDTO;
                })
                .toList();
    }

    @Override
    public SysFileDTO getById(Long id) {
        SysFile sysFile = sysFileDao.getById(id);
        if (sysFile == null) {
            return null;
        }
        SysFileDTO sysFileDTO = new SysFileDTO();
        BeanUtils.copyProperties(sysFile, sysFileDTO);
        return sysFileDTO;
    }
}
