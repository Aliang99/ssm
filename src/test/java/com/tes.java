package com;

import org.apache.log4j.net.JMSSink;

public class tes {
    public static void main(String[] args) {
        String str = "0e8effa2c0f34ef89279349385ffb98f.jpg";
        String[] split = str.split("\\.", -1);
        for (String s : split) {
            System.out.println(s);
        }
    }
}
