package com.example.a207_1.pwebchat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText etNomorHp;
    Button btLogin, btRegister;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userRef = database.getReference("users");

    SharedPreferences mylocaldata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etNomorHp = (EditText)findViewById(R.id.etNomorHp);
        btLogin = (Button)findViewById(R.id.btLogin);
        btRegister = (Button)findViewById(R.id.btRegister);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nomorhp = etNomorHp.getText().toString();

                userRef.child(nomorhp).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mylocaldata = getSharedPreferences("mylocaldata", MODE_PRIVATE);
                        User user = new User();
                        if(dataSnapshot.exists()){
                            user.setNama(
                                    dataSnapshot.child("nama").getValue(String.class));
                            user.setEmail(
                                    dataSnapshot.child("email").getValue(String.class));
                            user.setTelepon(
                                    dataSnapshot.child("telepon").getValue(String.class));

                            SharedPreferences.Editor editor = mylocaldata.edit();
                            editor.putString("uid", user.getTelepon());
                            editor.apply();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,
                        RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}
