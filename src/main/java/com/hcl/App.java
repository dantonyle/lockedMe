package com.hcl;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;


/**
 * Hello world!
 *
 */
public class App 
{


    //final static String FOLDER = "src/main/resources/LockDirectory";

    static Scanner scanner = new Scanner(System.in);


    public static void main( String[] args )
    {


        fileAccess.showWelcomeScreen();
        fileAccess.showMainMenu();

    }


}
