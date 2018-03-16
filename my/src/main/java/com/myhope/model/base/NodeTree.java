package com.myhope.model.base;

import javax.persistence.Entity;
import java.util.List;

/**
 * Created by Administrator on 2017/9/29 0029.
 */

public class NodeTree implements java.io.Serializable  {

    private String id;
    private String pId;
    private String name;
    private Integer money;

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
