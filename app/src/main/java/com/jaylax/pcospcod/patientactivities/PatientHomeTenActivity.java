package com.jaylax.pcospcod.patientactivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jaylax.pcospcod.R;

public class PatientHomeTenActivity extends AppCompatActivity {

    EditText value_LH, value_FSH, value_AMH;
    String v_fh, v_lh, v_amh, value;
    float val_one, val_two, val_three;
    Button show, show_amh;
    RadioGroup radioGroup;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home_ten);

        initToolbar();

        value_FSH = (EditText) findViewById(R.id.value_FSH);
        value_LH = (EditText) findViewById(R.id.value_LH);
        show = (Button) findViewById(R.id.bt_show);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group1);
        value_AMH = (EditText) findViewById(R.id.value_AMH);
        show_amh = (Button) findViewById(R.id.bt_show_AMH);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v_fh = value_FSH.getText().toString();
                v_lh = value_LH.getText().toString();

                val_one = Float.parseFloat(v_fh);
                val_two = Float.parseFloat(v_lh);


                if (val_two > val_one)
                {
                    final Dialog dialog = new Dialog(PatientHomeTenActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                    dialog.setContentView(R.layout.dialog_info_analysis);
                    dialog.setCancelable(true);

                    ImageView icon = dialog.findViewById(R.id.icon_only);
                    TextView textView = dialog.findViewById(R.id.text);

                    textView.setText("You have PCOS");

                    icon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();

                        }
                    });

                    dialog.show();
                }
                else {
                    final Dialog dialog = new Dialog(PatientHomeTenActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                    dialog.setContentView(R.layout.dialog_info_analysis);
                    dialog.setCancelable(true);

                    ImageView icon = dialog.findViewById(R.id.icon_only);
                    TextView textView = dialog.findViewById(R.id.text);

                    textView.setText("You have PCOD");

                    icon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();

                        }
                    });

                    dialog.show();
                }
            }
        });


        show_amh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v_amh = value_AMH.getText().toString();
                int selectedID = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedID);

                value = radioButton.getText().toString();
                val_three = Float.parseFloat(v_amh);

                if (val_three >= 3.50 && value.equals("Yes"))
                {
                    final Dialog dialog = new Dialog(PatientHomeTenActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                    dialog.setContentView(R.layout.dialog_info_analysis);
                    dialog.setCancelable(true);

                    ImageView icon = dialog.findViewById(R.id.icon_only);
                    TextView textView = dialog.findViewById(R.id.text);

                    textView.setText("Your AMH Level is more");

                    icon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();

                        }
                    });

                    dialog.show();
                }

            }
        });

    }

    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home )
        {
            finish();
        }

        return super.onOptionsItemSelected(item);


    }
}