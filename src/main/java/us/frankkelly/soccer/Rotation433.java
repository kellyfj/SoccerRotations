package us.frankkelly.soccer;

import java.util.ArrayList;
import java.util.List;

public class Rotation433 {

    private List<Player> forwards = new ArrayList<Player>();
    private List<Player> mids = new ArrayList<Player>();
    private List<Player> backs = new ArrayList<Player>();
    private Player goalie;
    
    private static final int MAX_FORWARDS = 3;
    private static final int MAX_MIDS = 3;
    private static final int MAX_BACKS = 4;
    
    public void addPlayer(Player p) {
        FieldPosition fp = p.getPreferred().get(0);
        //checkBeforeAdding(fp);
        FieldPosition choice = findMatchingPosition(p);
        switch (choice) {
        case FORWARD:
            forwards.add(p);
            break;
        case MID:
            mids.add(p);
            break;
        case BACK:
            backs.add(p);
            break;
        }
    }
    
    public Player replacePlayer(Player p) {
        FieldPosition fp = p.getPreferred().get(0);
        Player replaced = null;
        switch (fp) {
        case FORWARD:
            replaced = forwards.remove(0); forwards.add(p);
            break;
        case MID:
            replaced = mids.remove(0); mids.add(p);
            break;
        case BACK:
            replaced = backs.remove(0); backs.add(p);
            break;
        } 
        
        return replaced;
    }

    public void setGoalie(Player p) {
        goalie=p;
    }
    
    private void checkBeforeAdding(FieldPosition fp) {
        switch(fp) {
            case FORWARD: 
                if(forwards.size() == MAX_FORWARDS) throw new RuntimeException("Already have " + MAX_FORWARDS + " forwards");
                break;
            case MID: 
                if(mids.size() == MAX_MIDS) throw new RuntimeException("Already have " + MAX_MIDS + " mids");
                break;
            case BACK: 
                if(backs.size() == MAX_BACKS) throw new RuntimeException("Already have " + MAX_BACKS + " backs");
                break;
        }  
    }
    
    private FieldPosition findMatchingPosition(Player p) {

        FieldPosition chosenSpot = null;
        for (FieldPosition choice : p.getPreferred()) {
            System.out.println("Trying " + p.getName() + " at " + choice);
            switch (choice) {
            case FORWARD:
                if (forwards.size() != MAX_FORWARDS) {
                    chosenSpot = FieldPosition.FORWARD;
                    break;
                }
            case MID:
                if (mids.size() != MAX_MIDS) {
                    chosenSpot = FieldPosition.MID;
                    break;
                }
            case BACK:
                if (backs.size() != MAX_BACKS) {
                    chosenSpot = FieldPosition.BACK;
                    break;
                }
            default:
                break;
            }
            if (chosenSpot != null) {
                break;
            }
        }

        if(chosenSpot == null) {
            if(backs.size() < MAX_BACKS)
                chosenSpot = FieldPosition.BACK;
            if(forwards.size() < MAX_FORWARDS) 
                chosenSpot = FieldPosition.FORWARD;
            if(mids.size() < MAX_MIDS)
                chosenSpot = FieldPosition.MID;

        }
        System.out.println("Chose " + p.getName() + " at " + chosenSpot);
        return chosenSpot;
    }
    
    public void print() {
        
        System.out.print("FWDS: ");
        for(Player p: forwards) {
            System.out.print(p.getName() + " / ");
        }
        System.out.println("");
        System.out.print("MIDS: ");
        for(Player p: mids) {
            System.out.print(p.getName() + " / ");
        }
        System.out.println("");        
        System.out.print("BACKS: ");
        for(Player p: backs) {
            System.out.print(p.getName() + " / ");
        }       
        System.out.println("");
        System.out.println("GOALIE: " + goalie.getName());
    }
    

}
