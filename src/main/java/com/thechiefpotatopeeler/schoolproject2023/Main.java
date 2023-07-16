package com.thechiefpotatopeeler.schoolproject2023;

import com.thechiefpotatopeeler.schoolproject2023.io.gui.UIApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The main class of the program
 * */
public class Main {

    public static Logger logger = LogManager.getLogger("Game of life");

    /**
     * The main method of the program
     * @param args The arguments passed to the program
     * */
    public static void main(String[] args) {
        //Launches the game in its appropriate version depending on the arguments passed
        try{
            switch (args[0].toLowerCase()) {
                case "gui" -> UIApplication.launch(UIApplication.class, args);
                case "cli" -> runCLIApplication();
                default -> System.out.println("Please add argument gui or cli");
            }
        } catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Please add argument gui or cli");
        }
    }


    /**
     * The method that runs the CLI version of the game
     * */
    private static void runCLIApplication(){
        System.out.println("CLI is deprecated please use GUI");
    }
}
