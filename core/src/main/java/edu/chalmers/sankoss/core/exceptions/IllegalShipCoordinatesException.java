package edu.chalmers.sankoss.core.exceptions;

/**
 * Exception for declaring there is an illegal set of coordinates for a ship.
 * 
 * @author Daniel Eineving
 * @date 2014-04-08
 */
public class IllegalShipCoordinatesException extends Exception {

	/** {@inheritDoc} */
	public IllegalShipCoordinatesException() {
		super();
	}

	/** {@inheritDoc} */
	public IllegalShipCoordinatesException(String message) {
		super(message);
	}

	/** {@inheritDoc} */
	public IllegalShipCoordinatesException(String message, Throwable cause) {
		super(message, cause);
	}

	/** {@inheritDoc} */
	public IllegalShipCoordinatesException(Throwable cause) {
		super(cause);
	}
}
