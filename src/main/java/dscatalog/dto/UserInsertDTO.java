package dscatalog.dto;

import javax.validation.constraints.Pattern;

public class UserInsertDTO extends UserDTO {
    @Pattern(regexp = "(?=^.{6,10}$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+}{\":;'?/>.<,])(?!.*\\s).*$",
            message = "Password must contain upper case and lower case letters, special character, number and without spaces, 6 to 10 digits")
    private String password;

    public UserInsertDTO() {
        super();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
