package com.gzs.learn.serial.domain;

import java.io.Serializable;

import lombok.ToString;

@ToString
public class IndexNode implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 索引位置
     */
    private int index;

    /**
     * 索引范围
     */
    private int size;

    /**
     * @param index
     * @param size
     */
    public IndexNode(int index, int size) {
        this.index = index;
        this.size = size;
    }

    /**
     * @return
     */
    public int getIndex() {
        this.index = (this.index + 1) % this.size;
        return index;
    }
}
