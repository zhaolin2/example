package com.vertx.cache;


public class FinallyDemo {
    public static void main(String[] args) {


        FinallyDemo finallyDemo = new FinallyDemo();
//         finallyDemo.finallyTestTryNoResult();
//         System.out.println(finallyDemo.finallyTestTryResult());
//        System.out.println(finallyDemo.finallyTestCatchResult());

//         System.out.println(finallyDemo.finallyTestFinallyResult());
    }

    // 1.都没有返回值
    public void finallyTestTryNoResult() {
        try {
            System.out.println("try code block invoked");
            //int i = 1 / 0;
            throw new Exception();
        } catch (Exception e) {
            System.out.println("catch code block invoked");
        } finally {
            System.out.println("finally code block invoked");
        }
    }

    // 2.try有返回值
    public String finallyTestTryResult() {
        try {
            System.out.println("try code block invoked");
            return "no result";
            //throw new Exception();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("catch code block invoked");
        } finally {
            System.out.println("finally code block invoked");
        }
        return "result";

    }

    // 3.catch有返回值
    public String finallyTestCatchResult() {

        try {
            System.out.println("try code block invoked");
            //throw new Exception();
            int i = 1 / 0;
            return "no result";
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("catch code block invoked");
            return "error";
        } finally {
            System.out.println("finally code block invoked");
        }

    }

    // 4.finally有返回值
    public String finallyTestFinallyResult() {

        try {
            System.out.println("try code block invoked");
            //throw new Exception();
            int i = 1 / 0;
            return "no result";
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("catch code block invoked");
            return "error";
        } finally {
            System.out.println("finally code block invoked");
            return "success";
        }

    }


}