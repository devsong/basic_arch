package com.gzs.learn.web.common.constant;

import static com.gzs.learn.web.common.constant.ResponseCode.SUCCESS;

import com.gzs.learn.web.common.constant.tips.Tip;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CommonResponse<T> extends Tip {
    private T data;

    /**
     * 接口耗时
     */
    private long elapsed;

    /**
     * 后端服务器IP
     */
    private String serverIp;

    public static <T> CommonResponse<T> build(Integer code, String msg, T data) {
        return new CommonResponse<>(code, msg, data);
    }

    public static <T> CommonResponse<T> buildSuccess(T data) {
        return buildSuccess(data, 0L);
    }

    public static <T> CommonResponse<T> buildSuccess(T data, Long count) {
        return new CommonResponse<>(SUCCESS.getCode(), SUCCESS.getMsg(), data);
    }

    public CommonResponse() {
        super();
    }

    public CommonResponse(int code, String msg, T data) {
        super(code, msg);
        this.data = data;
    }
}
