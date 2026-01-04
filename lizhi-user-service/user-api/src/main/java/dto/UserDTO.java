// user-api/src/main/java/com/lizhi/user/api/dto/UserDTO.java
package dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String realName;
    private String phone;
    private Integer role;
    private Integer status;
    private String gender;
    private String signature;
    // 注意：不包含password字段！
}