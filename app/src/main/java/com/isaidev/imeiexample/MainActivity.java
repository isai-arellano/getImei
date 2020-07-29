package com.isaidev.imeiexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
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
            @Override
            public void onClick(View view) {
              String  respuesta = obtenerIMEI();
                Toast.makeText(MainActivity.this, "El imei es : " + respuesta, Toast.LENGTH_SHORT).show();

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
            imei = obtenerIMEI();
            Toast.makeText(this,permission + " El permiso a la aplicación esta concedido.", Toast.LENGTH_SHORT).show();
        }
    }



    // Resultado de permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode) {
            case 1: {

                // Validacion para ver si el usuario acepto los permisos en el onRequest
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    imei = obtenerIMEI();

                } else {

                    Toast.makeText(MainActivity.this, "Has negado el permiso a la aplicación", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


    //Obtenemos IMEI

    private String obtenerIMEI() {
        final TelephonyManager telephonyManager= (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Hacemos la validación de métodos, para versiones anteriores de androids
            return telephonyManager.getImei();
        }
        else {
            return telephonyManager.getDeviceId();
        }

    }

}


