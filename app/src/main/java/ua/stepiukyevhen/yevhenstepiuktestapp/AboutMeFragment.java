package ua.stepiukyevhen.yevhenstepiuktestapp;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class AboutMeFragment extends Fragment implements DatePicker.OnDateChangedListener{

    EditText editName;
    DatePicker datePicker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_about_me, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        findViewsById();
    }

    private void findViewsById() {
        editName = (EditText) getActivity().findViewById(R.id.edit_name);
        datePicker = (DatePicker) getActivity().findViewById(R.id.datePicker);
    }

    @Override
    public void onPause() {
        SharedPreferences.Editor editor = getActivity()
                .getSharedPreferences(MainActivity.PREFS, Context.MODE_PRIVATE).edit();

        editor.putString(MainActivity.NAME, editName.getText().toString());
        editor.apply();

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences prefs = getActivity()
                .getSharedPreferences(MainActivity.PREFS, Context.MODE_PRIVATE);

        String name = prefs.getString(MainActivity.NAME, null);
        int day = prefs.getInt(MainActivity.DAY_OF_MONTH, 0);
        int month = prefs.getInt(MainActivity.MONTH, 0);
        int year = prefs.getInt(MainActivity.YEAR, 0);

        if (name != null) {
            editName.setText(name);
        }
        if (day != 0) {
            datePicker.init(year, month, day, this);
        } else {
            Calendar c = Calendar.getInstance();
            datePicker.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), this);
        }

    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        SharedPreferences.Editor editor = getActivity()
                .getSharedPreferences(MainActivity.PREFS, Context.MODE_PRIVATE).edit();

        editor.putInt(MainActivity.DAY_OF_MONTH, dayOfMonth);
        editor.putInt(MainActivity.MONTH, monthOfYear);
        editor.putInt(MainActivity.YEAR, year);

        editor.apply();
    }
}
