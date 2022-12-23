package com.example.appannotationandcascade.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AddressDto {
     @NotNull
     @Size(min = 3,max = 50)
     private String street ;

     @Size(min = 3,max = 50)
     private String city ;


     private Integer personId ;
}
