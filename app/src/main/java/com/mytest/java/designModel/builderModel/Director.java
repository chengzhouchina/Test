package com.mytest.java.designModel.builderModel;

public class Director {

    Builder builder = null;

    public Director(Builder builder) {
        this.builder = builder;
    }

    public Computer createComputer(String cpu,String mainboard,String ram){
        //规范建造流程
        this.builder.buildCpu(cpu);
        this.builder.buildMainboard(mainboard);
        this.builder.buildRam(ram);
        return this.builder.create();
    }
}
