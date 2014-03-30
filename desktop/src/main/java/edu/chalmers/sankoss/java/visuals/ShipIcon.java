package edu.chalmers.sankoss.java.visuals;

import javax.swing.ImageIcon;

import edu.chalmers.sankoss.core.protocol.Ship;


/**
 * Image of a ship
 * @author Daniel Eineving
 * @date 2014-03-30
 *
 */
public class ShipIcon extends ImageIcon{
		
	//TODO Should not ship have an image, instead of this?
	private Ship ship;
	
	/**
	 * Creates an image of a ship
	 * @param ship
	 */
	public ShipIcon(Ship ship){
		this.ship=ship;
		setShipImage();
	}
	
	/*
	public Coordinate getTopLeftCoordinate(){
		
	}
	*/
	
	/**
	 * Sets the image to correct size and rotation
	 */
	private void setShipImage(){
		
		int size = this.ship.getSize();
		
		//TODO Counting coordinates?
		//TODO make if smaller?
		if(size==5){
			if(ship.getStartCoordinate().getX()==ship.getEndCoordinate().getX()){
				if(ship.getStartCoordinate().getY() < ship.getEndCoordinate().getY()){
					// 5 front up
					setImage(null);
				}
				else{
					// 5 front down
					setImage(null);
				}
			}
			else{
				if(ship.getStartCoordinate().getX() < ship.getEndCoordinate().getX()){
					// 5 front left
					setImage(null);
				}
				else{
					// 5 front right
					setImage(null);
				}
			}
		}
		else if(size == 4){
			if(ship.getStartCoordinate().getX()==ship.getEndCoordinate().getX()){
				if(ship.getStartCoordinate().getY() < ship.getEndCoordinate().getY()){
					// 4 front up
					setImage(null);
				}
				else{
					// 4 front down
					setImage(null);
				}
			}
			else{
				if(ship.getStartCoordinate().getX() < ship.getEndCoordinate().getX()){
					// 4 front left
					setImage(null);
				}
				else{
					// 4 front right
					setImage(null);
				}
			}
		}
		else if(size == 3){
			if(ship.getStartCoordinate().getX()==ship.getEndCoordinate().getX()){
				if(ship.getStartCoordinate().getY() < ship.getEndCoordinate().getY()){
					// 3 front up
					setImage(null);
				}
				else{
					// 3 front down
					setImage(null);
				}
			}
			else{
				if(ship.getStartCoordinate().getX() < ship.getEndCoordinate().getX()){
					// 3 front left
					setImage(null);
				}
				else{
					// 3 front right
					setImage(null);
				}
			}
		}
		else if(size == 2){
			if(ship.getStartCoordinate().getX()==ship.getEndCoordinate().getX()){
				if(ship.getStartCoordinate().getY() < ship.getEndCoordinate().getY()){
					// 2 front up
					setImage(null);
				}
				else{
					// 2 front down
					setImage(null);
				}
			}
			else{
				if(ship.getStartCoordinate().getX() < ship.getEndCoordinate().getX()){
					// 2 front left
					setImage(null);
				}
				else{
					// 2 front right
					setImage(null);
				}
			}
		}
	}
}
