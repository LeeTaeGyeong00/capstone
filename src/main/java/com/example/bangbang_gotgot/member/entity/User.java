package com.example.bangbang_gotgot.member.entity;

import com.example.bangbang_gotgot.member.dto.AllUserInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @EmbeddedId
    private UserPk id;

    @Column(nullable = false)
    private String person_id;

    @Column(nullable = false)
    private String passwd;

    @Column(nullable = false)
    private LocalDateTime created_at;

    @Column(nullable = false)
    private LocalDateTime last_id_changed;

    @Column(nullable = false)
    private LocalDateTime last_passwd_changed;


}
