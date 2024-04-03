package com.example.bangbang_gotgot.member.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import java.io.Serializable;

@Embeddable
@Data
public class UserPk implements Serializable {

    private int id;
}
