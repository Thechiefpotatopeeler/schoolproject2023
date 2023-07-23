package com.thechiefpotatopeeler.schoolproject2023;

import com.thechiefpotatopeeler.schoolproject2023.io.gui.UIApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Thomas Jackson
 *
 * The main class of the program
 * Most of the actual code is at {@link com.thechiefpotatopeeler.schoolproject2023.io.gui.UIApplication} and {@link com.thechiefpotatopeeler.schoolproject2023.board.BoardHandler}
 * */
public class Main {

    public static Logger logger = LogManager.getLogger("Game of life");

    public static final String INCORRECT_ARGUMENT_RESPONSE = "Please append with argument gui or cli";

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
                default -> System.out.println(INCORRECT_ARGUMENT_RESPONSE);
            }
        } catch(ArrayIndexOutOfBoundsException e){
            System.out.println(INCORRECT_ARGUMENT_RESPONSE);
        }
    }


    /**
     * The method that runs the CLI version of the game
     * */
    private static void runCLIApplication(){
        System.out.println("CLI is deprecated please use GUI");
    }
}
