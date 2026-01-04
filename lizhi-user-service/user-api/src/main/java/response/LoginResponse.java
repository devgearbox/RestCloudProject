package response;

import dto.UserDTO;
import lombok.Data;

@Data
public class LoginResponse {
    private Boolean success;
    private String token;
    private UserDTO user;
    private Integer role;
    private String message;

    public static LoginResponse success(String token, UserDTO user) {
        LoginResponse response = new LoginResponse();
        response.setSuccess(true);
        response.setToken(token);
        response.setUser(user);
        response.setRole(user.getRole());
        response.setMessage("登录成功");
        return response;
    }

    public static LoginResponse error(String message) {
        LoginResponse response = new LoginResponse();
        response.setSuccess(false);
        response.setMessage(message);
        return response;
    }
}