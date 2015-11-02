package com.myon.jukka.tekusahkotracker;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import HttpService.WebService;


/**
 * A placeholder fragment containing a simple view.
 */
public class GraphActivityFragment extends Fragment implements HttpService.WebService.onAsyncRequestComplete{

    ArrayList<BarEntry> Measurements;
    BarChart chart;
    ArrayList<String> xVals;
    ArrayList<BarDataSet> dataSets;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_graph, container, false);
        chart = (BarChart ) rootview.findViewById(R.id.chart);

        String url = "http://codez.savonia.fi/etp4301_2015_r1/Test.php?key=SK1-tekuenergy&Tags=4096&obj=OPI-JKL02&Date=2015-10-31&measuresInHour=12&graphScaleInHours=24&dataPrecision=hour&roundPrecision=2";
        //String url = "http://codez.savonia.fi/etp4301_2015_r1/Test.php?key=SK101-kuopioenergy&Tags=666&Date=2014-9-23&obj=KPOE&measuresInHour=1&graphScaleInHours=24&dataPrecision=hour&roundPrecision=2";
        WebService request = new WebService(GraphActivityFragment.this, "GET");

        Measurements = new ArrayList<BarEntry>();

        dataSets = new ArrayList<BarDataSet>();
        BarDataSet setComp1 = new BarDataSet(Measurements, "Sähkötolpat");
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);

        chart.setAutoScaleMinMaxEnabled(true);
        chart.setDrawValueAboveBar(false);

        xVals = new ArrayList<String>();
        xVals.add("00:00");
        xVals.add("01:00");
        xVals.add("02:00");
        xVals.add("03:00");
        xVals.add("04:00");
        xVals.add("05:00");
        xVals.add("06:00");
        xVals.add("07:00");
        xVals.add("08:00");
        xVals.add("09:00");
        xVals.add("10:00");
        xVals.add("11:00");
        xVals.add("12:00");
        xVals.add("13:00");
        xVals.add("14:00");
        xVals.add("15:00");
        xVals.add("16:00");
        xVals.add("17:00");
        xVals.add("18:00");
        xVals.add("19:00");
        xVals.add("20:00");
        xVals.add("21:00");
        xVals.add("22:00");
        xVals.add("23:00");
        xVals.add("24:00");


        request.execute(url);
        return rootview;
    }

    @Override
    public void HttpResponse(String response) {

        Date xPoint = new Date();
        String yPoint = "";
        try {
            JSONObject reader = new JSONObject(response);
            String xString = reader.getString("Results");

            String[] test = xString.split(",");
            for (String s: test) {
                s = s.replace('{', ' ');
                s =  s.replace('}', ' ');
                s =  s.replace('"', ' ');
                String[] mark = s.split(":");
                Integer intTest;

                if(s.substring(12, 13).equals("0")) {
                    //Log.d("paiva1",s.substring(13, 14) );
                    intTest = Integer.parseInt(s.substring(13, 14));
                }
                else {
                    //Log.d("paiva2",s.substring(12, 14).replace(" ", "") );
                    intTest = Integer.parseInt(s.substring(12, 14).replace(" ", ""));
                }

                //String str = "22:00";
                //SimpleDateFormat ft = new SimpleDateFormat ("HH:mm");

                //xPoint = ft.parse(str);
                yPoint = mark[2];

                BarEntry measure = new BarEntry (Float.parseFloat(yPoint), intTest); //
                Measurements.add(measure);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        BarDataSet setComp1 = new BarDataSet (Measurements, "Sähkötolpat");
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);

        dataSets.add(setComp1);
        BarData data = new BarData (xVals, dataSets);
        chart.setData(data);
        chart.invalidate();

    }
}
