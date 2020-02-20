package com.lrm.x_games.Resources;

public class Players {
    String eid, name, team, score;

    public Players(String eid, String name, String team, String score) {
        this.eid = eid;
        this.name = name;
        this.team = team;
        this.score = score;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
