package com.example.saravanan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.security.auth.Subject;

public class MainActivity extends AppCompatActivity {


    ListView listView;
    EditText editText;
    public static ArrayList<User> SubjectList = new ArrayList<User>();
    String HttpURL = "http://192.168.43.238/test/getcontacts.php";
    ListAdapter listAdapter;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView1);

        editText = (EditText) findViewById(R.id.edittext1);

        progressBar = (ProgressBar)findViewById(R.id.progressbar);

        listView.setTextFilterEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               AlertDialog.Builder builder =new AlertDialog.Builder(view.getContext());
                ProgressDialog progressDialog=new ProgressDialog(view.getContext());

                User ListViewClickData = (User)parent.getItemAtPosition(position);
                CharSequence[] dialogItem ={"View Profile"};
                builder.setTitle(ListViewClickData.getName());
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch(which){
                            case 0:
                                startActivity(new Intent(getApplicationContext(),MainActivity2.class).putExtra("position",position));
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });

        editText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence stringVar, int start, int before, int count) {

                listAdapter.getFilter().filter(stringVar.toString());
            }
        });

        new ParseJSonDataClass(this).execute();

    }

    private class ParseJSonDataClass extends AsyncTask<Void, Void, Void> {
        public Context context;
        String FinalJSonResult;

        public ParseJSonDataClass(Context context) {

            this.context = context;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpServiceClass httpServiceClass = new HttpServiceClass(HttpURL);

            try {
                httpServiceClass.ExecutePostRequest();

                if (httpServiceClass.getResponseCode() == 200) {

                    FinalJSonResult = httpServiceClass.getResponse();

                    if (FinalJSonResult != null) {

                        JSONArray jsonArray = null;
                        try {

                            jsonArray = new JSONArray(FinalJSonResult);

                            JSONObject jsonObject;

                            User user;

                            SubjectList = new ArrayList<User>();

                            for (int i = 0; i < jsonArray.length(); i++) {

                                jsonObject = jsonArray.getJSONObject(i);

                                String tempName = jsonObject.getString("Name").toString();

                                String tempEmail = jsonObject.getString("Email").toString();

                                String tempDepartment = jsonObject.getString("Department").toString();

                                String tempBatch = jsonObject.getString("Batch").toString();

                                String tempCountry = jsonObject.getString("Country").toString();

                                String tempImage = jsonObject.getString("Image").toString();

                                user = new User(tempName,tempEmail,tempDepartment,tempBatch,tempCountry,tempImage);

                                SubjectList.add(user);
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                } else {

                    Toast.makeText(context, httpServiceClass.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)

        {
            progressBar.setVisibility(View.INVISIBLE);
            listAdapter = new ListAdapter(MainActivity.this, R.layout.custom_layout, SubjectList);
            listView.setAdapter(listAdapter);
        }
    }

}

