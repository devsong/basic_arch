package com.ruoyi.serial;

import com.ruoyi.serial.common.Result;

public interface IDGen {

    Result get(String key);

    boolean init();
}
