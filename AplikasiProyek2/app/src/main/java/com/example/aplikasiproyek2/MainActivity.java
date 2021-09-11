package com.example.aplikasiproyek2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private EditText etUsername;
    private EditText etEmail;
    private EditText etNamaLengkap;
    private EditText etAsalSekolah;
    private EditText etAlamat;

    //local
    EditText etPassword;
    Button btSimpan;
    TextView tvPassword;

    public static final String FILENAME = "login";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // view to object
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar()!=null) getSupportActionBar().setTitle("Halaman Depan");

        etUsername = findViewById(R.id.etUsername);
        tvPassword = findViewById(R.id.tvPassword);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        etNamaLengkap = findViewById(R.id.etNamaLengkap);
        etAsalSekolah = findViewById(R.id.etAsalSekolah);
        etAlamat = findViewById(R.id.etAlamat);
        btSimpan = findViewById(R.id.btSimpan);

        etUsername.setEnabled(false);
        tvPassword.setVisibility(View.GONE);
        etPassword.setVisibility(View.GONE);
        etEmail.setEnabled(false);
        etNamaLengkap.setEnabled(false);
        etAsalSekolah.setEnabled(false);
        etAlamat.setEnabled(false);

        btSimpan.setVisibility(View.GONE);

        bacaFileLogin();
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

    void bacaFileLogin(){
        File sdcard = getFilesDir();
        File file = new File(sdcard, FILENAME);

        if (file.exists()){
            StringBuilder text = new StringBuilder();
            try(BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line = br.readLine();
                while (line !=null){
                    text.append(line);
                    line = br.readLine();
                }
            }catch (IOException e) {
                System.out.println("Error" + e.getMessage());
            }
            String data = text.toString();
            String[] dataUser = data.split(";");
            bacaDataUser(dataUser[0]);
        }
    }

    void bacaDataUser(String fileName){
        File sdcard = getFilesDir();
        File file = new File(sdcard,fileName);

        if (file.exists()){
            StringBuilder text = new StringBuilder();
            try(BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line = br.readLine();
                while (line !=null){
                    text.append(line);
                    line = br.readLine();
                }
            }catch (IOException e) {
                System.out.println("Error" + e.getMessage());
            }
            String data = text.toString();
            String[] dataUser = data.split(";");

            etUsername.setText(dataUser[0]);
            etEmail.setText(dataUser[2]);
            etNamaLengkap.setText(dataUser[3]);
            etAsalSekolah.setText(dataUser[4]);
            etAlamat.setText(dataUser[5]);
        }else
            Toast.makeText(this, "User Tidak Ditemukan", Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }



    void hapusFile(){
        File file = new File(getFilesDir(),FILENAME);

        if (file.exists() && file.delete())
            System.out.println("File Telah Dihapus");
    }

    void showLogOutDialog(){
        new AlertDialog.Builder(this)
                .setTitle("Confirmation Logout")
                .setMessage("Anda Yakin Ingin Logout? ")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("YES",(dialog, which) -> {
                    hapusFile();

                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("NO",null).show();

    }
}