package com.example.jdk_learn.keywords;

import lombok.*;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * @author xumin zhao
 * @version 1.0
 * @description User2
 * @date 2020/8/27 15:33
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User2 implements Externalizable {
    private static final long serialVersionUID = 989543898485507283L;

    // private transient Integer age;

    private transient String name;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject( name );
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
    }
}
