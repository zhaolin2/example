package io.zl.test.nio.selector;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 使用一个selector 来检测多个 channel
 *
 *
 * channel必须处于非阻塞模式 所以无法跟fileChannel一起使用
 */
public class BasicSelectorTest {

    public void basicSelector() throws IOException {
        Selector selector = Selector.open();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);

        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

        selectionKey.isReadable();
        selectionKey.channel();
        selectionKey.selector();

        selectionKey.attach("123");
        selectionKey.attachment();


        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = selectionKeys.iterator();
        while (iterator.hasNext()){
            SelectionKey key = iterator.next();

            if (key.isReadable()){

            }
            iterator.remove();

        }


    }
}
