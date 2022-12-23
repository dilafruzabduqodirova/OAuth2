package uz.pdp.jwtappauditingemail.payload;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RegisterDto {
    @NotNull
    @Size(min = 5 , max = 50)
    private String firstName; //ismi

    @NotNull
    @Size(min = 5 , max = 50)
    private String lastName; //familiyasi

    @NotNull
    @Email
    private String email; //emaili => username sifatida ketadi .

    @NotNull
    private String password ; //passwordi

}
