package us.frankkelly.soccer;

import java.util.ArrayList;
import java.util.List;

public class Player implements Comparable<Player>, Cloneable{

    private String name;
    private Double score;
    private List<FieldPosition> preferredPositions = new ArrayList<FieldPosition>();
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getScore() {
        return score;
    }
    public void setScore(Double score) {
        this.score = score;
    }
    public List<FieldPosition> getPreferredPositions() {
        return preferredPositions;
    }
    public void addPreferredPosition(FieldPosition p) {
        preferredPositions.add(p);
    }
    
    public static Player getPlayer(String s) {
        System.out.println("Creating player from --> " + s);
        String[] split = s.split(",");
        
        int numFieldPositions = split.length - 2;
        
        Player p = new Player();
        p.setName(split[0].trim());
        p.setScore(new Double(split[1].trim()));
        
        for(int i=0; i < numFieldPositions; i++) {
            p.addPreferredPosition(FieldPosition.valueOf(split[2+i].trim()));
        }

        return p;
    }
   
    @Override
    public int compareTo(Player p) {
        if (score > p.score)
            return -1;
        else if (score < p.score)
            return 1;
        return 0;
    }
}
