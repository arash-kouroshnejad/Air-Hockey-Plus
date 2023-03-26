package Control;

import Model.Match;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class IO {
    public static void saveGame(Match game) {
        try {
            FileWriter writer = new FileWriter(new File("./src/main/resources/matches.txt"), true);
            writer.write((game.isGoalLimited() ? "GoalLimit : " + game.getGoalLimit() + " margin : " + game.getMargin() + " " : "") + (game.isTimeLimited() ? "TimeLimit : " + game.getTimeLimit() + " " : "") + "time elapsed : " + game.getTimeElapsed() + " results : " + game.getScore1() + ":" + game.getScore2() + " colors: player 1 : " + game.getPlayer1().toString() + " player 2 : " + game.getPlayer2().toString() + " player 1 name : " + game.getPlayer1Name() + " player 2 name : " + game.getPlayer2Name() + (game.isFinished() ? " finished" : " notFinished") + System.lineSeparator());
            writer.close();
        } catch (Exception e) {
            System.out.println("Error occurred during writing");
        }
    }

    public static ArrayList<Match> getGames() {
        ArrayList<Match> allMatches = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("./src/main/resources/matches.txt"));
            String line = reader.readLine();
            boolean goalLimited = false;
            int goalLimit = 0;
            int margin = 2;
            boolean timeLimited = false;
            int timeLimit = 0;
            long elapsedTime = 0;
            int player1Score = 0;
            int player2Score = 0;
            Color player1Color;
            Color player2Color;
            boolean finished;
            int startIndex = 0;
            while (line != null) {
                String[] parsed = line.split(" ");
                if (parsed[0].equals("GoalLimit")) {
                    goalLimited = true;
                    goalLimit = Integer.parseInt(parsed[2]);
                    margin = Integer.parseInt(parsed[5]);
                    startIndex = 6;
                }
                if (parsed[0].equals("TimeLimit")) {
                    timeLimited = true;
                    timeLimit = Integer.parseInt(parsed[2]);
                    startIndex = 3;
                }
                if (parsed[6].equals("TimeLimit")) {
                    timeLimited = true;
                    timeLimit = Integer.parseInt(parsed[8]);
                    startIndex = 9;
                }
                elapsedTime = Long.parseLong(parsed[startIndex + 3]);
                player1Score = Integer.parseInt(parsed[startIndex + 6].split(":")[0]);
                player2Score = Integer.parseInt(parsed[startIndex + 6].split(":")[1]);
                player1Color = IO.parseColor(parsed[11 + startIndex]);
                player2Color = IO.parseColor(parsed[15 + startIndex]);
                String player1Name = parsed[startIndex + 20];
                String player2Name = parsed[25 + startIndex];
                finished = (parsed[26 + startIndex].equals("finished"));
                Match match = new Match(player1Name, player2Name, goalLimited, goalLimit, margin, timeLimited, timeLimit, player1Color, player2Color);
                match.setScore1(player1Score);
                match.setScore2(player2Score);
                match.setTimeElapsed(elapsedTime);
                if (finished) {
                    match.finished();
                }
                allMatches.add(match);
                line = reader.readLine();
                timeLimited = false;
                goalLimited = false;
            }
        } catch (Exception e) {
            System.out.println("Error occurred during reading");
        }
        return allMatches;
    }

    public static Color parseColor(String input) {
        return switch (input) {
            case "BLACK" -> Color.BLACK;
            case "MAGENTA" -> Color.MAGENTA;
            case "BLUE" -> Color.BLUE;
            case "RED" -> Color.RED;
            case "YELLOW" -> Color.YELLOW;
            case "GREEN" -> Color.GREEN;
            case "CYAN" -> Color.CYAN;
            case "java.awt.Color[r=0,g=0,b=0]" -> Color.BLACK;
            case "java.awt.Color[r=255,g=0,b=255]" -> Color.MAGENTA;
            case "java.awt.Color[r=0,g=0,b=255]" -> Color.BLUE;
            case "java.awt.Color[r=255,g=0,b=0]" -> Color.RED;
            case "java.awt.Color[r=255,g=255,b=0]" -> Color.YELLOW;
            case "java.awt.Color[r=0,g=255,b=0]" -> Color.GREEN;
            case "java.awt.Color[r=0,g=255,b=255]" -> Color.CYAN;
            case "java.awt.Color[r=255,g=255,b=255]" -> Color.WHITE;
            default -> Color.WHITE;
        };
    }
}
