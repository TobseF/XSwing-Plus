/*
 * @version 0.0 20.12.2008
 * @author Tobse F
 */
package lib.mylib.hacks;

import org.newdawn.slick.particles.ConfigurableEmitter.Value;

/**
 * A configurable simple single value
 * 
 * @author void
 */
public class SimpleValue implements Value {

	/** The value configured */
	private float value;

	/**
	 * Create a new configurable new value
	 * 
	 * @param value The initial value
	 */
	public SimpleValue(float value) {
		this.value = value;
	}

	/**
	 * Get the currently configured value
	 * 
	 * @return The currently configured value
	 */
	public float getValue(float time) {
		return value;
	}

	/**
	 * Set the configured value
	 * 
	 * @param value The configured value
	 */
	public void setValue(float value) {
		this.value = value;
	}
}