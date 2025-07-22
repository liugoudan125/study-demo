package com.example.extend;

/**
 * Sub
 */
public class Sub extends Base {

    static {
        System.out.println("Sub static block executed");
    }

    {
        System.out.println("Sub instance block executed");
    }

    public Sub() {
        System.out.println("Sub constructor executed");
    }

    public Base getBase() {
        return super.getBase();
    }
}
