package com.jaylax.pcospcod.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jaylax.pcospcod.patientactivities.PatientDashboardActivity;
import com.jaylax.pcospcod.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class InuiryFragmentOne extends Fragment {

    NestedScrollView nestedScrollView;
    FrameLayout frameLayout;
    ListView listView;
    TextView next;
    String name;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    List<String> symptoms = Arrays.asList(
            "Irregular Periods",
            "Heavy Bleeding",
            "Unable to conceive",
            "Acne",
            "Weight Gain",
            "Unable to lose weight",
            "Mood Swings",
            "Sudden Anger",
            "Facial Hair (Hirsutism)",
            "Unwanted Hair on Body",
            "Memory Loss",
            "Unable to Focus or Concentrate on anything",
            "Sleep Disorder",
            "Depression",
            "Thinning Hair or Hair loss",
            "Darken Skin"
    );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inuiry_one, container, false);

        ((PatientDashboardActivity)getActivity()).setToolbarTitle("Consult a Doctor");

        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nested);
        frameLayout = (FrameLayout) view.findViewById(R.id.Framelayout);
        listView = (ListView) view.findViewById(R.id.listivew);
        next = (TextView) view.findViewById(R.id.bt_submit);

        sharedPreferences = getContext().getSharedPreferences(InquiryFragment.MyPREFERENCES_TEMP, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        name = sharedPreferences.getString("i_name",null);

        Log.i("name",name);

        ArrayAdapter<String> adapter = new ArrayAdapter(
                getContext(), android.R.layout.simple_list_item_multiple_choice, symptoms);

        listView.setAdapter(adapter);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray checked = listView.getCheckedItemPositions();
                ArrayList<String> selectedItems = new ArrayList<String>();
                for (int i = 0; i < checked.size(); i++) {
                    // Item position in adapter
                    int position = checked.keyAt(i);
                    // Add sport if it is checked i.e.) == TRUE!
                    if (checked.valueAt(i))
                        selectedItems.add(adapter.getItem(position));
                }

                String[] outputStrArr = new String[selectedItems.size()];

                for (int i = 0; i < selectedItems.size(); i++) {
                    outputStrArr[i] = selectedItems.get(i);
                }

                Log.i("ss",Arrays.toString(outputStrArr));

                if (outputStrArr != null)
                {

                    String an = Arrays.toString(outputStrArr);
                    Log.i("an",an);
                    editor.putString("i_symptoms",an);
                    editor.apply();
                    editor.commit();

                    ((PatientDashboardActivity)getActivity()).replaceFragment(new InquirythreeFragment());

                }


            }
        });

        return view;
    }
}