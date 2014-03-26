package edu.chalmers.sankoss.core.protocol;
/**
 * 
 * @author Niklas Tegnander
 * 
 */
public class CreateRoom {
    private String name;
    private String password;
    
    public CreateRoom() {
        
    }
    
    public CreateRoom(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
