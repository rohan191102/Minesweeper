import java.util.Scanner;

public class Main {
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the minesweeper game !");
        System.out.println("Please enter the difficulty : ");
        System.out.println("1 for EASY");
        System.out.println("2 for MEDIUM");
        System.out.println("3 for HARD");
        int num =scanner.nextInt();
        scanner.nextLine();
        difficulty d = null;

        boolean isValid = false;

        // Setting the difficulty
        while(!isValid)
        {
            switch (num)
            {
                case 1 :
                    d = difficulty.easy;
                    isValid = true;
                    break;
                case 2:
                    d = difficulty.medium;
                    isValid = true;
                    break;
                case 3 :
                    d = difficulty.hard;
                    isValid = true;
                    break;
                default:
                    System.out.println("Please enter a valid option.");
            }
        }

        // Creating a game session
        Game game = new Game(d);
        int gameState = 0;
        System.out.println("Welcome in the minseweeper game! E.g R 2 3 to reveal (2,3). Type H to get the full help.");
        System.out.println("You will be prompt to enter a command. E.g R 2 3 to reveal (2,3), or H to get the full help.");

        // Loop while the game is still going.
        while(gameState == 0)
        {
            game.printBoard(false);
            System.out.println("Enter a command :");
            String input = scanner.nextLine();
            System.out.println(input);
            game.getInput(input);
            gameState = game.checkState();
        }
        // The game is over. It is time to determine if it is a win or loose.
        scanner.close();
        if(gameState == 1)
        {
            System.out.println("You WON !!");
        }
        else
        {
            System.out.println("You LOST !!");
        }
        game.printBoard(true);
    }
}