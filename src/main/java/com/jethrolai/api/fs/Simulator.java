package com.jethrolai.api.fs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * 
 * entry point of finance simulator
 * 
 * @author jethrolai
 *
 */
public class Simulator {
    private static final Logger logger = Logger.getLogger(Simulation.class.getName());

    public static void main(String[] args) throws IOException {

        // TODO need some input validation
        // TODO use Option or other command line library to properly handle arguments
        if (args != null && args.length == 1) {
            logger.log(Level.INFO, "Running custom simulations ...");
            StringBuffer sb = new StringBuffer("\n\n======================================\nResults:\n\n");
            
            BufferedReader reader = ReaderFactory.getReader(args[0]);
            long start = System.currentTimeMillis();
            reader.lines().parallel().forEach(input -> {
                Iterator<String> inputs = Arrays.asList(input.split(",")).iterator();
                Simulation simulation = new Simulation(inputs.next(), inputs.next(), Double.parseDouble(inputs.next()),
                        Double.parseDouble(inputs.next()), Double.parseDouble(inputs.next()), Double.parseDouble(inputs.next()),
                        Integer.parseInt(inputs.next()), Integer.parseInt(inputs.next()), Integer.parseInt(inputs.next()),
                        Integer.parseInt(inputs.next()));
                simulation.simulate();
                simulation.getResultInPlainText();
                sb.append(simulation.getResultInPlainText()).append("\n").toString();
            });
            String elapstedTime = formatInterval(System.currentTimeMillis() - start);
            logger.log(Level.INFO, new StringBuffer("completed in ").append(elapstedTime));
            logger.log(Level.INFO, sb.toString());
            reader.close();
            return;
        }
        else {
            // if no input, run demo
            demo();
        }
    }

    /**
     * Similar to BufferedReaderFactory in Spring, use this for inversion of control and testability
     * 
     * @author jlai
     *
     */
    protected static class ReaderFactory {
        protected static BufferedReader getReader(String filePath) throws FileNotFoundException {
            return new BufferedReader(new FileReader(filePath));
        }
    }

    private static void demo() {

        logger.log(Level.INFO, "Running demo simulations ...");

        Simulation s = new Simulation("Anonymous", "Aggressive", 0.15675, 0.094324, 100000, 0.034, 20, 100000, 90, 10);
        s.simulate();
        Simulation c = new Simulation("", "Conservative", 0.063438, 0.06189, 100000, 0.035, 20, 100000, 90, 10);
        c.simulate();

        logger.log(Level.INFO, " completed!");

        StringBuffer sb = new StringBuffer("\n\n======================================\nResults:\n\n");

        sb.append(c.getResultInPlainText());
        sb.append(s.getResultInPlainText());

        logger.log(Level.INFO, sb.append("\n").toString());

    }

    private static String formatInterval(final long elapsedTime) {
        final long hr = TimeUnit.MILLISECONDS.toHours(elapsedTime);
        final long min = TimeUnit.MILLISECONDS.toMinutes(elapsedTime - TimeUnit.HOURS.toMillis(hr));
        final long sec = TimeUnit.MILLISECONDS.toSeconds(elapsedTime - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min));
        final long ms = TimeUnit.MILLISECONDS
                .toMillis(elapsedTime - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min) - TimeUnit.SECONDS.toMillis(sec));
        return String.format("%02d:%02d:%02d.%03d", hr, min, sec, ms);
    }

}
