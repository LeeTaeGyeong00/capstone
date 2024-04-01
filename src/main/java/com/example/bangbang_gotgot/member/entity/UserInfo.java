package com.example.bangbang_gotgot.member.entity;


import com.example.bangbang_gotgot.member.dto.AllUserInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

import static com.example.bangbang_gotgot.member.entity.Role.ROLE_USER;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class UserInfo implements Serializable {

    @EmbeddedId
    private UserPk id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private String user_name;

    @Column(nullable = false)
    private int old;

    @Column(nullable = false)
    private String phone_num;

    public static UserInfo toEntity(AllUserInfoDto userAllInfoDto, User user) {
        return new UserInfo(
                user.getId(),
                user,
                ROLE_USER,
                userAllInfoDto.getUser_name(),
                userAllInfoDto.getOld(),
                userAllInfoDto.getPhone_num()
        );
    }
}
