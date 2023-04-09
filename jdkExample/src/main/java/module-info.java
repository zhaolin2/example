import io.util.provide.EchoListener;
import io.util.spi.test.IEventListener;

module jdkExample {
    requires util;

    uses IEventListener;
    provides IEventListener with EchoListener;
}