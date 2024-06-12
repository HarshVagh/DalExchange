package com.asdc.dalexchange.dto;
import com.asdc.dalexchange.enums.Role;
import lombok.*;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public class UserDTO {
    private Long userId;
    private String username;
    private String password;
    private String email;
    private String phoneNo;
    private String fullName;
    private String profilePicture;
    private Role role;
    private LocalDateTime joinedAt;
    private String bio;

}

