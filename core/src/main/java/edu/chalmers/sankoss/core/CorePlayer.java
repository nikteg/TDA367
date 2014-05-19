package edu.chalmers.sankoss.core;

/**
 * @author Niklas Tegnander
 * @modified Fredrik Thune
 */
public class CorePlayer {
    private Long playerID = (long) -1;
    private String name = "Unnamed";
    private Nationality nationality = Nationality.USA;
    private boolean myTurn = false;

    public CorePlayer() {

    }

    public CorePlayer(Long playerID) {
        this.playerID = playerID;
        this.name = "Player #" + playerID;
    }

    public CorePlayer(Long playerID, String name) {
        this.name = name;
        this.playerID = playerID;
    }

    public CorePlayer(Long playerID, String name, Nationality nationality) {
        this.playerID = playerID;
        this.name = name;
        this.nationality = nationality;
    }

    public boolean getMyTurn() {
        return myTurn;
    }

    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
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
        USA("USA", "textures/USA.png"),
        GERMANY("GER", "textures/germany.png"),
        JAPAN("JAP", "textures/japan.png"),
        ENGLAND("ENG", "textures/england.png");

        private String landName;
        private java.awt.Color color;
        private String path;

        Nationality(String name, java.awt.Color color) {
            this.landName = name;
            this.color = color;
        }

        Nationality(String name, String path) {
            this.landName = name;
            this.path = path;
        }

        public String getPath() {
            return path;
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

    @Override
    public boolean equals(Object player) {
        if(player != null) {
            if(player instanceof CorePlayer) {
                return (((CorePlayer) player).getID() == this.getID()
                        && ((CorePlayer) player).getName().equals(this.getName()));
            }

        }

        return false;
    }
}
