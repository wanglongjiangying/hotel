package com.wanglong.pojo;

import java.io.Serializable;

/**
 * @author 王龙
 */
public class Floor implements Serializable {

    private Integer id;
    private String remark;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Floor{" +
                "id=" + id +
                ", remark='" + remark + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
