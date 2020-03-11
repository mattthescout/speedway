package com.company;


import com.impinj.octane.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;
import org.apache.mina.filter.logging.LogLevel;
import sun.rmi.runtime.Log;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.ERROR);

        try {
            String hostname = SampleProperties.hostname;

            ImpinjReader reader = new ImpinjReader();

            System.out.println("Connecting to " + hostname);
            reader.connect(hostname);

            Settings settings = reader.queryDefaultSettings();

            ReportConfig report = settings.getReport();
            report.setIncludeAntennaPortNumber(true);

            report.setMode(ReportMode.Individual);

            settings.setReaderMode(ReaderMode.MaxThroughput);
            settings.setSearchMode(SearchMode.DualTarget);
            settings.setSession(2);
            settings.setTagPopulationEstimate(16);

            AntennaConfigGroup antennas = settings.getAntennas();
            antennas.disableAll();
            antennas.enableById(new short[] {1, 2, 3, 4});

            settings.getReport().setIncludeAntennaPortNumber(true);
            settings.getReport().setIncludeSeenCount(true);
            settings.getReport().setIncludeChannel(true);
            settings.getReport().setIncludePeakRssi(true);
            settings.getReport().setIncludePhaseAngle(true);


            reader.setTagReportListener(new TagReportListenerImplementation());

            reader.setBufferOverflowListener(new BufferOverflowListenerImplementation());
            reader.setBufferWarningListener(new BufferWarningListenerImplementation());

            System.out.println("Applying Settings");
            reader.applySettings(settings);

            System.out.println("Starting");
            reader.start();
//            Thread.sleep(11000);
            System.out.println("Press Enter to continue and read all tags.");
            Scanner s = new Scanner(System.in);
            s.nextLine();
            reader.queryTags();


            System.out.println("Stopping " + hostname);
            reader.stop();

            System.out.println("Disconnecting from " + hostname);
            reader.disconnect();

            System.out.println("Done");

        } catch (OctaneSdkException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace(System.out);
        }
    }
}
