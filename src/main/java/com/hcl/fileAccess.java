package com.hcl;

import com.hcl.constants;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

public class fileAccess {

    static Scanner scanner = new Scanner(System.in);

    // displays Welcome Screen
    static void showWelcomeScreen() {
        System.out.println("");
        System.out.println("--------- WELCOME    to    LockedMe.com ------");
        System.out.println("");
        System.out.println("Developer: Dan Tony Le");
        System.out.println("----------------------------------------------");
    }

    // displays Main Menu options
    static void showMainMenu() {
        System.out.println("------------------ MAIN MENU -----------------");
        System.out.println("1.) Show files in ascending order");
        System.out.println("2.) Perform file operations");
        System.out.println("3.) Close the application");
        System.out.println("----------------------------------------------");
        collectMainMenuOption();
    }

    // processes user input for Main Menu options
    private static void collectMainMenuOption() {
            System.out.println("Please choose 1, 2 or 3:");
        String option = scanner.nextLine();
        switch (option) {
            case "1":
                showFilesInAscendingOrder();
                break;
            case "2":
                showFileOperations();
                break;
            case "3":
                System.out.println("Thanks for using LockedMe.com. Closing application.");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid input provided, please choose 1, 2 or 3.");
        }
        showMainMenu();
    }

    // displays all current files in directory in ascending order
    private static void showFilesInAscendingOrder() {

        System.out.println("--------------- Current LockedMe Files -----------------");
        System.out.println("Showing files in ascending order");
        File[] files = new File(constants.FOLDER).listFiles();

        List<String> sorted = new ArrayList<String>();

        for (File file: files) {
            if (!file.isFile()) {
                continue;
            }
            sorted.add(file.getName());
        }

        Collections.sort(sorted);

        sorted.forEach(System.out::println);
        System.out.println("----------------------------------------------");
    }

    // display File Manager options
    private static void showFileOperations() {
        System.out.println("--------------- FILE MANAGER -----------------");
        System.out.println("1.) Add a file");
        System.out.println("2.) Delete a file");
        System.out.println("3.) Search for a file");
        System.out.println("4.) Back to main menu");
        System.out.println("----------------------------------------------");
        collectFileOperation();
    }

    // processes user input for File Manager options
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

    // add a file to the Lockedme directory
    private static void addAFile() throws InvalidPathException {

        System.out.println("--------------- ADD option -------------------");
        System.out.println("Please provide a file path:");
        String filePath = scanner.nextLine();
        Path path = Paths.get(filePath);


        if (!Files.exists(path)) {
            System.out.println("File does not exist using entered file path");
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


        System.out.println("----------------------------------------------");

    }

    // deletes a file from the LockedMe directory
    private static void deleteFile() throws InvalidPathException {

        System.out.println("--------------- DELETE option ----------------");
        File[] files = new File(constants.FOLDER).listFiles();
        System.out.println("Current files in LockedMe folder:");
        for(File file: files) {
            System.out.print( "[ " + file.getName() + " ] ");
        }

        boolean fileFound = false;
        String filePath = "";
        String deleteFileName = "";

        System.out.println("");

        System.out.println("----------------------------------------------");
        System.out.println("Please enter name.filetype from list above to delete from LockedMe folder:");
        String fileName = scanner.nextLine();

        for(File file: files) {
            if (fileName.equals(file.getName())) {
                fileFound = true;

                do {
                    System.out.println("Are you sure you want to delete: " + deleteFileName);
                    System.out.println("Please enter 'yes' to delete or 'no' to cancel : ");
                    fileName = scanner.nextLine();
                } while (!(fileName.equals("yes")) && !(fileName.equals("no")));

                if (fileName.equals("yes")) {
                    file.delete();
                    System.out.println("File deleted");
                } else System.out.println("Delete has been cancelled");

                break;
            }
        }

        if (!fileFound) {
            System.out.println("Incorrect spelling or file was not found");
        }

    }

    // User enters a fileName
    // Search through LockDirectory folder
    // Find file based on name and type given: sample.txt
    // Spelling must be correct
    // Allow upper and lower case but must be spelled correctly
    private static void searchFile() {

        System.out.println("--------------- SEARCH option ----------------");
        File[] files = new File(constants.FOLDER).listFiles();
        List<String> removedFileType = new ArrayList<String>();
        String filePath = null;
        boolean fileFound = false;
        boolean specialCase = false;
        String lowerCase;
        String exactFileName = null;
        String nameOnly = null;
        int dotIndex = 0;

        System.out.println("Current files in LockedMe folder:");
        for(File file: files) {
            System.out.print( "[ " + file.getName() + " ] ");
            /*dotIndex = file.getName().indexOf(".");
            removedFileType.add(file.getName().toString().substring(0,dotIndex));*/
        }

       /* for(String name: removedFileType) {
            System.out.print( "[ " + name + " ] ");
        }*/

        System.out.println("");
        System.out.println("----------------------------------------------");

        System.out.println("Please provide a file name from above to return file path:");
        String fileName = scanner.nextLine();

        for (File file : files) {

            dotIndex = file.getName().indexOf(".");
            nameOnly = file.getName().substring(0, dotIndex).toLowerCase();
            lowerCase = file.getName().toLowerCase();

            //System.out.print( " nameOnly: " + nameOnly );

            if (fileName.equals(file.getName())) {
                fileFound = true;
                filePath = file.getPath();
            } else if (lowerCase.equals(fileName.toLowerCase())) {
                specialCase = true;
                filePath = file.getPath();
                exactFileName = file.getName();
            } else if (nameOnly.equals(fileName.toLowerCase())) {
                specialCase = true;
                filePath = file.getPath();
                exactFileName = file.getName();
            }
        }

        if (fileFound) {
            System.out.println("File was found: file path below: ");
            System.out.println(filePath);
        } else if (specialCase) {
            System.out.println("Did you mean to find: " + exactFileName + " - file path below: ");
            System.out.println(filePath);
        } else {
            System.out.println("File does not exist");
        }


        System.out.println("----------------------------------------------");

    }
}

