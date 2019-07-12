package com.mytest.java.genericityAndreflect;

import java.util.ArrayList;
import java.util.List;

/**
 * 泛型接口定义及使用
 * <p>
 */
public class Genericity {

    public static void main(String[] args) {
//        Info<String> i = new InfoImpl<>("hello");
//        Info<Integer> i3 = new InfoImpl<>(123);
//        System.out.print(i.getVar());
//        System.out.print(i3.getVar());
//        Info<String> i2 = new InfoImpl2("hello2");
//        System.out.print(i2.getVar());

//        StringCompare result = min(new StringCompare("1234"), new StringCompare("234"), new StringCompare("56789"));
//        System.out.print(result.str);
//        System.out.print(getFruitName(new Banana()));
//        System.out.print(getFruitName(new Apple()));


        Point<Integer> integerPoint = new Point<Integer>(3,3);
        Point<Float> floatPoint = new Point<Float>(4.3f,4.3f);
        Point<Double> doublePoint = new Point<Double>(4.3d,4.90d);
        Point<Long> longPoint = new Point<Long>(12l,23l);

        //无边界通配符：？
        /**
         *  构造泛型实例时，如果省略了填充类型，则默认填充为无边界通配符！
         */

        //如果说 <? extends XXX>指填充为派生于XXX的任意子类的话，那么<? super XXX>则表示填充为任意XXX的父类！
        Point<?> point;
        point = new Point<Integer>(3,3);
//        point = new Point<Float>(4.3f,4.3f);
//        point = new Point<Double>(4.3d,4.90d);
//        point = new Point<Long>(12l,23l);


        //通配符？的extends绑定
        //能取不能存
        //这里虽然是指派生自Number的任意类型，但大家注意到了没： new Point<Number>();也是可以成功赋值的，这说明包括边界自身。
        //再重复一遍：无边界通配符只是泛型T的填充方式，给他加上限定，只是限定了赋值给它（比如这里的point）的实例类型。
        //如果想从根本上解决乱填充Point的问题，需要从Point泛型类定义时加上<T extends Number>:
//        Point<? extends Number> point1;
//        point1 = new Point<Integer>(3,3);
//        point1 = new Point<Float>(4.3f,4.3f);
//        point1 = new Point<Double>(4.3d,4.90d);
//        point1 = new Point<Long>(12l,23l);
//        point1 = new Point<Object>();

        //它表示的意思是将泛型T填充为<? super Manager>，即任意Manager的父类；
        // 也就是说任意将List<T>中的泛型变量T填充为Manager父类(包括边界的)的List变量，都可以赋值给list;
        //能存不能取
        List<? super Manager> list;
        list = new ArrayList<Employee>();
        list = new ArrayList<Manager>();
//        list = new ArrayList<CEO>();
    }

    /**
     * 类型绑定：extends
     *
     * @param a
     * @param <T>
     * @return
     */
    public static <T extends Comparable> T min(T... a) {
        T smallest = a[0];
        for (T item : a) {
            if (smallest.compareTo(item)) {
                smallest = item;
            }
        }
        return smallest;
    }

    public static <T extends Fruit> String getFruitName(T t) {
        return t.getName();
    }
}

interface Info<T> {
    T getVar();

    void setVar(T var);
}

/**
 * 泛型类
 *
 * @param <T>
 */
class InfoImpl<T> implements Info<T> {
    private T var;

    InfoImpl(T var) {
        this.setVar(var);
    }

    @Override
    public T getVar() {
        return this.var;
    }

    @Override
    public void setVar(T var) {
        this.var = var;
    }
}

/**
 * 非泛型类
 */
class InfoImpl2 implements Info<String> {

    private String var;

    InfoImpl2(String var) {
        this.setVar(var);
    }

    @Override
    public String getVar() {
        return this.var;
    }

    @Override
    public void setVar(String var) {
        this.var = var;
    }
}

interface Comparable<T> {
    boolean compareTo(T t);
}

class StringCompare implements Comparable<StringCompare> {

    public String str;

    public StringCompare(String str) {
        this.str = str;
    }

    @Override
    public boolean compareTo(StringCompare stringCompare) {
        if (this.str.length() > stringCompare.str.length()) {
            return true;
        }
        return false;
    }
}

class Fruit {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class Banana extends Fruit {
    public Banana() {
        setName("banana");
    }
}

class Apple extends Fruit {
    public Apple() {
        setName("apple");
    }
}


class Point<T> {
    private T x;
    private T y;

    public Point() {

    }

    public Point(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public void setX(T x) {
        this.x = x;
    }

    public void setY(T y) {
        this.y = y;
    }

    public T getX() {
        return this.x;
    }

    public T getY() {
        return this.y;
    }
}


class CEO extends Manager {
}

class Manager extends Employee {
}

class Employee {
}

