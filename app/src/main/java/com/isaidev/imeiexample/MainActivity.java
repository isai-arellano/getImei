package com.isaidev.imeiexample;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    Button obtenerImei;
    String imei;

    static final Integer PHONESTATS = 0x1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Revisamos si el app ya tiene permisos
        pedirPermisos(Manifest.permission.READ_PHONE_STATE, PHONESTATS);

        obtenerImei= (Button)findViewById(R.id.btnObtenerImei);


        obtenerImei.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                obtenerIdentificador();
            }
        });
    }


    // Pedir permisos a usuario
    private void pedirPermisos(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission)) {

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
            }
        } else {
            Toast.makeText(this,permission + " El permiso a la aplicación esta concedido.", Toast.LENGTH_SHORT).show();
        }
    }



    // Resultado de permisos
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode) {
            case 1: {

                // Validacion para ver si el usuario acepto los permisos en el onRequest
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    obtenerIdentificador();

                } else {

                    Toast.makeText(MainActivity.this, "Has negado el permiso a la aplicación", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


    //Obtenemos IDENTIFICADOR

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String obtenerIdentificador() {
        final TelephonyManager telephonyManager= (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&  Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            // versiones con android 6 a 9
            TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            String respuesta = tm.getImei();
            Toast.makeText(MainActivity.this, "Es version superior a 6 e inferior a 9 " + "El IMEI ES: " + respuesta , Toast.LENGTH_SHORT).show();
        }else if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            // versiones con android 10 arriba
            String respuesta = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            Toast.makeText(MainActivity.this, "Es version superior a 9 " + "El ANDROID_ID ES: " + respuesta , Toast.LENGTH_SHORT).show();
        }
        else{
            // versiones con android 6 a 9
            TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            String respuesta = tm.getImei();
            Toast.makeText(MainActivity.this, "Es inferior a 6 " + "El IMEI ES: " + respuesta , Toast.LENGTH_SHORT).show();
        }
        return null;
    }


}


