package common;

/**
 * This class used to package up data for use in components, such as drop-down boxes.
 * @author Roshun Jones
 * @version 1.0
 */
public class ReferenceObject {
  /**
   * Default constructor.
   */
  public ReferenceObject() {}
  
  /**
   * 2-Arg Constructor.
   */
  public ReferenceObject(final String aDisplay, final Object aObject) {
    display = aDisplay;
    data = aObject;
  }
  
  /**
   * Holds the display text of the object.
   */
  private String display;
  
  /**
   * Holds the value of the object.
   */
  private Object data;
  
  /**
   * Returns display string.
   */
  public String getDisplay() {
    return display;
  }
  
  /**
   * Sets display string.
   */
  public void setDisplay(final String aDisplay) {
    display = aDisplay;
  }
  
  /**
   * Sets data payload.
   */
  public void setData(final Object aObject) {
    data = aObject;
  }
  
  /**
   * Gets data object.
   */
  public Object getData() {
    return data;
  }
  
  /**
   * Returns String representation of object.
   */
  @Override
  public String toString() {
    return display;
  }
}
