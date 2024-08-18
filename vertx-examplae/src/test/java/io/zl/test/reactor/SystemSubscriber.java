package io.zl.test.reactor;

import com.vertx.co.Runner;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.SignalType;

public class SystemSubscriber extends BaseSubscriber {

    private static final Logger logger = LoggerFactory.getLogger(SystemSubscriber.class);

    @Override
    protected void hookOnSubscribe(Subscription subscription) {
        logger.info("开始订阅");
        super.hookOnSubscribe(subscription);
//        request(1);
    }

    @Override
    protected void hookOnNext(Object value) {
        super.hookOnNext(value);
        logger.info("打印当前处理元素,[value:{}]",value);
//        request(1);
    }

    @Override
    protected void hookOnComplete() {
        super.hookOnComplete();
    }

    @Override
    protected void hookOnError(Throwable throwable) {
        super.hookOnError(throwable);
    }

    @Override
    protected void hookOnCancel() {
        super.hookOnCancel();
    }

    @Override
    protected void hookFinally(SignalType type) {
        super.hookFinally(type);
    }
}
