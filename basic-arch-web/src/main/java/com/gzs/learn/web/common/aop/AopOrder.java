package com.gzs.learn.web.common.aop;

/**
 * <p>
 * aop顺序
 * </p>
 * @author guanzhisong
 *
 */
public interface AopOrder {
    int PARAM_SET_ORDER = 0;

    int SESSION_TIMEOUT_ORDER = 1;

    int SESSION_ORDER = 2;

    int PERMISSION_ORDER = 3;

    int TRANSACTION_ORDER = 5;

    int LOG_ORDER = 6;
}
