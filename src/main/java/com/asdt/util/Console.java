package com.asdt.util;

import java.io.IOException;

public class Console {

    private Console() {
        // empty
    }

    public static void println(Object m) {
        System.out.println(m);
    }

    public static void println() {
        System.out.println();
    }

    public static void pause() {
        // JOptionPane.showMessageDialog(null, "");

        System.out.print("Press any key to continue . . . ");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
