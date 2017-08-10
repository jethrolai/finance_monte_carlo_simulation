package com.jethrolai.api.fs;

import java.io.BufferedReader;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.jethrolai.api.fs.Simulator.ReaderFactory;

/**
 * Let's try to test the main method
 * 
 * @author jlai
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ReaderFactory.class)
public class SimulatorTest {

    @Test
    public void testMain() throws Exception {
        String[] filePath = new String[] { "fakefile.csv" };
        PowerMockito.mockStatic(ReaderFactory.class);
        PowerMockito.mockStatic(ReaderFactory.class);
        BufferedReader mockReader = PowerMockito.mock(BufferedReader.class);
        Stream<String> mockStream = PowerMockito.mock(Stream.class);
        BDDMockito.given(ReaderFactory.getReader(filePath[0])).willReturn(mockReader);
        PowerMockito.when(mockReader.lines()).thenReturn(mockStream);

        PowerMockito.doReturn(mockStream).when(mockStream).parallel();
        PowerMockito.doNothing().when(mockStream).forEach(Mockito.any());
        PowerMockito.doNothing().when(mockReader).close();

        Simulator.main(filePath);

        Mockito.verify(mockReader).lines();
        Mockito.verify(mockStream).parallel();
        Mockito.verify(mockStream).forEach(Mockito.any());
        Mockito.verify(mockReader).close();

    }
}
