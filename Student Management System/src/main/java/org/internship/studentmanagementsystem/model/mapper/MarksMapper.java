package org.internship.studentmanagementsystem.model.mapper;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import org.internship.studentmanagementsystem.model.dto.MarksInfo;
import org.internship.studentmanagementsystem.model.entity.Marks;
import org.springframework.beans.BeanUtils;

import java.util.Objects;

public class MarksMapper extends BaseMapper<Marks, MarksInfo> {

    @Override
    public MarksInfo convertToDto(Marks entity, Object... args) {

        MarksInfo dto = new MarksInfo();
        if(!Objects.isNull(entity)){
            BeanUtils.copyProperties(entity, dto);
        }
        return dto;
    }

    @Override
    public Marks convertToEntity(MarksInfo dto, Object... args) {

        Marks entity = new Marks();
        if(!Objects.isNull(dto)){
            BeanUtils.copyProperties(dto, entity);
        }
        return entity;
    }
}
