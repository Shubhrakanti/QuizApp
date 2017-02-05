package com.example.giddu.quizapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    RadioGroup question1Group;
    RadioGroup question2Group;
    RadioGroup question4Group;
    RadioGroup question5Group;

    CheckBox question3option1;
    CheckBox question3option2;
    CheckBox question3option3;
    CheckBox question3option4;

    EditText answer6;

    boolean isQuestion1correct;
    boolean isQuestion2correct;
    boolean isQuestion3correct;
    boolean isQuestion4correct;
    boolean isQuestion5correct;

    TextView results;
    Button email;

    int numCorrect;
    String lang;

    RadioGroup languageSelector;
    RadioButton radioButtonEn;
    RadioButton radioButtonSp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        question1Group = (RadioGroup) findViewById(R.id.question1group);
        question2Group = (RadioGroup) findViewById(R.id.question2group);
        question4Group = (RadioGroup) findViewById(R.id.question4group);
        question5Group = (RadioGroup) findViewById(R.id.question5group);

        question3option1 = (CheckBox) findViewById(R.id.question3option1);
        question3option2 = (CheckBox) findViewById(R.id.question3option2);
        question3option3 = (CheckBox) findViewById(R.id.question3option3);
        question3option4 = (CheckBox) findViewById(R.id.question3option4);

        answer6 = (EditText) findViewById(R.id.answer6);

        results= (TextView) findViewById(R.id.resultText);
        email= (Button) findViewById(R.id.emailButton);

        radioButtonEn = (RadioButton) findViewById(R.id.english);
        radioButtonSp = (RadioButton) findViewById(R.id.spanish);

        numCorrect = 0;
        lang = "en";

        Intent intent = getIntent();
        String languangeIntent = intent.getStringExtra("lang");
        String language = "en"; //default
        if(languangeIntent != null){
            language = languangeIntent;
        }

        if(language.equals("en")){
            radioButtonEn.setChecked(true);
        }else if(language.equals("es")){
            radioButtonSp.setChecked(true);
        }

        languageSelector = (RadioGroup) findViewById(R.id.languageSelector);
        languageSelector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId == R.id.english){
                    lang = "en";
                }else if(checkedId == R.id.spanish){
                    lang = "es";
                }

                Locale myLocale = new Locale(lang);
                Resources res = getResources();
                DisplayMetrics dm = res.getDisplayMetrics();
                Configuration conf = res.getConfiguration();
                conf.locale= myLocale;
                res.updateConfiguration(conf, dm);

                // refresh the activity using an intent
                Intent i = new Intent(MainActivity.this, MainActivity.class);
                if(lang.equals("en")){
                    i.putExtra("lang","en");
                }else if(lang.equals("es")){
                    i.putExtra("lang","es");
                }
                startActivity(i);
                finish();

            }


        });

    }

    public void submitClick(View view) {

        //check answer for question 1

        numCorrect = 0;

        if(question1Group.getCheckedRadioButtonId() == R.id.question1option1){
            isQuestion1correct = true;
            numCorrect++;
        }
        else {
            isQuestion1correct = false;
        }

        //check answer for question 2

        if(question2Group.getCheckedRadioButtonId() == R.id.question2option2){
            isQuestion2correct = true;
            numCorrect++;
        }
        else {
            isQuestion2correct = false;
        }

        //check answer for question 3

        if(!question3option1.isChecked() &&
                question3option2.isChecked() &&
                question3option3.isChecked() &&
                !question3option4.isChecked()){
            isQuestion3correct = true;
            numCorrect++;
        }
        else {
            isQuestion3correct = false;
        }

        //check answer for question 4

        if(question4Group.getCheckedRadioButtonId() == R.id.question4option4){
            isQuestion4correct = true;
            numCorrect++;
        }
        else {
            isQuestion4correct = false;
        }



        //check answer for question 5

        if(question5Group.getCheckedRadioButtonId() == R.id.question5option3){
            isQuestion5correct = true;
            numCorrect++;
        }
        else {
            isQuestion5correct = false;
        }

        //check answer 6

        if (answer6.getText().toString().equals("2")){
            numCorrect++;
            Log.d("THIS IS SPARTA", answer6.getText().toString());
        }

        Context context = getApplicationContext();
        CharSequence text = "You got "+numCorrect+" correct!";
        int duration = Toast.LENGTH_SHORT;



        results.setText("You got "+numCorrect+" correct!");
        //how can I put that value in strings.xml so I can translate it???
        results.setVisibility(View.VISIBLE);
        email.setVisibility(View.VISIBLE);
        email.requestFocus();

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }

    public void setEmail(View view){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, "teacher@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Test Results");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        //I don't have an android device so I could not test this feature
        //please let me know whether or not this works
    }


}
