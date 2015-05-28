package constant;
/**
 * enum ThermalCommands contains all the commands that we are going to accepts from other modules/groups
 * 
 * @author <b>Thermal Group</b> (CS537: California State University, Los Angeles) <br><br>
 * 
 * <ul>
 * 	<li> Debasish Guha: debasish.gt@gmail.com </li>
 *  <li> Mahdiye Jamali: mahdiye.jamali@gmail.com </li>
 *  <li> Gayatri Devpal: gayatridevpal18@gmail.com </li>
 *  <li> Mohammad Yazdani: m.yazdani2010@gmail.com </li>
 * </ul>
 */
public enum ThermalCommands {
	CURRENT_TEMPERATURE, 
	OUTSIDE_TEMPERATURE, 
	CURRENT_TEMPERATURES, 
	PROCESS_TEMPERATURE,
	THRM_HEATER_ON,
	THRM_HEATER_OFF,
	THRM_COOLER_ON,THRM_COOLER_OFF;
}
