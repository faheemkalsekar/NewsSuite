package com.gadgetmedia.newssuite.util;

import android.annotation.SuppressLint;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class NetworkExecutor {

    private static final long KEEP_ALIVE_TIME = 1L;
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAX_POOL_SIZE = CPU_COUNT * 2 + 1;

    public static ExecutorService newNetworkExecutor() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());

        allowCoreThreadTimeout(executor, true);

        return executor;
    }

    @SuppressLint("NewApi")
    private static void allowCoreThreadTimeout(ThreadPoolExecutor executor, boolean value) {
        executor.allowCoreThreadTimeOut(value);
    }
}