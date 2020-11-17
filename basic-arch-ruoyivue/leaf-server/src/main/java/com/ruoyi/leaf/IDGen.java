package com.ruoyi.leaf;

import com.ruoyi.leaf.common.Result;

public interface IDGen {
    Long EPOCH = 1288834974657L;

    Result get(String key);
    boolean init();
}
