package ca.csfoy.servicesweb.camarchedoc.api.user;

public class UserCredentialsDto {
    
    public final String emailAdress;
    public final String password;
    
    public UserCredentialsDto(String emailAdress, String password) {
        this.emailAdress = emailAdress;
        this.password = password;
    }

}
