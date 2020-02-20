package com.lrm.x_games.Resources;

public class Teams{
    String name,score,tid;

    public Teams(String name, String score, String tid) {
        this.name = name;
        this.score = score;
        this.tid = tid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
