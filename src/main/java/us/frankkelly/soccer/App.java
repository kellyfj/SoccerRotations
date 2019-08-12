/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package us.frankkelly.soccer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {
    
    private static final int GOALIE = 1;
    private static final int ELEVEN_VS_ELEVEN = 11;
    private List<Player> team = new ArrayList<Player>();
    private Player firstHalfGoalie, secondHalfGoalie;
    private int lengthOfGameInMinutes=70, rotationsPerHalf=4, playersPerRotation=3;

    public static void main(String[] args) {
        new App();
    }
    
    public App() {
        try {
            team = loadTeamFromClasspath( "gr78-red.csv");
            loadPreferences();
            calculateRotationForGame(team, firstHalfGoalie, secondHalfGoalie);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    private void loadPreferences() {        
        System.out.println("Enter Player # for Goalie for 1st Half: ");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        firstHalfGoalie = team.get(i-1);
        System.out.println("Goalie for 1st Half: " + firstHalfGoalie.getName());
        
        System.out.println("Enter Player # for Goalie for 2nd Half: ");
        scanner = new Scanner(System.in);
        i = scanner.nextInt();
        secondHalfGoalie = team.get(i-1);
        System.out.println("Goalie for 2nd Half: " + secondHalfGoalie.getName());
        scanner.close();
    }
    
    public void calculateRotationForGame(List<Player> team, Player goalie1, Player goalie2) {
        List<Player> fullTeamCopy = new ArrayList<>(team);
        List<Player> teamRemaining = new ArrayList<>(team);
        
        //First Half
        System.out.println("===============");
        System.out.println("= ROTATIONS   =");
        System.out.println("===============");
        int timePerRotation =  lengthOfGameInMinutes/(rotationsPerHalf*2);
        int gameTime = 0;
        
        System.out.println("1ST HALF");
        System.out.println("ROTATION #1 TIME: " + gameTime + " to " + (gameTime += timePerRotation));
        Rotation433 firstHalfRotation = new Rotation433();
        firstHalfRotation.setGoalie(goalie1);
        fullTeamCopy.remove(goalie1);
        teamRemaining.remove(goalie1);
        
        //Create initial Rotation
        for(int i=0; i< ELEVEN_VS_ELEVEN - GOALIE; i++) {
            Player p = fullTeamCopy.get(i);
            firstHalfRotation.addPlayer(p); 
            teamRemaining.remove(p);
        }
        firstHalfRotation.print();
        printTeam("Available / Resting", teamRemaining);
        printSeparator();
        
        
        for(int i=1; i<= rotationsPerHalf-1; i++) {
            System.out.println("ROTATION #" + (i+1) + " TIME: " + gameTime + " to " + (gameTime += timePerRotation));
            for(int j=0; j< playersPerRotation; j++) {
                Player replaced = firstHalfRotation.replacePlayer(teamRemaining.get(0));
                teamRemaining.remove(0);
                teamRemaining.add(replaced);
            }
            firstHalfRotation.print();
            printTeam("Available / Resting", teamRemaining);
            printSeparator();
        }
        
        firstHalfRotation = null;
        
        //Second Half
        System.out.println("2ND HALF");
        gameTime = lengthOfGameInMinutes/2;
        System.out.println("ROTATION #1 TIME: " + gameTime + " to " + (gameTime += timePerRotation));

        Rotation433 secondHalfRotation = new Rotation433();
        teamRemaining = new ArrayList<>(team);
        secondHalfRotation.setGoalie(goalie2);
        fullTeamCopy.remove(goalie2);
        teamRemaining.remove(goalie2);
        for(int i=0; i< ELEVEN_VS_ELEVEN - GOALIE; i++) {
            Player p = fullTeamCopy.get((fullTeamCopy.size()-1)-i);
            secondHalfRotation.addPlayer(p); 
            teamRemaining.remove(p);
        }
        secondHalfRotation.print();
        printTeam("Available / Resting", teamRemaining);
        printSeparator();
        
        for(int i=1; i<= rotationsPerHalf-1; i++) {
            System.out.println("ROTATION # " + (i+1) + " TIME: " + gameTime + " to " + (gameTime += timePerRotation));
            for(int j=0; j< playersPerRotation; j++) {
                Player replaced = secondHalfRotation.replacePlayer(teamRemaining.get(0));
                teamRemaining.remove(0);
                teamRemaining.add(replaced);
            }
            secondHalfRotation.print();
            printTeam("Available / Resting", teamRemaining);
            printSeparator();
        }
    }
    
    private void printSeparator() {
        System.out.println("-----------------------------------------");
        
    }

    private void printTeam(String s, List<Player> list) {
        System.out.println(s);
        for(Player p : list) {
            System.out.println(" " + p.getName() + " " + p.getScore());
        }
    }
    
    private List<Player> loadTeamFromClasspath(String fileName) throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource(fileName).toURI());

        Stream<String> stream = Files.lines(path);

        List<Player> list = stream.map(Player::getPlayer).collect(Collectors.toList());
        stream.close();
        Collections.sort(list);

        int i = 1;
        for (Player p : list) {
            System.out.println("Player #" + i++ + ") " + p.getName() + " " + p.getScore());
        }
        
        return list;
    }
}
