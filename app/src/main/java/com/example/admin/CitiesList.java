package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class CitiesList extends AppCompatActivity {

    private ListView lv;
    private ArrayList<String> cities;
    // Listview Adapter
    ArrayAdapter<String> adapter;
    ArrayList<HashMap<String, String>> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities_list);
        cities=new ArrayList<>();
//        setTitle("Select City");
        lv = (ListView) findViewById(R.id.list_view);
        DatabaseReference myRef= FirebaseDatabase.getInstance().getReference("garbage");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    for(DataSnapshot data: dataSnapshot.getChildren()){
                        String city=data.getKey();
                        cities.add(city);
                    }
                    if(cities.size()>0){
                        adapter = new ArrayAdapter<String>(CitiesList.this, R.layout.list_item, R.id.product_name, cities);
                        lv.setAdapter(adapter);
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String city=cities.get(i);
                                Intent intent=new Intent(CitiesList.this,Junk.class);
                                intent.putExtra("City",city);
                                startActivity(intent);
                            }
                        });
                    }
                }else{
                    Toast.makeText(CitiesList.this,"No junk yet!",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
