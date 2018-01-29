package com.example.user.myapplication.activities;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.user.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.BubbleChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.BubbleChartData;
import lecho.lib.hellocharts.model.BubbleValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.BubbleChartView;
import lecho.lib.hellocharts.view.Chart;

public class BubbleChartActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bubble_chart);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
        }
    }

    /**
     * A fragment containing a bubble chart.
     */
    public static class PlaceholderFragment extends Fragment {

        private static final int BUBBLES_NUM = 8;
        final String ip = "192.168.43.123";
        private BubbleChartView chart;
        private BubbleChartData data;
        private boolean hasAxes = true;
        private boolean hasAxesNames = true;
        private ValueShape shape = ValueShape.CIRCLE;
        private boolean hasLabels = false;
        private boolean hasLabelForSelected = false;
        public static final int COLOR_VIOLET = Color.parseColor("#AA66CC");

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            setHasOptionsMenu(true);
            View rootView = inflater.inflate(R.layout.fragment_bubble_chart, container, false);

            chart = (BubbleChartView) rootView.findViewById(R.id.chart);
            chart.setOnValueTouchListener(new ValueTouchListener());

            generateData();

            return rootView;
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.bubble_chart, menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.action_reset) {
                reset();
                generateData();
                return true;
            }
            if (id == R.id.action_shape_circles) {
                setCircles();
                return true;
            }
            if (id == R.id.action_shape_square) {
                setSquares();
                return true;
            }
            if (id == R.id.action_toggle_labels) {
                toggleLabels();
                return true;
            }
            if (id == R.id.action_toggle_axes) {
                toggleAxes();
                return true;
            }
            if (id == R.id.action_toggle_axes_names) {
                toggleAxesNames();
                return true;
            }
            if (id == R.id.action_animate) {
                prepareDataAnimation();
                chart.startDataAnimation();
                return true;
            }
            if (id == R.id.action_toggle_selection_mode) {
                toggleLabelForSelected();
                Toast.makeText(getActivity(),
                        "Selection mode set to " + chart.isValueSelectionEnabled() + " select any point.",
                        Toast.LENGTH_SHORT).show();
                return true;
            }
            if (id == R.id.action_toggle_touch_zoom) {
                chart.setZoomEnabled(!chart.isZoomEnabled());
                Toast.makeText(getActivity(), "IsZoomEnabled " + chart.isZoomEnabled(), Toast.LENGTH_SHORT).show();
                return true;
            }
            if (id == R.id.action_zoom_both) {
                chart.setZoomType(ZoomType.HORIZONTAL_AND_VERTICAL);
                return true;
            }
            if (id == R.id.action_zoom_horizontal) {
                chart.setZoomType(ZoomType.HORIZONTAL);
                return true;
            }
            if (id == R.id.action_zoom_vertical) {
                chart.setZoomType(ZoomType.VERTICAL);
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        private void reset() {
            hasAxes = true;
            hasAxesNames = true;
            shape = ValueShape.CIRCLE;
            hasLabels = false;
            hasLabelForSelected = false;

            chart.setValueSelectionEnabled(hasLabelForSelected);
        }

        private void generateData() {
            //Some url endpoint that you may have
            String getdevice_Url = "http://"+ip+":3000/api/getTempFromDeviceName/jai";
            //String to place our getdevice_result in
            String getdevice_result = null;
            //Instantiate new instance of our class
            RestOperation getRequest = new RestOperation();
            //Perform the doInBackground method, passing in our url
            try {
                getdevice_result = getRequest.execute("GET",getdevice_Url).get();
                //Log.e("CHECK",getdevice_result);
                Log.e("CHART-RESTAPI","Result:"+getdevice_result.substring(1,getdevice_result.length()-1));
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.e("CHART-RESTAPI","Result:"+e);
            } catch (ExecutionException e) {
                e.printStackTrace();
                Log.e("CHART-RESTAPI","Result:"+e);
            }

            try {
                JSONArray json_array = new JSONArray(getdevice_result);

                for (int i = 0; i < json_array.length(); i++) {
                    JSONObject device = json_array.getJSONObject(i);
                    String temp = device.getString("Temp");
                    Log.e("CHART-RESTAPI","Result:"+i+","+temp);
                }

                List<BubbleValue> values = new ArrayList<BubbleValue>();
                for (int i = 0; i < json_array.length(); ++i) {
                    JSONObject device = json_array.getJSONObject(i);
                    String temp = device.getString("Temp");
                    BubbleValue value = new BubbleValue(i, Integer.parseInt(temp), 1000);
                    value.setColor(COLOR_VIOLET);
                    value.setShape(shape);
                    values.add(value);
                }

                data = new BubbleChartData(values);
                data.setHasLabels(hasLabels);
                data.setHasLabelsOnlyForSelected(hasLabelForSelected);


                if (hasAxes) {
                    Axis axisX = new Axis();
                    Axis axisY = new Axis().setHasLines(true);
                    if (hasAxesNames) {
                        axisX.setName("Axis X");
                        axisY.setName("Axis Y");
                    }
                    data.setAxisXBottom(axisX);
                    data.setAxisYLeft(axisY);
                } else {
                    data.setAxisXBottom(null);
                    data.setAxisYLeft(null);
                }

                chart.setBubbleChartData(data);

            }
            catch (JSONException e) {
                e.printStackTrace();
            }
//
//            List<BubbleValue> values = new ArrayList<BubbleValue>();
//            for (int i = 0; i < BUBBLES_NUM; ++i) {
//                BubbleValue value = new BubbleValue(i, (float) Math.random() * 100, 1);
//                value.setColor(ChartUtils.pickColor());
//                value.setShape(shape);
//                values.add(value);
//            }

//            data = new BubbleChartData(values);
//            data.setHasLabels(hasLabels);
//            data.setHasLabelsOnlyForSelected(hasLabelForSelected);

//            if (hasAxes) {
//                Axis axisX = new Axis();
//                Axis axisY = new Axis().setHasLines(true);
//                if (hasAxesNames) {
//                    axisX.setName("Axis X");
//                    axisY.setName("Axis Y");
//                }
//                data.setAxisXBottom(axisX);
//                data.setAxisYLeft(axisY);
//            } else {
//                data.setAxisXBottom(null);
//                data.setAxisYLeft(null);
//            }
//
//            chart.setBubbleChartData(data);

        }

        private void setCircles() {
            shape = ValueShape.CIRCLE;
            generateData();
        }

        private void setSquares() {
            shape = ValueShape.SQUARE;
            generateData();
        }

        private void toggleLabels() {
            hasLabels = !hasLabels;

            if (hasLabels) {
                hasLabelForSelected = false;
                chart.setValueSelectionEnabled(hasLabelForSelected);
            }

            generateData();
        }

        private void toggleLabelForSelected() {
            hasLabelForSelected = !hasLabelForSelected;

            chart.setValueSelectionEnabled(hasLabelForSelected);

            if (hasLabelForSelected) {
                hasLabels = false;
            }

            generateData();
        }

        private void toggleAxes() {
            hasAxes = !hasAxes;

            generateData();
        }

        private void toggleAxesNames() {
            hasAxesNames = !hasAxesNames;

            generateData();
        }

        /**
         * To animate values you have to change targets values and then call {@link Chart#startDataAnimation()}
         * method(don't confuse with View.animate()).
         */
        private void prepareDataAnimation() {
            for (BubbleValue value : data.getValues()) {
                value.setTarget(value.getX() + (float) Math.random() * 4 * getSign(), (float) Math.random() * 100,
                        (float) Math.random() * 1000);
            }
        }

        private int getSign() {
            int[] sign = new int[]{-1, 1};
            return sign[Math.round((float) Math.random())];
        }

        private class ValueTouchListener implements BubbleChartOnValueSelectListener {

            @Override
            public void onValueSelected(int bubbleIndex, BubbleValue value) {
                Toast.makeText(getActivity(), "Selected: " + value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onValueDeselected() {
                // TODO Auto-generated method stub

            }
        }
    }
}