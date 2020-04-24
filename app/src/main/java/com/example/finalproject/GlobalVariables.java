package com.example.finalproject;

import android.app.Application;

public class GlobalVariables extends Application {
    private String Mtitle1,MPoster1,Mtitle2,MPoster2,Mtitle3,MPoster3,Counter="1";
    //set functions
    public void setMPoster1(String MPoster1) {
        this.MPoster1 = MPoster1;
    }

    public void setMPoster2(String MPoster2) {
        this.MPoster2 = MPoster2;
    }

    public void setMPoster3(String MPoster3) {
        this.MPoster3 = MPoster3;
    }

    public void setMtitle1(String mtitle1) {
        Mtitle1 = mtitle1;
    }

    public void setMtitle2(String mtitle2) {
        Mtitle2 = mtitle2;
    }

    public void setMtitle3(String mtitle3) {
        Mtitle3 = mtitle3;
    }

    public void setCounter(String counter) {
        Counter = counter;
    }

   //get functions

    public String getMPoster1() {
        return MPoster1;
    }

    public String getMPoster2() {
        return MPoster2;
    }

    public String getMPoster3() {
        return MPoster3;
    }

    public String getMtitle1() {
        return Mtitle1;
    }

    public String getMtitle2() {
        return Mtitle2;
    }

    public String getMtitle3() {
        return Mtitle3;
    }

    public String getCounter() {
        return Counter;
    }

}
