package com.vraj.magazinesystem.util;

public class PasswordHashGenerator {
    public static void main(String[] args) {
        String journalistPassword = PasswordHasher.hash("plainPassword");
        String editorPassword = PasswordHasher.hash("editorPassword");

        System.out.println("Journalist Password Hash: " + journalistPassword);
        System.out.println("Editor Password Hash: " + editorPassword);
    }
}

