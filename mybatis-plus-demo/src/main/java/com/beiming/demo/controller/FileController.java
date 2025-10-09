package com.beiming.demo.controller;

import com.beiming.demo.model.dto.SysFileDTO;
import com.beiming.demo.service.SysFileService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * FileController
 */
@RestController
@RequestMapping("file")
public class FileController {

    @Resource
    private SysFileService sysFileService;


    @GetMapping("")
    public List<SysFileDTO> list(){
        return sysFileService.list();
    }

    @GetMapping("/{id}")
    public SysFileDTO getById(@PathVariable Long id){
        return sysFileService.getById(id);
    }

}
