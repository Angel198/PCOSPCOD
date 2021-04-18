package com.jaylax.pcospcod.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jaylax.pcospcod.R;
import com.jaylax.pcospcod.patientactivities.PatientDashboardActivity;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

public class InquiryFragment extends Fragment {

    EditText fname, lname, shopname, aadhar, et_state, et_city, et_village, et_district, zip, et_street, _contact, email;
    String f_name, l_name, shop_name, aadhar_num, pin_code, select_state, select_district, select_village,street,_email;
    TextView submit;
    NestedScrollView nestedScrollView;
    FrameLayout frameLayout;
    public String SelectedState = null;
    public String SelectedVillage = null;
    public String SelectedDistrict = null;
    String number,contact,n;
    private String[] array_city;
    private String[] array_state;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String MyPREFERENCES_TEMP = "ActivityPREF";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inquiry, container, false);

        ((PatientDashboardActivity)getActivity()).setToolbarTitle("Consult a Doctor");

//        ((PatientDashboardActivity)getActivity()).setToolbarTitle("Cosult a Doctor");

//        getActivity().getActionBar().setTitle("Consult a Doctor");

        submit = (TextView) view.findViewById(R.id.bt_submit);
        fname = (EditText) view.findViewById(R.id.fname);
        lname = (EditText) view.findViewById(R.id.lname);
        et_village = (EditText) view.findViewById(R.id.country);
        et_street = (EditText) view.findViewById(R.id.et_street);
        zip = (EditText) view.findViewById(R.id.pincode);
        _contact = (EditText) view.findViewById(R.id.customer_contact);
        email = (EditText) view.findViewById(R.id.email);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nested);
        frameLayout = (FrameLayout) view.findViewById(R.id.Framelayout);

//        array_state = getResources().getStringArray(R.array.state);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                f_name = fname.getText().toString();
                l_name = lname.getText().toString();
                select_village = et_village.getText().toString();
                pin_code = zip.getText().toString();
                street = et_street.getText().toString();
                number = _contact.getText().toString();
                _email = email.getText().toString();

                if (!f_name.equals("") && !l_name.equals("")  && !number.equals("")){

                    sharedPreferences = getContext().getSharedPreferences(MyPREFERENCES_TEMP, Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putString("i_name",f_name);
                    editor.putString("i_lname",l_name);
                    editor.putString("i_village",select_village);
                    editor.putString("i_pin",pin_code);
                    editor.putString("i_street",street);
                    editor.putString("i_number",number);
                    editor.putString("i_email",_email);
                    editor.apply();
                    editor.commit();

                    ((PatientDashboardActivity)getActivity()).replaceFragment(new InuiryFragmentOne());

                }

                else {


                    if(TextUtils.isEmpty(f_name))
                    {
                        fname.setError("Required");
                    }
                    if (TextUtils.isEmpty(l_name))
                    {
                        lname.setError("Required");
                    }
                    if (TextUtils.isEmpty(_email))
                    {
                        email.setError("Required");
                    }
                    if (TextUtils.isEmpty(number))
                    {
                        _contact.setError("Required");
                    }

                }

            }

        });

        return view;

    }


//    private void showCityChoiceDialog(final EditText bt) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setCancelable(true);
//        builder.setSingleChoiceItems(array_city, -1, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                bt.setTextColor(Color.BLACK);
//                bt.setText(array_city[which]);
//                SelectedDistrict = array_city[which];
//                bt.setError(null);
//            }
//        });
//        builder.show();
//    }


//    private void showStateDialog(final EditText v) {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setTitle("State");
//        builder.setSingleChoiceItems(array_state, -1, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//                v.setText(array_state[i]);
//                SelectedState = array_state[i];
//
//                switch (i)
//                {
//                    case 0:
//                        array_city = getResources().getStringArray(R.array.Andhra_Pradesh);
//                        break;
//                    case 1:
//                        array_city = getResources().getStringArray(R.array.Arunachal_Pradesh);
//                        break;
//                    case 2:
//                        array_city = getResources().getStringArray(R.array.Assam);
//                        break;
//                    case 3:
//                        array_city = getResources().getStringArray(R.array.Bihar);
//                        break;
//                    case 4:
//                        array_city = getResources().getStringArray(R.array.Chhattisgarh);
//                        break;
//                    case 5:
//                        array_city = getResources().getStringArray(R.array.Delhi);
//                        break;
//                    case 6:
//                        array_city = getResources().getStringArray(R.array.Goa);
//                        break;
//                    case 7:
//                        array_city = getResources().getStringArray(R.array.Gujarat);
//                        break;
//                    case 8:
//                        array_city = getResources().getStringArray(R.array.Haryana);
//                        break;
//                    default:
//                        array_city = getResources().getStringArray(R.array.india_top_places);
//                        break;
//                }
//
//            }
//        });
//        builder.show();
//    }

}