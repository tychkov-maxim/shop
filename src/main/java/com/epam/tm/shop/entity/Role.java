package com.epam.tm.shop.entity;

public class Role extends BaseEntity{

    private String name;

    public Role() {
    }

    public Role(String name, int id) {
        this.name = name;
        this.setId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public static Role getAdministratorRole(){
        return new Role("Administrator",2);
    }
    public static Role getUserRole(){
        return new Role("User",1);
    }
    public static Role getAnonymousRole(){
        return new Role("Anonymous",0);
    }

    @Override
    public String toString() {
        return "Role{" +
                "name='" + name + '\'' +
                "} ";
    }
}
