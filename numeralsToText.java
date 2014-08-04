import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


public class numeralsToText {
	public static String currency;
	public static String subCurrency;
	
	public static Map<Integer, String> denominations;
	
	public numeralsToText(String system)
	{		
		if (system.equals("western"))
		{
			denominations = makeDenominationsWestern();
			currency = "Dollars";
			subCurrency = "Cents";
		}
		else
		{
			denominations = makeDenominationsIndian();
			currency = "Rupees";
			subCurrency = "Paise";
		}	
	}
		
	public static void main(String args[])
	{
		String input = "1191111.68";
		String system = "western";
		numeralsToText numToText = new numeralsToText(system);
		Double amount = Double.parseDouble(input);
		
		System.out.println(amountInwords(amount));	
	}	
	private static String amountInwords(double amount) 
	{		
			int integralPart = (int)(amount);
			String inwords = "";
			int fractionalPart = (int) (amount * 100 - integralPart * 100);
			
			if(amount >= 1)
			{
				inwords = getIntegralInWords(integralPart);
				if(getFractionInWords(fractionalPart)!="")
					inwords += currency + " and " + getFractionInWords(fractionalPart);
			}
			else if(amount > 0)
			{
				inwords += getFractionInWords(fractionalPart);
			}
			else
			{
				inwords = "zero ";
			}
			return currencyFormat(inwords);			
	}
	private static String getIntegralInWords(int integralPart) 
	{		
		String inwords = "";
		Iterator it = denominations.entrySet().iterator();
		
    	while (it.hasNext()) 
    	{
        	Map.Entry pairs = (Map.Entry)it.next();
        	Integer denomination = (Integer) pairs.getKey();
        	int d = integralPart / denomination;
        	
        	if(d > 99)
        	{
        		inwords += getIntegralInWords(d);
        	}
        	else if ( d > 0 ) 
        	{        		
        		if(integralPart % denomination == 0 && inwords != "")
        			inwords += "and " + splitBy2Digits(d) ;
        		else
        			inwords += splitBy2Digits(d) ;
        	}
        	if( d > 0)
        	{
        		inwords += pairs.getValue();
        	}
        		
        	integralPart = integralPart % denomination;
		};
		return inwords;
	}
	private static String getFractionInWords(int fraction) 
	{
		if(fraction != 0)
		{
			return splitBy2Digits(fraction) + subCurrency + " ";
		}
		return "";
	}
	public static String splitBy2Digits(int num)
	{
		String []upto19 = {"", "one ", "two ", "three ", "four ", "five ", "six ", "seven ", "eight ", "nine ", "ten ", "eleven ", "twelve ", "thirteen ", "fourteen ", "fifteen ", "sixteen ", "seventeen ", "eighteen ", "nineteen "};
		String []tens = {"", "ten ", "twenty ", "thirty ", "forty ", "fifty ", "sixty ", "seventy ", "eighty ", "ninety "};
		if(num / 10 > 1)
		{
			return tens[num / 10] + upto19[num % 10];
		}
		else
		{
			return upto19[num];
		}
	}
	private static Map<Integer, String> makeDenominationsWestern() 
	{
		Map<Integer, String> denominations = new HashMap<Integer, String>();
		denominations.put(100000000 , "billion ");
		denominations.put(1000000 , "million ");
		denominations.put(1000 , "thousand ");
		denominations.put(100 , "hundred ");
		denominations.put(1 , "");
		denominations = new TreeMap<Integer, String>(denominations);
		denominations = ((TreeMap<Integer, String>) denominations).descendingMap();
		return denominations;
	}
	private static Map<Integer, String> makeDenominationsIndian() 
	{
		Map<Integer, String> denominations = new HashMap<Integer, String>();
		denominations.put(10000000 , "crore ");
		denominations.put(100000 , "lakh ");
		denominations.put(1000 , "thousand ");
		denominations.put(100 , "hundred ");
		denominations.put(1 , "");
		denominations = new TreeMap<Integer, String>(denominations);
		denominations = ((TreeMap<Integer, String>) denominations).descendingMap();
		return denominations;
	}
	private static String currencyFormat(String inwords) 
	{
		return  inwords + "only";
	}
	
}