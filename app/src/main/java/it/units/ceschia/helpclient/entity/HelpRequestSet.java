package it.units.ceschia.helpclient.entity;

import java.util.ArrayList;

public class HelpRequestSet {
    private ArrayList<HelpRequest> helpRequests;

    public HelpRequestSet() {
    }

    public HelpRequestSet(ArrayList<HelpRequest> helpRequests) {
        this.helpRequests = helpRequests;
    }

    public ArrayList<HelpRequest> getHelpRequests() {
        return helpRequests;
    }

    public void setHelpRequests(ArrayList<HelpRequest> helpRequests) {
        this.helpRequests = helpRequests;
    }
}
