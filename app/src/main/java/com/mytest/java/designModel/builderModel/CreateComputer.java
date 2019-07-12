package com.mytest.java.designModel.builderModel;

/**
 * 建造者模式
 */
public class CreateComputer {

    public static void main(String[] args) {
        Builder builder = new MoonComputerBuilder();
        Director director = new Director(builder);
        //组装计算机
        Computer computer = director.createComputer("i7", "华擎", "三星");
        Computer computer1 = new Director(new MoonComputerBuilder()).createComputer("i7", "华擎", "三星");
    }
}
