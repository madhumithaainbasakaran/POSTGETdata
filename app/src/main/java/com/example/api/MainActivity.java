package com.example.api;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText txtName,txtJob;
    private TextView lblOutput;
    private Button btnPostData;

    private Button btnGetData;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtName = findViewById(R.id.txtName);
        txtJob = findViewById(R.id.txtJob);
        lblOutput = findViewById(R.id.lblOutput);
        btnPostData = findViewById(R.id.btnPostData);


        btnPostData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get Data From TextBox
                String strName,strJob;
                strName = txtName.getText().toString();
                strJob = txtJob.getText().toString();

                if(strName == "")
                {
                    Toast.makeText(MainActivity.this,"Please Enter Name",Toast.LENGTH_SHORT).show();
                }
                else if(strJob == "")
                {
                    Toast.makeText(MainActivity.this,"Please Enter Job",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    ApiInterface methods = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
                    Call<User> call = methods.getUserData(strName,strJob);

                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            String strOutput = "";
                            strOutput =  "Id : " + response.body().getId();
                            strOutput = strOutput + "\nName : " + response.body().getName();
                            strOutput = strOutput + "\nJob : " + response.body().getJob();
                            strOutput = strOutput + "\nCreated At : " + response.body().getCreatedAt();
                            lblOutput.setText(strOutput);
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(MainActivity.this,"Error Occurred",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


            btnGetData = findViewById(R.id.btnGetData);
            listView = findViewById(R.id.listviewData);
            btnGetData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ApiInterface methods = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
                    Call < Model > call = methods.getAllData();
                    call.enqueue(new Callback < Model > () {
                        @Override
                        public void onResponse(Call < Model > call, Response < Model > response) {
                            ArrayList< Model.data > data = response.body().getData();
                            String[] names = new String[data.size()];
                            for (int i = 0; i < data.size(); i++) {
                                names[i] = "Id : " + data.get(i).getId() + "\nName : " + data.get(i).getFirst_name() + " " + data.get(i).getLast_name() + "\nEmail : " + data.get(i).getEmail();
                            }
                            listView.setAdapter(new ArrayAdapter< String >(getApplicationContext(), android.R.layout.simple_list_item_1, names));
                        }
                        @Override
                        public void onFailure(Call < Model > call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "An error has occurred", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }
}
