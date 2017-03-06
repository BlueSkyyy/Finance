package cn.com.xbed.common.util;

import java.math.BigDecimal;

public final class NumberUtil
{
	public static Double formatDoubleByN(Double value, Integer num)
	{
		BigDecimal b = new BigDecimal(value);
		double myNum3 = b.setScale(num, BigDecimal.ROUND_HALF_UP).doubleValue();
		return myNum3;
	}
	
	public static Double formatDoubleBy2(Double value)
	{
		return formatDoubleByN(value, 2);
	}
	
}
