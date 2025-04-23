package com.example.domain;

import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author
 * @date 2023/4/8 15:05
 * @desc OrderConvert
 */
@Mapper
public interface OrderConverter {


    OrderConverter INSTANCE = Mappers.getMapper(OrderConverter.class);

    OrderDTO toDto(OrderDO orderDO);

    @Mapping(source = "userId", target = "id", defaultValue = "10000000")
    @Mapping(target = "createTime",expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updateTime",expression = "java(java.time.LocalDateTime.now())")
    OrderDO toDo(OrderReq.CreateDTO createDTO);
}
