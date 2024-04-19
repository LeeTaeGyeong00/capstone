package com.example.bangbang_gotgot.member.entity;

import com.example.bangbang_gotgot.member.dto.AllUserInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String person_id;

    @Column(nullable = false)
    private String nick_name;

    @Column(nullable = false)
    private String passwd;

    @Column(nullable = false)
    private LocalDateTime created_at;

    @Column(nullable = false)
    private LocalDateTime last_id_changed;

    @Column(nullable = false)
    private LocalDateTime last_passwd_changed;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private int old;

    @Column(nullable = false)
    private String phone_num;


}
