package com.thechiefpotatopeeler.schoolproject2023;

import com.thechiefpotatopeeler.schoolproject2023.board.BoardHandler;
import com.thechiefpotatopeeler.schoolproject2023.display.gui.UIApplication;

/**
 * The main class of the program
 * */
public class Main {

    public static void main(String[] args) {
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


    private static void runCLIApplication(){
    }
}
