package com.gzs.learn.rbac;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.gzs.learn.common.factory.NamedThreadFactory;

public interface IRbacConstant {
    String MODULE_NAME = "basic-arch-rbac-server";

    Executor EXECUTOR = Executors.newFixedThreadPool(20, new NamedThreadFactory(MODULE_NAME));
}
