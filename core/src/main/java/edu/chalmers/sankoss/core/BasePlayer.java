package edu.chalmers.sankoss.core;

/**
 * @author Niklas Tegnander
 * @modified Fredrik Thune
 */
public class BasePlayer {
    private Long playerID = (long) -1;
    private String name = "Unnamed";
    private Nationality nationality = Nationality.USA;

    public BasePlayer() {

    }

    public BasePlayer(Long playerID) {
        this.playerID = playerID;
    }

    public BasePlayer(Long playerID, String name) {
        this.name = name;
        this.playerID = playerID;
    }

    public BasePlayer(Long playerID, String name, Nationality nationality) {
        this.playerID = playerID;
        this.name = name;
        this.nationality = nationality;
    }

    public Long getID() {
        return playerID;
    }

    public String getName() {
        return name;
    }

    public Nationality getNationality() {
        return nationality;
    }

    public enum Nationality {
        USA("USA", java.awt.Color.WHITE),
        GERMANY("GER", java.awt.Color.RED),
        JAPAN("JAP", java.awt.Color.GREEN),
        ENGLAND("ENG", java.awt.Color.BLUE);

        private String landName;
        private java.awt.Color color;

        Nationality(String name, java.awt.Color color) {
            this.landName = name;
            this.color = color;
        }

        public Nationality getNext(){
            return values()[(ordinal() + 1) % values().length];
        }

        public Nationality getLast(){
            return values()[(ordinal() + 3) % values().length];
        }

        public java.awt.Color getColor() {
            return color;
        }

        public void setColor(java.awt.Color color) {
            this.color = color;
        }

        public String getLandName() {
            return landName;
        }

        public void setLandName(String landName) {
            this.landName = landName;
        }
    }

    public void setID(Long playerID) {
        this.playerID = playerID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNationality(Nationality nationality) {
        this.nationality = nationality;
    }
}
