package com.example.earthquake;

public class Word {
    private double ms1;
    private  String ms2;
    private  long ms3;
    private String murl;
   public Word(double s1,String s2,long s3,String url)
    {
        ms1=s1;
        ms2=s2;
        ms3=s3;
        murl=url;
    }

    public double getMs1() {
        return ms1;
    }

    public String getMs2() {
        return ms2;
    }

    public long getMs3() {
        return ms3;
    }
    public String getMurl(){return murl;}
}
