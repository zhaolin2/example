//package io.zl.test.juc;
//
//public class TestBean {
//
//    public static void main(String[] args) {
////        initApolloConfig();
////        Config config = ConfigService.getConfig("application"); //apollo 值发生变更添加监听器
////        config.addChangeListener(new ConfigChangeListener() {
////            @Override
////            public void onChange(ConfigChangeEvent configChangeEvent) {
////                ConfigChange threadCoreSize = configChangeEvent.getChange("thread_core_size");
////                ConfigChange threadMaxSize = configChangeEvent.getChange("thread_max_size");
////                ConfigChange queueSize = configChangeEvent.getChange("queue_size");
////                if (threadCoreSize != null) {
////                    threadPoolExecutor.setCorePoolSize(Integer.parseInt(threadCoreSize.getNewValue()));
////                }
////                if (threadMaxSize != null) {
////                    threadPoolExecutor.setMaximumPoolSize(Integer.parseInt(threadMaxSize.getNewValue()));
////                }
////                if (queueSize != null) {
////                    CLinkedBlockingQueue cLinkedBlockingQueue = (CLinkedBlockingQueue) threadPoolExecutor.getQueue();
////                    cLinkedBlockingQueue.setCapacity(Integer.parseInt(queueSize.getNewValue()));
////                }
////            }
////        });
//        System.out.println("threadPoolExecutor init status:");
//        printThreadPoolStatus(); //每1S添加一个任务
//        for (int i = 0; i < 100; i++) {
//            try {
//                Thread.sleep(1000);
//                dynamicThreadPoolAddTask(i);
//                printThreadPoolStatus();
//            } catch (InterruptedException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }
//
//    /*** * 打印当前线程池的状态 */
//    private static void printThreadPoolStatus() {
//        String s = String.format("core_size:%s,thread_current_size:%s;" + "thread_max_size:%s;queue_current_size:%s,total_task_count:%s", threadPoolExecutor.getCorePoolSize(), threadPoolExecutor.getActiveCount(), threadPoolExecutor.getMaximumPoolSize(), threadPoolExecutor.getQueue().size(), threadPoolExecutor.getTaskCount());
//        System.out.println(s);
//    }
//
//    /*** * 给线程池添加任务 * @param i */
//    private static void dynamicThreadPoolAddTask(int i) {
//        threadPoolExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(10000);
//                    System.out.println(i);
//                } catch (InterruptedException ex) {
//                }
//            }
//        });
//    }
//}
