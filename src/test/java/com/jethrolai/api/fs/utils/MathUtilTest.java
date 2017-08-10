package com.jethrolai.api.fs.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

public class MathUtilTest {

	@Test
	public void testFindMedian() {
		List<Double> list = Stream.of("1,2,3".split(",")).mapToDouble(Double::parseDouble).boxed()
				.collect(Collectors.toList());

		list = Stream.of("1,2,3,4".split(",")).mapToDouble(Double::parseDouble).boxed()
				.collect(Collectors.toList());
		Assert.assertEquals(2.5, MathUtil.findMedian(list), 0.00000000001);
		
		list = new ArrayList<Double>();
		
		Assert.assertEquals(Double.NaN, MathUtil.findMedian(list), 0.00000000001);
	}
	
	
	@Test
	public void testGetPercentilePosition() {		
		Assert.assertEquals(95, MathUtil.getPercentilePosition(100, 95));
		Assert.assertEquals(100, MathUtil.getPercentilePosition(100, 100));
		Assert.assertEquals(1, MathUtil.getPercentilePosition(100, 1));
		Assert.assertEquals(50, MathUtil.getPercentilePosition(100, 50));
		Assert.assertEquals(45, MathUtil.getPercentilePosition(50, 90));
		Assert.assertEquals(5, MathUtil.getPercentilePosition(50, 10));
		Assert.assertEquals(1900, MathUtil.getPercentilePosition(2000, 95));
		Assert.assertEquals(0, MathUtil.getPercentilePosition(2000, 0));
		Assert.assertEquals(-1, MathUtil.getPercentilePosition(2000, -1));
		Assert.assertEquals(0, MathUtil.getPercentilePosition(0, 50));
	}
	
	

}
