package me.titan.devroomtrial.util;

import java.text.DecimalFormat;

public class NumberStringParser {

	//public static BigInteger SEXTILLION = new BigInteger("1000000000000000000000");
	public static long QUANT = 1_000_000_000_000_000_000L;
	public static long QUAD = 1_000_000_000_000_000L;
	public static long TRILLION = 1_000_000_000_000L;
	public static long BILLION = 1_000_000_000;
	public static long MILLION = 1_000_000;
	public static long KILO =  1_000;

	public static String S_SMALL = "s";
	public static String Q2_SMALL = "Q";
	public static String Q_SMALL = "q";
	public static String T_SMALL = "t";
	public static String B_BIG = "billion";
	public static String B_SMALL = "b";
	public static String M_BIG = "million";
	public static String M_SMALL = "m";
	public static String K = "k";
	public static String fixString(double value) {


		DecimalFormat de = new DecimalFormat("###.##");
		return de.format(value);
//		String str = "";
//		if(value == ((int) value)){
//			str = ((int) value)+"";
//		}else{
//			str = value + "";
//		}

	}
	public static String parseSmall(long t){
		String r = "";
		// 3.0
		// 3.1212

		if(t >= QUANT){

			r = fixString(((double) t / QUANT)  ) + Q2_SMALL;
		}else if(t >= QUAD){

			r = fixString(((double) t / QUAD)  ) + Q_SMALL;
		}else if(t >= TRILLION){

			r = fixString(((double) t / TRILLION)  ) + T_SMALL;
		}else if(t >= BILLION){

			r = fixString(((double) t / BILLION)  ) + B_SMALL;
		}else if(t >= MILLION){
			r = fixString(((double) t / MILLION) )+M_SMALL;
		}else if(t >= KILO){
			r = fixString(((double) t /KILO))+ K;
		}else{
			r = t+"";
		}
		return r;
	}
//	public static String parseBig(long t){
//		String r = "";
//		if(t >= BILLION){
//
//			r = fixString(((double) t / BILLION)  , B_BIG );
//		}else if(t >= MILLION){
//			r = fixString(((double) t / MILLION) +"" , M_BIG);
//		}else if(t >= KILO){
//			r = fixString(((double) t /KILO) +"" , K);
//		}else{
//			r = t+"";
//		}
//		return r;
//	}
}
