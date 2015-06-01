package model;
/**
 * This is a model class represents a sol (1 sol represents one day which is equivalent 
 * to one 24 hours and 40 minutes of the Earth time) temperature of Mars.
 * @author <b>Thermal Group</b> (CS537: California State University, Los Angeles) <br><br>
 * 
 * <ul>
 * 	<li> Debasish Guha: debasish.gt@gmail.com </li>
 *  <li> Mahdiye Jamali: mahdiye.jamali@gmail.com </li>
 *  <li> Gayatri Devpal: gayatridevpal18@gmail.com </li>
 *  <li> Mohammad Yazdani: m.yazdani2010@gmail.com </li>
 * </ul>
 *
 */
public class SolTemp
{
  private int minute;
  private float temp;
  
  /**
   * Default constructor 
   */
  public SolTemp()
  {  }
  
  /**
   * Sets the vales int for minute and float for temperature.
   * @param minute
   * @param temp
   */
  public SolTemp(int minute , float temp)
  {
	this.minute = minute;
	this.temp = temp;
  }
  
  /**
   * Returns the minute value.
   * @return int
   */
  public int getMinute()
  {
    return minute;
  }
  
  /**
   * Sets the minute value
   * @param minute
   */
  public void setMinute(int minute)
  {
    this.minute = minute;
  }
  
  /**
   * Returns the temperature vale
   * @return float
   */
  public float getTemp()
  {
    return temp;
  }
  
  /**
   * Sets the temperature value
   * @param temp
   */
  public void setTemp(float temp)
  {
    this.temp = temp;
  }
  
  /**
   * Overriding the toString() of supper class Object for representation the object detail.
   * @return String  
   */
  @Override
  public String  toString()
  {
	return "Minute: " + minute + ",  Temp: "+ temp;
  }
}
