package com.example.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author
 * @date 2023/4/4 11:08
 * @desc domain
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@JacksonXmlRootElement
public class Student implements Serializable {


    private static final long serialVersionUID = 8196648312242494391L;
    @JacksonXmlProperty
    private String name;

    @JacksonXmlProperty
    private String gender;

}
