package us.frankkelly.soccer;

import java.util.ArrayList;
import java.util.List;

public abstract class Rotation {

    protected List<Player> forwards = new ArrayList<Player>();
    protected List<Player> mids = new ArrayList<Player>();
    protected List<Player> backs = new ArrayList<Player>();
    protected Player goalie;
    
    protected int MAX_FORWARDS = 3;
    protected int MAX_MIDS = 3;
    protected int MAX_BACKS = 4;
    
    public List<String> getPlayerNames() {
        List<String> names = new ArrayList<String>(forwards.size());

        for (Player p : forwards) {
            names.add(p.getName());
        }
        for (Player p : mids) {
            names.add(p.getName());
        }
        for (Player p : backs) {
            names.add(p.getName());
        }
        names.add(goalie.getName());
        return names;
    }
    
    public void addPlayer(Player p) {
        FieldPosition fp = p.getPreferredPositions().get(0);
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
        FieldPosition fp = p.getPreferredPositions().get(0);
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
        for (FieldPosition choice : p.getPreferredPositions()) {
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
    
    public void print(int rotationNumber, int rotationStartTime, int rotationEndTime) {
        System.out.println("ROTATION #" + rotationNumber + " TIME: " + rotationStartTime + " to " + rotationEndTime);
        
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
