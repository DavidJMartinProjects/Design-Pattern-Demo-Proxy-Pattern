package com.patterns_playground.proxy;

public interface Authentication {
    public Boolean authenticate(String username, String password) throws Exception;
}