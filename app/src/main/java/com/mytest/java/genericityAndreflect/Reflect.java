package com.mytest.java.genericityAndreflect;

import android.util.Log;

import com.mytest.BaseApplication;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 反射
 */
public class Reflect {

    private Class a;

    /**
     * 内部类
     * 在类名，前面的那一坨public static final，就是类的访问修饰符，是定义这个类在的访问区域和访问限定的。
     */
    public static final class InnerClass {

    }

    /**
     * 定义一个类部接口
     */
    public static interface InnerInterface {

    }

    public static void main(String[] args) {
//        try {
//            Class c = Class.forName("com.mytest.java.genericityAndreflect.Manager");
//            Class parentClass = c.getSuperclass();
//            System.out.print("父类 ：" + parentClass.getName());
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

        //获取Animal类的接口列表
        Class class3 = Animal.class;
        Class<?>[] interfaces = class3.getInterfaces();
//        for (Class interItem : interfaces){
//            System.out.print("Animal继承的接口 ：" + interItem.getName());
//        }

        //获取AnimalImpl的接口列表
//        class3 = AnimalImpl.class;
//        interfaces = class3.getInterfaces();
//        if (interfaces.length > 0) {
//            for (Class interItem : interfaces) {
//                System.out.print("AnimalImpl继承的接口 ：" + interItem.getName());
//            }
//        }else {
//            System.out.print("AnimalImpl无继承的接口");
//        }

        //调用
//        Class<?>[] clazzes = Reflect.getAllInterface(AnimalImpl.class);
//        SpannableStringBuilder builder = new SpannableStringBuilder();
//        for (Class clazz : clazzes) {
//            builder.append(clazz.getName());
//            builder.append("  ");
//        }
//        System.out.print("AnimalImpl继承的所有接口:" + builder.toString());

        Class<?> clazz = TestImpl.class;
        //获取泛型超类
        Type type = clazz.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            //返回表示此类型实际类型参数的 Type 对象的数组
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            for (Type parameterArgType : actualTypeArguments) {
                Class parameterArgClass = (Class) parameterArgType;
                System.out.print("填充类型为：" + parameterArgClass.getName());
            }

            //返回 Type 对象，表示声明此类型的类或接口。
            Type type1 = parameterizedType.getRawType();
            Class class2 = (Class) type1;
            System.out.print("TestImpl的父类类型为：" + class2.getName());
        }
    }

    public static Class<?>[] getAllInterface(Class<?> clazz) {
        //获取自身的所有接口
        Class<?>[] interself = clazz.getInterfaces();
        //递规调用getAllInterface获取超类的所有接口
        Class<?> superClazz = clazz.getSuperclass();
        Class<?>[] interParent = null;
        if (null != superClazz) {
            interParent = getAllInterface(superClazz);
        }

        if (interParent == null && interself != null) {
            return interself;
        } else if (interParent == null && interself == null) {
            return null;
        } else if (interParent != null && interself == null) {
            return interParent;
        } else {
            int length = interParent.length + interself.length;
            Class<?>[] result = new Class[length];
            /**
             * 将指定个数的元素从源数组src复制到目标数组dst中
             *
             * @param src ：源数组
             * @param srcPos：源数组开始复制的item的索引，从0开始
             * @param dst：目标数组
             * @param dstPos：目标数组开始接收复制item的位置索引，从0开始
             * @param length：要复制的元素个数
             */
            System.arraycopy(interself, 0, result, 0, interself.length);
            System.arraycopy(interParent, 0, result, interself.length, interParent.length);
            return result;
        }
    }

    /**
     * 获取类类型的方法
     */
    private void getClassName() {
        StaticFans staticFans = new StaticFans();
        //通过类实例的getClass()方法得到类类型。
        a = staticFans.getClass();
        //直接通过类的class对象得到
        Class b = StaticFans.class;
        try {
            //Class.forName(String className)不仅会将类加载进来，而且会对其进行初始化，
            // 而ClassLoader.loadClass(String ClassName)则只是将类加载进来，而没有对类进行初始化。
            // 一般来讲，他们两个是通用的，但如果你加载类依赖初始化值的话，那ClassLoader.loadClass(String ClassName)将不再适用。

            //这里的ClassName一定要从包名具体到类名，唯一定位到一个类才行，不然就会报ClassNotFound错误
            Class c = Class.forName("com.mytest.java.genericityAndreflect.StaticFans");
            //不建议使用
            Class d = BaseApplication.getContext().getClassLoader().loadClass("com.mytest.java.genericityAndreflect.StaticFans");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 基本类类型周边信息获取
     */
    private void getRimClassMsg() {
        //获取完整的类名（包含包名）
        a.getName();
        //仅获取类名
        a.getSimpleName();
        //获取类类型所对应的package对象
        a.getPackage();
        //获取普通函数的父类Class对象
        a.getSuperclass();
        //针对泛型父类而设计
        a.getGenericSuperclass();
        /**
         * 不是直接继承的接口是无法获取的
         */
        ////获取普通接口的方法
        a.getInterfaces();
        //获取泛型接口的方法
        a.getGenericInterfaces();
    }

    /**
     * 获取类的访问修饰符
     *
     * @param clazz
     */
    private void getClassModifier(Class<?> clazz) {
        //通过clazz.getModifiers()得到一个整型变量，由于访问修饰符有很多，所以这些修饰符被打包成
        // 一个int，对应的二进制中，每个修饰符是一个标志位，可以被置位或清零。
        int modifiers = clazz.getModifiers();
        String retval = Modifier.toString(modifiers);
        boolean isFinal = Modifier.isFinal(modifiers);
        Log.e("mytest", "Class的定义修饰符:" + retval);
        Log.e("mytest", "is Final:" + isFinal);
    }

    /**
     * 获取接口的访问修饰符
     */
    private void getInterfaceModifier() {
        Class<?> clazz = InnerInterface.class;
        int modifiers = clazz.getModifiers();
        String retval = Modifier.toString(modifiers);
        boolean isInterface = Modifier.isInterface(modifiers);
        Log.e("mytest", "InnerInterface的定义修饰符:" + retval);
        Log.e("mytest", "is Inteface:" + isInterface);
    }

    /**
     * 获取泛型超类相关信息
     */
    private void getGenericSuperClass() {
        Class<?> clazz = TestImpl.class;
        //获取泛型超类
        Type type = clazz.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            //返回表示此类型实际类型参数的 Type 对象的数组
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            for (Type parameterArgType : actualTypeArguments) {
                Class parameterArgClass = (Class) parameterArgType;
                Log.e("mytest", "填充类型为：" + parameterArgClass.getName());
            }

            //返回 Type 对象，表示声明此类型的类或接口。
            Type type1 = parameterizedType.getRawType();
            Class class2 = (Class) type1;
            Log.e("mytest", "TestImpl的父类类型为：" + class2.getName());
        }
    }

    /**
     * 获取类的构造函数
     */
    private void getConstructorClass(){
        //枚举
        Class cla = Person.class;
        //getDeclaredConstructors()将得到所有构造函数的列表，包括声明为private,protected和public的构造函数。
        Constructor[] constructors = cla.getDeclaredConstructors();
        for (Constructor item : constructors){
            Log.e("mytest","枚举到的构造函数："+item.toString());
        }

        try {
            //这个函数是通过指定构造函数的参数列表来得到指定构造函数的
            //不是模糊匹配而是精确匹配
            //不光派生自Object的类具有Class对象，原始的数据类型也是具有Class对象的。
            Constructor constructor = cla.getDeclaredConstructor(int.class,String.class);
            Log.e("mytest","指定参数得到的构造函数："+constructor.toString());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}

interface IAnimal {
    void setName(String name);

    String getNanme();
}

class Animal implements IAnimal, Serializable {

    private String name;

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getNanme() {
        return this.name;
    }
}

class Test<T> {

}

class TestImpl extends Test<Integer> {

}



