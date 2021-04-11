package com.jaylax.pcospcod.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

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


public class PquestionnineFragment extends Fragment {

    FrameLayout frameLayout;
    ListView listView;
    TextView next;
    List<String> symptoms = Arrays.asList(
            "USG (Sonography)",
            "LH",
            "FSH",
            "Acne",
            "Prolactin",
            "Testosterone",
            "DHEA (S)",
            "TSH",
            "AMH",
            "Vitamin D",
            "Fasting Sugar",
            "Fasting Insulin"

    );
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pquestionnine, container, false);

        ((PatientDashboardActivity)getActivity()).setToolbarTitle("Consult a Doctor");

        sharedPreferences = getContext().getSharedPreferences(InquiryFragment.MyPREFERENCES_TEMP, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        frameLayout = (FrameLayout) view.findViewById(R.id.Framelayout);
        listView = (ListView) view.findViewById(R.id.listivew);
        next = (TextView) view.findViewById(R.id.bt_submit);

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

                if (outputStrArr != null)
                {
                    String ar = Arrays.toString(outputStrArr);

                    Log.i("test",ar);
                    editor.putString("i_test_name",ar);
                    editor.apply();
                    editor.commit();

                    ((PatientDashboardActivity)getActivity()).replaceFragment(new ChooseDoctorFragment());


                }



            }
        });

        return view;

    }

}