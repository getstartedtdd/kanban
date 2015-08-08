package cn.thiki.core.endpoint.entity;

import java.io.Serializable;

public class MybatisOauth2Code implements Serializable {
    private static final long serialVersionUID = -1799776184263988216L;

    private String code;
    private byte[] authentication;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public byte[] getAuthentication() {
        return authentication;
    }

    public void setAuthentication(byte[] authentication) {
        this.authentication = authentication;
    }
}
