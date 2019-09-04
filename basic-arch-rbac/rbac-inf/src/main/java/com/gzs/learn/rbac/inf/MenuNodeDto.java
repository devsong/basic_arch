package com.gzs.learn.rbac.inf;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class MenuNodeDto implements Comparable<MenuNodeDto> {

    /**
     * 节点id
     */
    private Integer id;

    /**
     * 父节点
     */
    private Integer parentId;

    /**
     * 节点名称
     */
    private String name;

    /**
     * 按钮级别
     */
    private Integer levels;

    /**
     * 按钮级别
     */
    private Integer ismenu;

    /**
     * 按钮的排序
     */
    private Integer num;

    /**
     * 节点的url
     */
    private String url;

    /**
     * 节点图标
     */
    private String icon;

    /**
     * 子节点的集合
     */
    private List<MenuNodeDto> children;

    /**
     * 查询子节点时候的临时集合
     */
    private List<MenuNodeDto> linkedList = new ArrayList<MenuNodeDto>();

    public MenuNodeDto() {
        super();
    }

    public MenuNodeDto(Integer id, Integer parentId) {
        super();
        this.id = id;
        this.parentId = parentId;
    }

    @Override
    public int compareTo(MenuNodeDto o) {
        MenuNodeDto menuNodeDto = (MenuNodeDto) o;
        Integer num = menuNodeDto.getNum();
        if (num == null) {
            num = 0;
        }
        return this.num.compareTo(num);
    }
}
