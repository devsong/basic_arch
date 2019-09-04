package com.gzs.learn.rbac.inf;

import lombok.Data;
import java.io.Serializable;

@Data
public class Base implements Serializable {
    private static final long serialVersionUID = 1L;

    protected Long id;
}
