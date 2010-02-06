/*
 * @version 0.0 19.07.2009
 * @author Tobse F
 */
package lib.mylib.ident;

/**
 * Makes it possible to return an identity for a PC or an user, for the acknowledgment.
 * 
 * @author Tobse
 */
public interface Identable {

	static final String IDENT_NAME = "indent";
	static final String IDENT_EXTENSION = ".bin";

	/**
	 * Calculates an id to identify this computer
	 * 
	 * @return id to identify this computer
	 */
	public String getIdentity();

	/**
	 * calculates a new id
	 * 
	 * @see #getIdentity()
	 */
	public void newIdentity();
}
