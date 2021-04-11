package com.jaylax.pcospcod.patientactivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.jaylax.pcospcod.R;
import com.jaylax.pcospcod.patientadapter.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PatientHomeEightActivity extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home_eight);

        initToolbar();

        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        prepareListData();

        listAdapter = new ExpandableListAdapter(getApplicationContext(), listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);



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


    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Myth: PCOS or PCOD can’t be treated");
        listDataHeader.add("Myth: Exercise can help reduce PCOS and weight gain");
        listDataHeader.add("Myth: Ultrasound is must to diagnose PCOS or PCOD in a female accurately");
        listDataHeader.add("Myth: PCOS prevails till you die. So, you have to learn how to minimize it.");
        listDataHeader.add("Myth: A right natural supplement can help you get rid of PCOS.");
        listDataHeader.add("Myth: A tailored diet plan can help you shed some weight during PCOS or PCOD.");
        listDataHeader.add("Myth: You have unwanted hair growth on your body if you have PCOS.");
        listDataHeader.add("Myth: Birth control pills can help to cope with PCOS effectively.");
        listDataHeader.add("Myth: Women with PCOS naturally gain weight due to insulin resistance.");
        listDataHeader.add("Myth: Hormonal therapy is a “magic bullet” for women with PCOS.");
        listDataHeader.add("Myth: One of the hallmarks of PCOS is the inability to get pregnant.");
        listDataHeader.add("Myth: PCOS is a lifetime illness that requires extensive lifestyle changes and eating habits.");
        listDataHeader.add("Myth: Irregular menstrual cycles are a sure sign one has PCOS.");
        listDataHeader.add("Myth: You don't need to worry about PCOS. It treats itself as you get older.");
        listDataHeader.add("Myth: If I have PCOS, I can safely resort to the following: progestin therapy, birth control pills, Spironolactone (Aldactone), Eflornithine (Vaniqa), Gonadotropins, combination birth control pills, Clomid, Femara or Metformin to resolve my PCOS symptoms or sign up for a series of electrolysis sessions to help with any unwanted hair growth.");

        // Adding child data
        List<String> one = new ArrayList<String>();
        one.add("PCOS or PCOD is difficult to treat but not impossible. In fact, we have helped many women to overcome nearly all symptoms like weight gain, irregular menses, hair loss, hormonal imbalance, etc. They were treated with our patented external treatment within 48 days! \n" +
                "\n");

        List<String> two = new ArrayList<String>();
        two.add("This can be partially true. Exercise can keep your body active and energetic. However, it may not be the only factor that can help you treat PCOS. It stems from endocrine system imbalance which may take time to balance up. An imbalanced endocrine system may hamper the hormones in a female body and eventually lead to slow or negligent weight loss. This is the prime reason many females find it difficult to lose weight even after excessive exercise. \n" +
                "\n");

        List<String> three = new ArrayList<String>();
        three.add("Cystic ovaries do fall under the definite symptom of PCOS. However, it can be diagnosed via ultrasound when a female exhibits certain symptoms like weight gain, irregular periods, and unwanted facial hair, etc. Unfortunately, PCOS remains misdiagnosed since most doctors don’t demand the patient to undergo a full panel of hormonal tests. Simply doing an ultrasound can help only in the case of multiple cysts formation. The above-mentioned symptoms can still be the sign you have PCOS. \n" +
                "\n");

        List<String> four = new ArrayList<String>();
        four.add("At HealVibe, we believe in supporting conventional ways to treat patients. Fortunately, our therapy is highly efficient when it comes to eradicating the PCOS symptoms from the roots. We offer complete assistance and make your lifestyle free from anger, mood swings, depression and other such side effects within 48 days* or even 1152 hours*. PCOS is curable and any woman can live a healthier life by treating it rightly with the help of HealVibe’s home treatment kit.\n" +
                "\n");

        List<String> five = new ArrayList<String>();
        five.add("At most, you can feel better than before but most natural supplements lack the ability to help you maintain your endocrine and hormonal balance. This may only help the person to treat partially but doesn’t eradicate the condition from the very root of it. \n" +
                "\n");

        List<String> six = new ArrayList<String>();
        six.add("A female suffering from PCOS or PCOD could be eating really fewer portions of meals but may be unable to lose weight. Simply focusing on dietary intake may not help a person to lose weight since their endocrine system may be hampered. \n");
        six.add("When a female starts home treatment kit of HealVibe, we focus on maintaining the hormonal balance and help them to maintain it without dieting. Once the hormones are balanced, weight loss happens naturally with minimal efforts. \n" +
                "\n");


        List<String> seven = new ArrayList<String>();
        seven.add("Although this is true in most instances, it is not an absolute sign that doctors follow. Some women with PCOS will not exhibit any signs of excessive and unwanted hair growth.\n" +
                "\n");

        List<String> eight = new ArrayList<String>();
        eight.add("Birth controls pills can help you regularize your cycle only till you consume them. They can provide a temporary solution to your problem of irregular periods. Moreover, they can also have serious side effects if consumed for a longer duration. \n" +
                "\n");

        List<String> nine = new ArrayList<String>();
        nine.add("Many overweight women have insulin resistance, but that is not the causative factor of their weight gain. Rather, it is that the endocrine system is out of sorts. Fixing that can actually make it easier for women to lose weight at a faster pace. \n" +
                "\n");

        List<String> ten = new ArrayList<String>();
        ten.add("Only a tiny fraction of doctors who prescribe hormonal therapy or hormone replacement therapy (HRT) to their patients, will bother explaining the deleterious effects that this therapy has on a woman’s body. For example, research shows that HRT may increase a women’s chance of getting cancer in her later years by as much as 90%! On the contrary, if you take a natural therapy from HealVibe, you can naturally get your hormonal balance in place within nearly 48 days! \n" +
                "\n");

        List<String> eleven = new ArrayList<String>();
        eleven.add("Many people believe that PCOS causes infertility, however, the heart of the matter, again, is that the endocrine system is out of balance, thus the hormonal system is too. The ovaries will not release an egg if there’s an imbalance of hormones, making it impossible for the egg to be fertilized.\n" +
                "\n");

        List<String> twelve = new ArrayList<String>();
        twelve.add("Yes, this is true for most of the treatments out there. However, if a woman with PCOS takes 48 Day therapy of HealVibe for every fourth day for 48 days (16 treatments), no lifestyle changes are required. You don’t have to have: \n" +
                "\n" +
                "Special diet\n" +
                "\n" +
                "Dietary changes\n" +
                "\n" +
                "Exercise\n" +
                "\n" +
                "Hormonal supplements\n" +
                "\n" +
                "Doctor visits\n" +
                "\n" +
                "Visit the costly fertility clinics\n" +
                "All you have to do is follow the simple instructions included in your package and 48 days later, you will know how it feels to be PCOS symptom-free!\n" +
                "\n");

        List<String> thirteen = new ArrayList<String>();
        thirteen.add("They are not. Why? Because there is a myriad of reasons why a cycle could be off, including environmental stress, thyroid issues, and emotional stress. So, this is not an entirely reliable gauge to determine if someone has PCOS or not.\n" +
                "\n");

        List<String> forteen = new ArrayList<String>();
        forteen.add("Yes, a woman might not be diagnosed with PCOS in midlife, but they might find themselves with a host of other illnesses like diabetes, high blood pressure, anxiety disorders and depression, among other disorders. Additionally, if a woman wants to get pregnant later in life, she may be unable to conceive, under normal circumstances. Don’t wait for it to get normal, make it normal to avoid later life complications.\n" +
                "\n");

        List<String> fifteen = new ArrayList<String>();
        fifteen.add("These drugs are not particularly safe to consume and they come with a long list of serious side effects. Also, regular electrolysis sessions are not cheap either.\n");
        fifteen.add("Avoid bothering over it and take 16 treatments of 48 Days* of HealVibe. We can take care of every symptom associated with PCOS in just 48 days* (under normal circumstances), including the above, without any side effects at all.\n" +
                "\n");


        listDataChild.put(listDataHeader.get(0), one); // Header, Child data
        listDataChild.put(listDataHeader.get(1), two);
        listDataChild.put(listDataHeader.get(2), three);
        listDataChild.put(listDataHeader.get(3), four);
        listDataChild.put(listDataHeader.get(4), five);
        listDataChild.put(listDataHeader.get(5), six);
        listDataChild.put(listDataHeader.get(6), seven);
        listDataChild.put(listDataHeader.get(7), eight);
        listDataChild.put(listDataHeader.get(8), nine);
        listDataChild.put(listDataHeader.get(9), ten);
        listDataChild.put(listDataHeader.get(10), eleven);
        listDataChild.put(listDataHeader.get(11), twelve);
        listDataChild.put(listDataHeader.get(12), thirteen);
        listDataChild.put(listDataHeader.get(13), forteen);
        listDataChild.put(listDataHeader.get(14), fifteen);

    }

}