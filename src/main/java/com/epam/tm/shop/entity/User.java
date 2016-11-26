package com.epam.tm.shop.entity;

import org.joda.money.Money;

public class User extends BaseEntity{
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private Role role;
    private Money account;
    private String address;


    public User() {
    }

    public User(String login, String password, String firstName, String lastName, Role role, Money account, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.role = role;
        this.account = account;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public Money getAccount() {
        return account;
    }

    public void setAccount(Money account) {
        this.account = account;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", account=" + account +
                ", address='" + address + '\'' +
                "} " + super.toString();
    }
}
