package com.epam.tm.shop.action;


import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;

public abstract class Action{

    abstract public String execute(HttpRequest req, HttpResponse res);

}
