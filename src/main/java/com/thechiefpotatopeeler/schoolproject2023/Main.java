package com.thechiefpotatopeeler.schoolproject2023;

import com.thechiefpotatopeeler.schoolproject2023.io.gui.UIApplication;

/**
 * The main class of the program
 * */
public class Main {

    /**
     * The main method of the program
     *
     * @param args The arguments passed to the program
     * */
    public static void main(String[] args) {
        //Launches the game in its appropriate version depending on the arguments passed
        switch(args[0].toLowerCase()){
            case "gui":
                UIApplication.launch(UIApplication.class, args);
                break;
            case "cli":
                runCLIApplication();
                break;
            default:
                System.out.println("Please add argument gui or cli");
                break;
        }
    }


    /**
     * The method that runs the CLI version of the game
     * */
    private static void runCLIApplication(){
        System.out.println("CLI is deprecated please use GUI");
    }
}
