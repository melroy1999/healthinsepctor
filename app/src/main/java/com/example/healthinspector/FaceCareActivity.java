package com.example.healthinspector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class FaceCareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_care);

        final ListView list = findViewById(R.id.list);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Face Wash");
        arrayList.add("Face Scrub");
        arrayList.add("Face Cream");
        arrayList.add("Face Mask");
        arrayList.add("Face Serum");
        arrayList.add("Face Oil");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        list.setAdapter(arrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem=(String) list.getItemAtPosition(position);
                Intent intent = new Intent(FaceCareActivity.this,ClickpictureActivity.class);
                startActivity(intent);
            }
        });
    }
}