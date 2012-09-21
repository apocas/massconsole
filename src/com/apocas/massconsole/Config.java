/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apocas.massconsole;

import java.io.File;

/**
 *
 * @author pedrodias
 */
public class Config {

    public static String password = null;
    public static String key = System.getProperty("user.home") + File.separator + ".ssh/id_rsa";
    public static int board_size = 2000;
    public static int font_size = 11;
    public static int font_gap = 15;
    public static int font_init_gap = 20;
    public static boolean print_console = false;
}
