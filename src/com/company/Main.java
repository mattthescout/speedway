package com.company;


import com.impinj.octane.AntennaConfigGroup;
import com.impinj.octane.ImpinjReader;
import com.impinj.octane.OctaneSdkException;
import com.impinj.octane.ReaderMode;
import com.impinj.octane.ReportMode;
import com.impinj.octane.SearchMode;
import com.impinj.octane.Settings;
import com.impinj.octane.Tag;
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        BasicConfigurator.configure();

        try {
            String hostname = SampleProperties.hostname;

            ImpinjReader reader = new ImpinjReader();

            System.out.println("Connecting to " + hostname);
            reader.connect(hostname);

            Settings settings = reader.queryDefaultSettings();

            settings.getReport().setMode(ReportMode.BatchAfterStop);
            settings.getReport().setIncludeAntennaPortNumber(true);
            settings.getReport().setIncludeChannel(true);
            settings.getReport().getIncludeAntennaPortNumber();
            settings.getReport().getIncludeChannel();
            settings.getReport().getIncludeSeenCount();

            settings.setReaderMode(ReaderMode.AutoSetDenseReader);
            settings.setSearchMode(SearchMode.DualTarget);
            settings.setSession(2);
            settings.setTagPopulationEstimate(250);

            AntennaConfigGroup ac = settings.getAntennas();

            ac.disableAll();
            ac.getAntenna(1).setEnabled(true);
            ac.getAntenna(2).setEnabled(true);
            ac.setIsMaxTxPower(true);
            ac.setIsMaxRxSensitivity(true);

            reader.applySettings(settings);

            reader.setAntennaChangeListener(new AntennaChangeListenerImplementation());

            reader.setTagReportListener(new TagReportListenerImplementation());

            reader.start();

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
