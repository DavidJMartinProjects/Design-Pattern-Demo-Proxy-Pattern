package com.patterns_playground.proxy;

public interface Authentication {
    public boolean authenticate(String username, String password) throws Exception;
}