package com.example.bangbang_gotgot.member.dto;

import com.example.bangbang_gotgot.member.entity.Role;
import com.example.bangbang_gotgot.member.entity.User;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
public class MemberDto {
    private Long id;
    private String person_id;
    private String nick_name;
    private String passwd;
    private LocalDateTime created_at;
    private LocalDateTime last_id_changed;
    private LocalDateTime last_passwd_changed;
    private Role role;
    private Integer old;
    private String phone_num;

    // Getters, Setters, Constructors


    public static MemberDto from(User member) {
        MemberDto dto = new MemberDto();
        dto.id = member.getId();
        dto.person_id = member.getPerson_id();
        dto.nick_name = member.getNick_name();
        dto.passwd = member.getPasswd();
        dto.created_at = member.getCreated_at();
        dto.last_id_changed = member.getLast_id_changed();
        dto.last_passwd_changed = member.getLast_passwd_changed();
        dto.role = member.getRole();
        dto.old = member.getOld();
        dto.phone_num = member.getPhone_num();
        return dto;
    }
}