package com.company;


import com.impinj.octane.ImpinjReader;
import com.impinj.octane.OctaneSdkException;
import com.impinj.octane.Settings;
import com.impinj.octane.Tag;

import com.impinj.octane.Tag;
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        BasicConfigurator.configure();

        try {
            String hostname = "10.0.0.124";

//            if (hostname == null) {
//                throw new Exception("Must specify the '"
//                        + SampleProperties.hostname + "' property");
//            }

            ImpinjReader reader = new ImpinjReader();

            System.out.println("Connecting to " + hostname);
            reader.connect(hostname);

//            Tag tag = new Tag();
//            tag.getAntennaPortNumber();
            //Settings settings = reader.queryDefaultSettings();

            //reader.applySettings(settings);

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
