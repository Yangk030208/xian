package com.xian.demo.entity;

import java.io.Serializable;

public class ProductType implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer ptype;
    private String ptypename;

    @Override
    public String toString() {
        return "ProductType{" +
                "ptype=" + ptype +
                ", ptypename='" + ptypename + '\'' +
                '}';
    }

    public Integer getPtype() {
        return ptype;
    }

    public void setPtype(Integer ptype) {
        this.ptype = ptype;
    }

    public String getPtypename() {
        return ptypename;
    }

    public void setPtypename(String ptypename) {
        this.ptypename = ptypename;
    }

    public ProductType() {
    }

    public ProductType(Integer ptype, String ptypename) {
        this.ptype = ptype;
        this.ptypename = ptypename;
    }
}
