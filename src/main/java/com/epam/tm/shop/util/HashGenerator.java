package com.epam.tm.shop.util;

public interface HashGenerator {
    String generateHashByString(String inputParameter) throws HashGeneratorException;

    boolean isHashAndParameterEquals(String hash, String inputParameter) throws HashGeneratorException;
}
