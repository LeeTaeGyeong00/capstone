package com.example.bangbang_gotgot.member.dto;

import com.example.bangbang_gotgot.member.entity.Role;
import com.example.bangbang_gotgot.member.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberDto {
    private Long id;
    private String personId;
    private String nickName;
    private String passWd;
    private LocalDateTime createdAt;
    private LocalDateTime lastIdChanged;
    private LocalDateTime lastPasswdChanged;
    private Role role;
    private Integer old;
    private String phoneNum;

    // Getters, Setters, Constructors


    public static MemberDto from(User member) {
        MemberDto dto = new MemberDto();
        dto.id = member.getId();
        dto.personId = member.getPerson_id();
        dto.nickName = member.getNick_name();
        dto.passWd = member.getPasswd();
        dto.createdAt = member.getCreated_at();
        dto.lastIdChanged = member.getLast_id_changed();
        dto.lastPasswdChanged = member.getLast_passwd_changed();
        dto.role = member.getRole();
        dto.nickName = member.getNick_name();
        dto.old = member.getOld();
        dto.phoneNum = member.getPhone_num();
        return dto;
    }
}