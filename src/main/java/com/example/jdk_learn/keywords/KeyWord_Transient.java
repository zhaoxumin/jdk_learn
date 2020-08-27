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

    
}
