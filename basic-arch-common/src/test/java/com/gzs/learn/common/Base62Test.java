package com.gzs.learn.common;

import org.junit.Test;

import com.gzs.learn.common.util.Base62;

public class Base62Test {

    @Test
    public void testEncode() {
        long id = 12121221;
        System.out.println(Base62.fromBase10(id));
    }

    @Test
    public void testDecode() {
        String code = "Y1rJ";
        System.out.println(Base62.toBase10(code));
    }
}
