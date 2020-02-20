package com.lrm.x_games.Resources;

import android.net.Uri;

import org.jetbrains.annotations.NotNull;

public class StaticProfileData {
    public static String name,team,email,score;
    public static int owner=0,captain=0;
    Uri profile;
    @NotNull
    public static String eid;
    public static int admin=0;

    public static int getAdmin() {
        return admin;
    }

    public static void setAdmin(int admin) {
        StaticProfileData.admin = admin;
    }

    public static String getScore() {
        return score;
    }

    public static void setScore(String score) {
        StaticProfileData.score = score;
    }

    public static int getOwner() {
        return owner;
    }

    public static void setOwner(int owner) {
        StaticProfileData.owner = owner;
    }

    public static int getCaptain() {
        return captain;
    }

    public static void setCaptain(int captain) {
        StaticProfileData.captain = captain;
    }

    @NotNull
    public static String getEid() {
        return eid;
    }

    public static void setEid(@NotNull String eid) {
        StaticProfileData.eid = eid;
    }

    public Uri getProfile() {
        return profile;
    }

    public void setProfile(Uri profile) {
        this.profile = profile;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        StaticProfileData.name = name;
    }

    public static String getTeam() {
        return team;
    }

    public static void setTeam(String team) {
        StaticProfileData.team = team;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        StaticProfileData.email = email;
    }
}
