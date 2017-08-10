package com.jethrolai.api.fs;

import java.io.IOException;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This is the place for your unit test
 * 
 * TODO implement all the tests!!!!!!
 *
 * @author jlai
 *
 */
public class SimulationTest {
	Random random;

	@Before
	public void setUp() throws Exception {
		random = new Random();
		// TODO
	}
	
	
	
	

	@Test
	public void testConstruct() throws IOException {

		String accountName = "TestUser";
		String portfolioType = "Aggressive";
		Double mean = random.nextDouble();
		Double sd = random.nextDouble();
		Double fund = random.nextDouble();
		Double inflation = random.nextDouble();
		int terms = random.nextInt();
		int runs = random.nextInt();
		int upperPercentile = random.nextInt();
		int lowerPercentile = random.nextInt();
		double delta = 0.0000001;

		Simulation s = new Simulation(accountName, portfolioType, sd, mean, fund, inflation, terms, runs,
				upperPercentile, lowerPercentile);
		Assert.assertEquals(accountName, s.getAccountName());
		Assert.assertEquals(portfolioType, s.getPortfolioType());
		Assert.assertEquals(mean, s.getMean(), delta);
		Assert.assertEquals(sd, s.getStandardDeviation(), delta);
		Assert.assertEquals(fund, s.getFund(), delta);
		Assert.assertEquals(inflation, s.getInflation(), delta);
		Assert.assertEquals(terms, s.getTerms());
		Assert.assertEquals(runs, s.getRuns());
		Assert.assertEquals(upperPercentile, s.getUpperPercentile());
		Assert.assertEquals(lowerPercentile, s.getLowerPercentile());
	}

	@Test
	public void testCustomAccountName() throws IOException {
		String accountName = "TestUser" + random.nextInt();
		Simulation s = new Simulation(accountName, "Aggressive", 0.15675, 0.094324, 100000, 0.034, 20, 100000, 90, 10);
		Assert.assertEquals(accountName, s.getAccountName());
	}

	@Test
	public void testEmptyAccountName() throws IOException {
		Simulation s = new Simulation("", "Aggressive", 0.15675, 0.094324, 100000, 0.034, 20, 100000, 90, 10);
		Assert.assertEquals("Anonymous", s.getAccountName());

	}

	@Test
	public void testNullAccountName() throws IOException {
		Simulation s = new Simulation(null, "Aggressive", 0.15675, 0.094324, 100000, 0.034, 20, 100000, 90, 10);
		Assert.assertEquals("Anonymous", s.getAccountName());

	}
}