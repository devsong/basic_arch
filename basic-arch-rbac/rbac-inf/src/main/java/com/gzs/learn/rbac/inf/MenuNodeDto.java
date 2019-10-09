package com.gzs.learn.rbac.inf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.gzs.learn.rbac.enums.IsMenu;

import lombok.Data;

@Data
public class MenuNodeDto implements Comparable<MenuNodeDto> {

    /**
     * 节点id
     */
    private Long id;

    /**
     * 父节点
     */
    private Long parentId;

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

    public MenuNodeDto(Long id, Long parentId) {
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

    /*
     * 构建整个菜单树
     *
     * @author fengshuonan
     */
    public void buildNodeTree(List<MenuNodeDto> nodeList) {
        for (MenuNodeDto treeNode : nodeList) {
            List<MenuNodeDto> linkedList = treeNode.findChildNodes(nodeList, treeNode.getId());
            if (linkedList.size() > 0) {
                treeNode.setChildren(linkedList);
            }
        }
    }

    /**
    * 查询子节点的集合
    *
    * @author fengshuonan
    */
    public List<MenuNodeDto> findChildNodes(List<MenuNodeDto> nodeList, Long parentId) {
        if (nodeList == null && parentId == null)
            return null;
        for (Iterator<MenuNodeDto> iterator = nodeList.iterator(); iterator.hasNext();) {
            MenuNodeDto node = (MenuNodeDto) iterator.next();
            // 根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (node.getParentId() != 0L && parentId.equals(node.getParentId())) {
                recursionFn(nodeList, node, parentId);
            }
        }
        return linkedList;
    }

    /**
    * 遍历一个节点的子节点
    */
    public void recursionFn(List<MenuNodeDto> nodeList, MenuNodeDto node, Long pId) {
        List<MenuNodeDto> childList = getChildList(nodeList, node);// 得到子节点列表
        if (childList.size() > 0) {
            // 判断是否有子节点
            if (node.getParentId().equals(pId)) {
                linkedList.add(node);
            }
            Iterator<MenuNodeDto> it = childList.iterator();
            while (it.hasNext()) {
                MenuNodeDto n = (MenuNodeDto) it.next();
                recursionFn(nodeList, n, pId);
            }
        } else {
            if (node.getParentId().equals(pId)) {
                linkedList.add(node);
            }
        }
    }

    /**
    * 得到子节点列表
    *
    * @author fengshuonan
    */
    private List<MenuNodeDto> getChildList(List<MenuNodeDto> list, MenuNodeDto node) {
        List<MenuNodeDto> nodeList = new ArrayList<MenuNodeDto>();
        Iterator<MenuNodeDto> it = list.iterator();
        while (it.hasNext()) {
            MenuNodeDto n = it.next();
            if (n.getParentId().equals(node.getId())) {
                nodeList.add(n);
            }
        }
        return nodeList;
    }

    /**
    * 清除掉按钮级别的资源
    *
    * @date 2017年2月19日 下午11:04:11
    */
    public static List<MenuNodeDto> clearBtn(List<MenuNodeDto> nodes) {
        List<MenuNodeDto> noBtns = new ArrayList<>();
        for (MenuNodeDto node : nodes) {
            if (node.getIsmenu() == IsMenu.YES.getCode()) {
                noBtns.add(node);
            }
        }
        return noBtns;
    }

    /**
    * 清除所有二级菜单
    *
    * @date 2017年2月19日 下午11:18:19
    */
    public static List<MenuNodeDto> clearLevelTwo(List<MenuNodeDto> nodes) {
        List<MenuNodeDto> results = new ArrayList<>();
        for (MenuNodeDto node : nodes) {
            Integer levels = node.getLevels();
            if (levels.equals(1)) {
                results.add(node);
            }
        }
        return results;
    }

    /**
    * 构建菜单列表
    *
    * @date 2017年2月19日 下午11:18:19
    */
    public static List<MenuNodeDto> buildTitle(List<MenuNodeDto> nodes) {
        List<MenuNodeDto> clearBtn = clearBtn(nodes);

        new MenuNodeDto().buildNodeTree(clearBtn);

        List<MenuNodeDto> menuNodes = clearLevelTwo(clearBtn);

        // 对菜单排序
        Collections.sort(menuNodes);

        // 对菜单的子菜单进行排序
        for (MenuNodeDto menuNode : menuNodes) {
            if (menuNode.getChildren() != null && menuNode.getChildren().size() > 0) {
                Collections.sort(menuNode.getChildren());
            }
        }

        // 如果关闭了接口文档,则不显示接口文档菜单
        // GunsProperties gunsProperties = SpringContextHolder.getBean(GunsProperties.class);
        // if (!gunsProperties.getSwaggerOpen()) {
        // List<MenuNodeDto> menuNodesCopy = new ArrayList<>();
        // for (MenuNodeDto menuNode : menuNodes) {
        // if (Const.API_MENU_NAME.equals(menuNode.getName())) {
        // continue;
        // } else {
        // menuNodesCopy.add(menuNode);
        // }
        // }
        // menuNodes = menuNodesCopy;
        // }

        return menuNodes;
    }
}
