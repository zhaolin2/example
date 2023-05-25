package com.vertx.co;

import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

/**
 * Multi thread helper tool to do some multi-thread works.
 */
public final class Runner {

    private static final Logger LOGGER = LoggerFactory.getLogger(Runner.class);

    public static void run(final Runnable hooker,
                           final String name) {
        final Thread thread = new Thread(hooker);
        // Append Thread id
        thread.setName(name + "-" + thread.getId());
        thread.start();
    }

}
