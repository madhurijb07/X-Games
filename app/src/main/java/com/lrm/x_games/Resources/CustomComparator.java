package com.lrm.x_games.Resources;

import java.util.Comparator;

public class CustomComparator implements Comparator<Teams> {
    @Override
    public int compare(Teams o1, Teams o2) {
        return o1.getScore().compareTo(o2.getScore());
    }
}
