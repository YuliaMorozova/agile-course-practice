package ru.unn.agile.Range.infrastructure_lab3_legacy;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.unn.agile.Range.ViewModel.legacy.RegexMatcher.matchesPattern;

public class TxtLoggerTest {
    @Before
    public void setUp() {
        txtLogger = new TxtLogger(FILE_NAME);
    }

    @Test
    public void canCreateLoggerWithFileName() {
        assertNotNull(txtLogger);
    }

    @Test
    public void canCreateLogFileOnDisk() {
        try {
            new BufferedReader(new FileReader(FILE_NAME));
        } catch (FileNotFoundException ex) {
            fail("File " + FILE_NAME + " was not found!");
        }
    }

    @Test
    public void canWriteLogMessage() {
        String testMessage = "Test message";

        txtLogger.log(testMessage);

        String message = txtLogger.getLog().get(0);
        assertThat(message, matchesPattern(".*" + testMessage + "$"));
    }

    @Test
    public void canWriteSeveralLogMessage() {
        String[] testMessages = {"Test message 1", "Test message 2"};

        txtLogger.log(testMessages[0]);
        txtLogger.log(testMessages[1]);

        List<String> actualMessages = txtLogger.getLog();
        for (int i = 0; i < actualMessages.size(); i++) {
            assertThat(actualMessages.get(i), matchesPattern(".*" + testMessages[i] + "$"));
        }
    }

    @Test
    public void doesLogContainDateAndTime() {
        String testMessage = "Test message";

        txtLogger.log(testMessage);

        String message = txtLogger.getLog().get(0);
        assertThat(message, matchesPattern("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} > .*"));
    }

    private static final String FILE_NAME = "./TxtLoggerTests-lab3-legacy.log";
    private TxtLogger txtLogger;
}
