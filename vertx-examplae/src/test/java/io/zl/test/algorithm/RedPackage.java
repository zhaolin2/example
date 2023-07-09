package io.zl.test.algorithm;

public class RedPackage {


    int remainSize;

    double remainMoney;


    public int getRemainSize() {
        return remainSize;
    }

    public void setRemainSize(int remainSize) {
        this.remainSize = remainSize;
    }

    public double getRemainMoney() {
        return remainMoney;
    }

    public void setRemainMoney(double remainMoney) {
        this.remainMoney = remainMoney;
    }

    @Override
    public String toString() {
        return "RedPackage{" +
                "remainSize=" + remainSize +
                ", remainMoney=" + remainMoney +
                '}';
    }
}
