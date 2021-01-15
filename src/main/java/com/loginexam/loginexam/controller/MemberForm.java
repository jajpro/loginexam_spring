package com.loginexam.loginexam.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberForm {
    private String name;
    private String password;

    public MemberForm(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
