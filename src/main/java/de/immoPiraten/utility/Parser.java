package de.immoPiraten.utility;

import java.util.function.Function;

public class Parser {
	
	public static String parseString(Object value){
		return (value != null) ? value.toString() : null;
	}

	public static Float parseFloat(Object value){
		return Parser.parseValue(value, valueToConvert -> Float.parseFloat(value.toString()));
	}
	
	public static Double parseDouble(Object value){
		return Parser.parseValue(value, valueToConvert -> Double.parseDouble(valueToConvert.toString()));
	}	
	
	public static Integer parseInteger(Object value){
		return Parser.parseValue(value, valueToConvert -> Integer.parseInt(valueToConvert.toString()));
	}		
	
	public static Boolean parseBoolean(Object value){
		return Parser.parseValue(value, valueToConvert -> Boolean.parseBoolean(valueToConvert.toString()));
	}		
	
	private static <T> T parseValue(Object value, Function<Object, T> parseFunction){
		T convertedValue = null;		
		
		if (value != null)
		{		
			try{
				convertedValue = parseFunction.apply(value);
			}
			catch(Exception e)
			{
				convertedValue = null;
			}
		}		
		
		return convertedValue;
	}	
}