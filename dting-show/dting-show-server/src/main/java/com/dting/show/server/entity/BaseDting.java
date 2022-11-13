package com.dting.show.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * *************************************************<br/>
 * 基类<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/11/13 8:20
 */
public class BaseDting implements Serializable {
    private static final long serialVersionUID = -4020998282068382839L;


    @TableId(type = IdType.AUTO)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
