package org.example.auth.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("user")
public class ScsUser {
    @Id
    private Integer id;
    private String username;
    private String password;

}
