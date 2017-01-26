package com.epam.tm.shop.util;

import com.epam.tm.shop.exception.HashGeneratorException;

public interface HashGenerator {
    String generateHashByString(String inputParameter) throws HashGeneratorException;

    boolean isHashAndParameterEquals(String hash, String inputParameter) throws HashGeneratorException;
}
