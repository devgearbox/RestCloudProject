package org.example.converte;

import dto.UserDTO;
import org.example.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public UserDTO toDTO(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setRealName(entity.getRealName());
        dto.setPhone(entity.getPhone());
        dto.setRole(entity.getRole());
        dto.setStatus(entity.getStatus());
        dto.setGender(entity.getGender());
        dto.setSignature(entity.getSignature());
        // 注意：不设置password字段！
        return dto;
    }
}