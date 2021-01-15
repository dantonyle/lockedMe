package com.hcl;

import sun.java2d.loops.FillPath;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;


/**
 * Hello world!
 *
 */
public class App 
{

    //final static String FOLDER = "C:\\Users\\danto\\eclipse-workspace\\lockedMe\\src\\LockDirectory";

    static Scanner scanner = new Scanner(System.in);

    public static void main( String[] args )
    {

        showWelcomeScreen();
        showMainMenu();

    }

    private static void collectMainMenuOption() {
            System.out.println("Please choose 1, 2 or 3:");
        String option = scanner.nextLine();
        switch (option) {
            case "1":
                showFilesInAscendingOrder();
                break;
            case "2":
                showFileOperations();
            case "3":
                System.out.println("Thanks for using lockedme.com. Closing application.");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid input provided, please choose 1, 2 or 3.");
        }
        showMainMenu();
    }

    private static void showFileOperations() {
        System.out.println("-------- FILE MANAGER --------");
        System.out.println("1.) Add a file");
        System.out.println("2.) Delete a file");
        System.out.println("3.) Search for a file");
        System.out.println("4.) Back to main menu");
        System.out.println("------------------------------");
        collectFileOperation();
    }

    private static void addAFile() throws InvalidPathException {
        System.out.println("------------ ADD option ------------");
        System.out.println("Please provide a file path:");
        String filePath = scanner.nextLine();
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            System.out.println("File does not exist");
            return;
        }

        String newFileName = path.getFileName().toString();
        String newFilePath = constants.FOLDER + "/" + path.getFileName();

        int inc = 0;
        while (Files.exists(Paths.get(newFilePath))) {
            inc++;
            newFileName = inc + "_" + path.getFileName().toString();
            newFilePath = constants.FOLDER + "/" + inc + "_" + path.getFileName();
        }
        try {
            Files.copy(path, Paths.get(newFilePath));
            System.out.println("File [ " + newFileName + " ] was added to the LockedMe folder");
        } catch(IOException e) {
            System.out.println("Unable to copy file to " + newFilePath);
        }

    }



    private static void deleteFile() throws InvalidPathException {
        System.out.println("------------ DELETE option ------------");
        File[] files = new File(constants.FOLDER).listFiles();
        System.out.println("Current files in LockedMe folder:");
        for(File file: files) {
            System.out.print( file.getName() + " ");
        }

        boolean fileFound = false;
        File deleteFile;

        System.out.println("");
        System.out.println("----------------------------------------------------");
        System.out.println("Please provide a file name from above to delete:");
        String fileName = scanner.nextLine();

        for(File file: files) {
            if (fileName.equals(file.getName())) {
                fileFound = true;
                file.delete();
            }
        }

        if (fileFound) {
            System.out.println( "File was found and deleted. ");
        } else {
            System.out.println("File does not exist");
        }
    }


    private static void collectFileOperation() {
        System.out.println("Please choose 1, 2, 3 or 4:");
        String option = scanner.nextLine();
        switch (option) {
            case "1":
                addAFile();
                break;
            case "2":
                deleteFile();
                break;
            case "3":
                searchFile();
                break;
            case "4":
                showMainMenu();
                break;
            default:
                System.out.println("Invalid input provided, please choose 1, 2, 3 or 4.");
        }
        showFileOperations();
    }

    private static void showFilesInAscendingOrder() {
        System.out.println("---------- LOCKED FILES -----------");
        System.out.println("Showing files in ascending order");
        File[] files = new File(constants.FOLDER).listFiles();

        Set<String> sorted = new TreeSet<>();

        for (File file: files) {
            if (!file.isFile()) {
                continue;
            }
            sorted.add(file.getName());
        }
        sorted.forEach(System.out::println);
        System.out.println("-----------------------------------");
    }

    private static void showMainMenu() {
        System.out.println("------------ MAIN MENU -------------");
        System.out.println("1.) Show files in ascending order");
        System.out.println("2.) Perform file operations");
        System.out.println("3.) Close the application");
        System.out.println("------------------------------------");
        collectMainMenuOption();
    }

    private static void showWelcomeScreen() {
        System.out.println("--------- WELCOME ----------");
        System.out.println("Application: LockedMe.com");
        System.out.println("Developer: Dan Tony Le");
        System.out.println("----------------------------");
    }

    private static void searchFile() {
        System.out.println("--------- SEARCH option ----------");
        File[] files = new File(constants.FOLDER).listFiles();
        String filePath = "";
        boolean fileFound = false;
        boolean specialCase = false;
        String lowerCase = "";
        String exactFileName = "";


        System.out.println("Please provide a file name from above to return file path:");
        String fileName = scanner.nextLine();

        for(File file: files) {
            lowerCase = file.getName().toLowerCase();
            if (fileName.equals(file.getName())) {
                fileFound = true;
                filePath = file.getPath().toString();
            } else if (lowerCase.equals(fileName.toLowerCase())) {
                specialCase = true;
                filePath = file.getPath().toString();
                exactFileName = file.getName();
            }
        }

        if (fileFound) {
            System.out.println( "File was found: file path below: ");
            System.out.println( filePath );
        } else if (specialCase) {
            System.out.println( "Did you mean to find: "+ exactFileName +" - file path below: ");
            System.out.println( filePath );
        } else {
            System.out.println("File does not exist");
        }

    }

}
