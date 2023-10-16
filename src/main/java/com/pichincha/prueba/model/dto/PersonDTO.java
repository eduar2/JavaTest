package com.pichincha.prueba.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
    protected String name;
    protected String gender;
    protected Integer age;
    protected String identification;
    protected String address;
    protected String phone;
}
