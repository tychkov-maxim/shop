package com.epam.tm.shop.util;

public class MD5Generator implements HashGenerator {


    private static final String HASH = "MD5";

    @Override
    public String generateHashByString(String inputParameter) throws HashGeneratorException {
        return MD5(inputParameter);
    }

    @Override
    public boolean isHashAndParameterEquals(String hash, String inputParameter) throws HashGeneratorException {
        return hash.equals(MD5(inputParameter));
    }

    private String MD5(String md5) throws HashGeneratorException {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance(HASH);
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (byte anArray : array) {
                sb.append(Integer.toHexString((anArray & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new HashGeneratorException(e);
        }
    }

}
