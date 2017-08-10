package com.jethrolai.api.fs.utils;

import java.util.List;
import java.util.stream.DoubleStream;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * few math related helpers
 * 
 * @author jethrolai
 *
 */
public class MathUtil {

    /**
     * as name
     * 
     * @param list
     * @return
     */
    static public double findMedian(List<Double> list) {
        if (list == null || list.size() == 0)
            return Double.NaN;
        double median;
        if (list.size() % 2 == 0)
            median = (list.get(list.size() / 2) + list.get(list.size() / 2 - 1)) / 2;
        else
            median = list.get(list.size() / 2);
        return median;
    }

    /**
     * find position of the percentile from 1 to total
     * 
     * @param total
     * @param percentile
     * @return
     */
    static public int getPercentilePosition(int total, double percentile) {
        if (percentile < 0)
            return -1;
        return (int) (Math.ceil(total * (double) percentile / (double) 100));
    }

    /**
     * TODO to be implemented
     * 
     * If the simulation runs is very larger, you might want to computer median in place without keeping all results. A simple
     * idea is to keep a few things while the simulation is running: current median, smallest on the larger half of current
     * results, largest on the smaller half of result, current count of how many results are larger than current media, and
     * current count of how many results are smaller than current median.
     * 
     * Once you have those you can adjust those numbers as you go. Each run you will need to compare median, smallest larger
     * number, largest smaller number, and the size of both side. then the stream finished, based on the total number and which
     * side has more results (two sides will either have same amount of results or one side will have one more), you can decide
     * how to calculate the final median by either average(current median, the end number of bigger half) or simply return median
     * if both sides have the same amount of results.
     * 
     * @param stream
     * @return
     */
    static public double inPlaceMedian(DoubleStream stream) {
        throw new NotImplementedException();
    }

    /**
     * TODO to be implemented
     * 
     * Without keeping all results and sorting them at the end result, you can use a max heap and min heap as you go, but this
     * won't save you space. To do this in place is similar to inPlaceMedian, instead of trying to make two sides are equals, you
     * make them proportional to the percentile input on both sides
     * 
     * @param stream
     * @return
     */
    static public double inPlacePercentile(DoubleStream stream, int percentile) {
        throw new NotImplementedException();
    }

}
