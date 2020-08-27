package com.example.jdk_learn.keywords;

import java.io.*;

/**
 * @author xumin zhao
 * @version 1.0
 * @description KeyWord_Transient
 * @date 2020/8/27 14:29
 **/
public class KeyWord_Transient {

    //其实这个关键字的作用很好理解，就是简单的一句话：将不需要序列化的属性前添加关键字transient，
    //序列化对象的时候，这个属性就不会被序列化。

    public static void main(String[] args) throws Exception, IOException {
        SerializeUser();
        DeSerializeUser();

        SerializeUser2();
        DeSerializeUser2();

        SerializeUser3();
        DeSerializeUser3();
    }

    private static void SerializeUser() throws FileNotFoundException, IOException,
            ClassCastException {
        User user = User.builder()
                .age( 24 ).name( "jdk关键字transient" ).build();
        ObjectOutputStream oos =
                new ObjectOutputStream( new FileOutputStream( "E://template" ) );
        oos.writeObject( user );
        oos.close();
        System.out.println( "添加了transient关键字序列化：age = " + user.getAge() );
    }

    private static void DeSerializeUser() throws IOException, ClassNotFoundException {
        File file = new File( "E://template" );
        ObjectInputStream ois = new ObjectInputStream( new FileInputStream( file ) );
        User newUser = (User) ois.readObject();
        System.out.println( "添加了transient关键字反序列化：age= " + newUser.getAge() );
    }

    //1、transient底层实现原理是什么？
    //
    //java的serialization提供了一个非常棒的存储对象状态的机制，说白了serialization就是把对象的状态存储到硬盘上
    // 去，等需要的时候就可以再把它读出来使用。有些时候像银行卡号这些字段是不希望在网络上传输的，transient的作用就
    // 是把这个字段的生命周期仅存于调用者的内存中而不会写到磁盘里持久化，意思是transient修饰的age字段，
    // 他的生命周期仅仅在内存中，不会被写到磁盘中。






    //2、被transient关键字修饰过得变量真的不能被序列化嘛？
    //
    //想要解决这个问题，首先还要再重提一下对象的序列化方式：
    //
    //Java序列化提供两种方式。
    //
    //一种是实现Serializable接口
    //
    //另一种是实现Exteranlizable接口。 需要重写writeExternal和readExternal方法，它的效率比Serializable高一些，
    // 并且可以决定哪些属性需要序列化（即使是transient修饰的），但是对大量对象，或者重复对象，则效率低。
    //
    //从上面的这两种序列化方式，我想你已经看到了，使用Exteranlizable接口实现序列化时，我们自己指定那些属性是需要序列化的，
    // 即使是transient修饰的。下面就验证一下
    //
    //首先我们定义User2类：这个类是被Externalizable接口修饰的


    //结果基本上验证了我们的猜想，也就是说，实现了Externalizable接口，
    // 哪一个属性被序列化使我们手动去指定的，即使是transient关键字修饰也不起作用。

    private static void SerializeUser2() throws FileNotFoundException, IOException,
            ClassCastException {
        User2 user = User2.builder()
                .name( "jdk关键字transient" ).build();
        ObjectOutputStream oos =
                new ObjectOutputStream( new FileOutputStream( "E://template" ) );
        oos.writeObject( user );
        oos.close();
        System.out.println( "添加了transient关键字序列化：name = " + user.getName() );
    }

    private static void DeSerializeUser2() throws IOException, ClassNotFoundException {
        File file = new File( "E://template" );
        ObjectInputStream ois = new ObjectInputStream( new FileInputStream( file ) );
        User2 newUser = (User2) ois.readObject();
        System.out.println( "添加了transient关键字反序列化：name= " + newUser.getName() );
    }

    //3、静态变量能被序列化吗？没被transient关键字修饰之后呢？
    //
    //这个我可以提前先告诉结果，静态变量是不会被序列化的，即使没有transient关键字修饰。下面去验证一下，然后再解释原因。
    //
    //首先，在User3类中对age属性添加transient关键字和static关键字修饰。

    //结果已经很明显了。现在解释一下，为什么会是这样，其实在前面已经提到过了。因为静态变量在全局区,本来流里面就没有写入静态变量,
    // 我打印静态变量当然会去全局区查找,而我们的序列化是写到磁盘上的，所以JVM查找这个静态变量的值，是从全局区查找的，而不是磁盘上。
    // user.setAge(26);年龄改成26之后，被写到了全局区，其实就是方法区，只不过被所有的线程共享的一块空间。因此可以总结一句话：
    //
    //静态变量不管是不是transient关键字修饰，都不会被序列化

    private static void SerializeUser3() throws FileNotFoundException, IOException,
            ClassCastException {
        User3 user = User3.builder()
                .name( "jdk关键字transient" ).build();
        User3.setAge( 26 );
        ObjectOutputStream oos =
                new ObjectOutputStream( new FileOutputStream( "E://template" ) );
        oos.writeObject( user );
        oos.close();
        System.out.println( "添加了transient关键字序列化：age = " + User3.getAge() );
    }

    private static void DeSerializeUser3() throws IOException, ClassNotFoundException {
        File file = new File( "E://template" );
        ObjectInputStream ois = new ObjectInputStream( new FileInputStream( file ) );
        User3 newUser = (User3) ois.readObject();
        System.out.println( "添加了transient关键字反序列化：age= " + User3.getAge() );
    }

    //三、transient关键字总结
    //
    //java 的transient关键字为我们提供了便利，你只需要实现Serilizable接口，
    // 将不需要序列化的属性前添加关键字transient，序列化对象的时候，这个属性就不会序列化到指定的目的地中
}
