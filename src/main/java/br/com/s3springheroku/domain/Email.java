package br.com.s3springheroku.domain;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

public class Email {

    private String address;

    public Email(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String getEncodedAddress() {
        return Hashing.md5().hashString(address, Charsets.UTF_8).toString();
    }

    /**
     * Retorna o endereço de e-mail.
     */
    @Override
    public String toString() {
        return address;
    }
}
