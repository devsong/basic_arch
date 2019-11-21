package com.gzs.learn.rbac;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RbacPageResponseDto<T> implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    // 返回值
    @Builder.Default
    protected int code = 0;
    // 返回描述信息
    protected String msg;
    // 返回对象实体
    protected List<T> data;
    // 分页数据对象
    protected PageResponse page;

    public static class LogPageResponseDtoBuilder<T> {
        // 返回值
        protected int code = 0;
        // 返回描述信息
        protected String msg;
        // 返回对象实体
        protected List<T> data;
        // 分页数据对象
        protected PageResponse page;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PageResponse implements Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        protected Integer page;
        protected Integer pageSize;
        protected Integer total;

        public Integer getTotalPage() {
            return total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        }
    }
}
