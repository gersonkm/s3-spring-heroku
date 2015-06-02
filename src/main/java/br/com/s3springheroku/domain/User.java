package br.com.s3springheroku.domain;

public class User {

    private Email email;
    private String name;
    private String password;

    public User() {
    }

    public User(Email email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email.getAddress();
    }

    public String getEncodedEmail() {
        return email.getEncodedAddress();
    }

    public String getPassword() {
        return password;
    }
}
