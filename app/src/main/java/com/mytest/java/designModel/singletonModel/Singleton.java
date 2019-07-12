package com.mytest.java.designModel.singletonModel;

public class Singleton {
    /**
     * 饿汉模式
     * 这种方式在类加载时就完成了初始化，所以类加载较慢，但获取对象的速度块。这种方式基于类加载机制，
     * 避免了多线程的同步问题。在类加载的时候就完成实例化，没有达到懒加载的效果。如果从始至终未使用过
     * 这个实例，则会造成内存的浪费
     */
//    private static Singleton instance = new Singleton();
//
//    public Singleton() {
//    }
//
//    public static Singleton getInstance(){
//        return instance;
//    }

    /**
     * 懒汉模式(线程不安全)
     * 懒汉模式声明了一个静态对象，在用户第一次调用时初始化。这虽然节约了资源，但第一次加载时需要实例
     * 化，反应稍慢一些，而且在多线程中不能正常工作。
      */
//    private static Singleton instance;
//
//    public Singleton() {
//    }

//    public static Singleton getInstance(){
//        if (instance == null){
//            instance = new Singleton();
//        }
//        return instance;
//    }

    /**
     * 懒汉模式（线程安全）
     * 这种写法能够在多线程中很好地工作，但是每次调用getInstance方法时都需要进行同步。这会造成不必
     * 要的同步开销，而且大部分时候我们是用不到同步的。所以，不建议用这种模式。
     * @return
     */
//    public static synchronized Singleton getInstance(){
//        if (instance == null){
//            instance = new Singleton();
//        }
//        return instance;
//    }

    /**
     * 双重检查模式(DCL)
     *这种写法在getSingleton方法中对Singleton进行了两次判空：第一次是为了不必要的同步，第二次是在
     * Singleton等于null的情况下才创建实例。在这里使用volatile会或多或少地影响性能，但考虑到程序的正确
     * 性，牺牲这点性能还是值得的。DCL的优点是资源利用率高。第一次执行getInstance时单例对象才被实例
     * 化，效率高。其缺点是第一次加载时反应稍慢一些，在高并发环境下也有一定的缺陷。DCL虽然在一定程
     * 度上解决了资源的消耗和多余的同步、线程安全等问题，但其还是在某些情况会出现失效的问题，也就是
     * DCL失效。
     */
//    private volatile static Singleton instance;
//
//    public Singleton() {
//    }
//
//    public static Singleton getInstance(){
//        if (instance == null){
//            synchronized (Singleton.class){
//                if (instance == null){
//                    instance = new Singleton();
//                }
//            }
//        }
//        return instance;
//    }

    /**
     * 静态内部类单例模式
     * 第一次加载Singleton类时并不会初始化sInstance，只有第一次调用getInstance方法时虚拟机加载
     * SingletonHolder 并初始化 sInstance。这样不仅能确保线程安全，也能保证 Singleton 类的唯一性。所以，推
     * 荐使用静态内部类单例模式。
     */
    public Singleton() {
    }

    public static Singleton getInstance(){
        return SingletonHolder.sInstance;
    }

    private static class SingletonHolder{
        private static final Singleton sInstance = new Singleton();
    }
}
