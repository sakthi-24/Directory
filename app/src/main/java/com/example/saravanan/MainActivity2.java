package com.example.saravanan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity2 extends AppCompatActivity {

    TextView tvname,tvemail,tvdepartment,tvbatch,tvcountry;
    ImageView Image;
    int position;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tvname=findViewById(R.id.NameValue);
        tvemail=findViewById(R.id.EmailValue);
        tvdepartment=findViewById(R.id.DepartmentValue);
        tvcountry=findViewById(R.id.CountryValue);
        tvbatch=findViewById(R.id.BatchValue);

        Image=findViewById(R.id.image);


        Intent intent =getIntent();
        position=intent.getExtras().getInt("position");

        tvname.setText(MainActivity.SubjectList.get(position).getName());
        tvemail.setText(MainActivity.SubjectList.get(position).getEmail());
        tvdepartment.setText(MainActivity.SubjectList.get(position).getDepartment());
        tvbatch.setText(MainActivity.SubjectList.get(position).getBatch());
        tvcountry.setText(MainActivity.SubjectList.get(position).getCountry());
        Glide.with(Image.getContext()).load(MainActivity.SubjectList.get(position).getImage()).into(Image);

    }
}