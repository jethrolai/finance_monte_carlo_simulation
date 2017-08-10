package com.jethrolai.api.fs;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import com.jethrolai.api.fs.utils.MathUtil;

/**
 * Placeholder of simulation profile and metadata
 * 
 * @author jethrolai
 *
 */
public class Simulation {
    private static final Logger logger = Logger.getLogger(Simulator.class.getName());

    final private String accountName;
    final private String portfolioType;
    final private double standardDeviation;
    final private double mean;
    final private double fund;
    final private double inflation;
    final private int terms;
    final private int runs;
    final private int upperPercentile;
    final private int lowerPercentile;
    private List<Double> runResults;
    private Random random = new Random();
    static final private NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();

    private Status status;

    private enum Status {
        DONE,
        RUNNING,
        NEW
    }

    public Simulation(String accountName, String portfolioType, double standardDeviation, double mean, double fund, double inflation,
            int terms, int runs, int upperPercentile, int lowerPercentile) {
        super();
        // TODO validation for input
        this.accountName = accountName == null || accountName.isEmpty() ? "Anonymous" : accountName;

        this.portfolioType = portfolioType != null
                && (portfolioType.trim().equalsIgnoreCase("Aggressive") || portfolioType.trim().equalsIgnoreCase("Conservative"))
                        ? portfolioType : "Unknown Porfolio Type";

        this.standardDeviation = standardDeviation;
        this.mean = mean;
        this.fund = fund;
        this.inflation = inflation;
        this.terms = terms;
        this.runs = runs;
        this.upperPercentile = upperPercentile;
        this.lowerPercentile = lowerPercentile;
        status = Status.NEW;

        runResults = new ArrayList<Double>();
    }

    public boolean simulate() {

        if (status == Status.RUNNING) {
            logger.log(Level.WARN, "This simulation is currently running. ");
            return false;
        }

        status = Status.RUNNING;

        IntStream.range(0, runs).parallel().forEach(run -> {
            run();
        });

        Collections.sort(runResults);

        status = Status.DONE;

        logger.log(Level.DEBUG, "Simulation results: ");
        runResults.stream().forEach(d -> logger.log(Level.DEBUG, d));
        return true;
    }

    private double findPercentileValue(int percentile) {
        return runResults.get(MathUtil.getPercentilePosition(runs, percentile));
    }

    private void run() {
        double[] projection = new double[] { fund };
        IntStream.range(1, terms + 1).forEach(term -> {
            projection[0] = (projection[0] * (random.nextGaussian() * standardDeviation + (1 + mean))) / (1d + inflation);
        });

        synchronized (this) {
            this.runResults.add(projection[0]);
        }
    }

    /**
     * might be useful if this simulator becomes a microservices/api one day.
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public JSONObject getResultJson() {
        if (status != Status.DONE)
            return null;

        JSONObject result = new JSONObject();
        result.put("accountName", this.accountName);
        result.put("portfolioType", this.portfolioType);
        result.put("mean", this.mean);
        result.put("standardDeviation", this.standardDeviation);
        result.put("inflation", this.inflation);
        result.put("initialFund", this.fund);
        result.put("termsInYear", this.terms);
        result.put("totalRuns", this.terms);
        result.put("upperPercentile" + this.upperPercentile, this.findPercentileValue(this.upperPercentile));
        result.put("lowerPercentile" + this.lowerPercentile, this.findPercentileValue(this.lowerPercentile));
        result.put("median", MathUtil.findMedian(runResults));

        return result;
    }

    public String getResultInPlainText() {
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        result.put("Account Name", this.accountName);
        result.put("Portfolio Type", this.portfolioType);
        result.put("Mean Return Rate", this.mean);
        result.put("Standard Deviation", this.standardDeviation);
        result.put("Inflation", this.inflation);
        result.put("Initial Fund", currencyFormatter.format(this.fund));
        result.put("All Terms In Year", this.terms);
        result.put("Total Runs", this.terms);
        result.put("Percentile " + this.upperPercentile + " Return",
                currencyFormatter.format(this.findPercentileValue(this.upperPercentile)));
        result.put("Percentile " + this.lowerPercentile + " Return",
                currencyFormatter.format(this.findPercentileValue(this.lowerPercentile)));
        result.put("Median Return", currencyFormatter.format(MathUtil.findMedian(runResults)));
        StringBuilder sb = new StringBuilder();
        result.keySet().stream().forEach(k -> {
            sb.append(String.format("%1$-20s = %2$-30s\n", k, result.get(k)));
        });

        return sb.toString();
    }

    public String getResultInJson() {
        return this.getResultJson().toJSONString();
    }

    public String getAccountName() {
        return accountName;
    }

    public String getPortfolioType() {
        return portfolioType;
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }

    public double getMean() {
        return mean;
    }

    public double getFund() {
        return fund;
    }

    public double getInflation() {
        return inflation;
    }

    public int getTerms() {
        return terms;
    }

    public int getRuns() {
        return runs;
    }

    public int getUpperPercentile() {
        return upperPercentile;
    }

    public int getLowerPercentile() {
        return lowerPercentile;
    }

    public Status getStatus() {
        return status;
    }

}
