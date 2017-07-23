package com.androidapp.richard.startfresh.AdaptersAndOtherClasses;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.List;

/**
 * Created by Richard Zhang on 2017-07-22.
 */

public class CustomXAxisLabelsFormatter implements IAxisValueFormatter {

    private List<String> yAxisValues;

    public CustomXAxisLabelsFormatter(List<String> yAxisValues) {
        this.yAxisValues = yAxisValues;
    }
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return yAxisValues.get((int)value);
    }
}
