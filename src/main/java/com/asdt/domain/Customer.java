package com.asdt.domain;

import com.asdt.persistence.PersistentObject;

public class Customer extends PersistentObject {
    private String name;
    private int age;

    public void setName(String n) {
        name = n;
    }

    public void setAge(int a) {
        age = a;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String toString() {
        return "Customer: OID: [" + oid + "] Name: [" + name + "] Age: [" + age + "]";
    }
}
