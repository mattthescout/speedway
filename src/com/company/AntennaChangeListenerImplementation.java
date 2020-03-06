package com.company;

import com.impinj.octane.ImpinjReader;
import com.impinj.octane.AntennaChangeListener;
import com.impinj.octane.AntennaEvent;

public class AntennaChangeListenerImplementation implements AntennaChangeListener {

    @Override
    public void onAntennaChanged(ImpinjReader reader, AntennaEvent ae) {
        System.out.println("Antenna Change--port: " + ae.getPortNumber() + " state: " + ae.getState().toString());
    }

}
