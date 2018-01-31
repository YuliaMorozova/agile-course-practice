package ru.unn.agile.Range.infrastructure_lab3_legacy;

import ru.unn.agile.Range.ViewModel.legacy.ILogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TxtLogger implements ILogger {
    public TxtLogger(final String fileName) {
        this.fileName = fileName;
        BufferedWriter logWriter = null;
        try {
            logWriter = new BufferedWriter(new FileWriter(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        writer = logWriter;
    }

    @Override
    public void log(final String str) {
        try {
            writer.write(now() + " > " + str);
            writer.newLine();
            writer.flush();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader bufReader;
        ArrayList<String> log = new ArrayList<String>();
        try {
            bufReader = new BufferedReader(new FileReader(fileName));
            String line = bufReader.readLine();
            while (line != null) {
                log.add(line);
                line = bufReader.readLine();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return log;
    }

    private static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        return sdf.format(cal.getTime());
    }

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final BufferedWriter writer;
    private final String fileName;
}
