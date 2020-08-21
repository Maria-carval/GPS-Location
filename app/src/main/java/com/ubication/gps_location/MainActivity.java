package com.ubication.gps_location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText NumeroCelular;
    Button boton_GPS;
    TextView Obtenercoordenadas;
    Button boton_Enviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true); //Para mostrar el ícono de la app en el action Bar
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        NumeroCelular = findViewById(R.id.editTextTextPersonName);
        Obtenercoordenadas = (TextView)findViewById(R.id.tvUbicacion);
        boton_GPS = (Button)findViewById(R.id.button);
        b0ton_Enviar = findViewById(R.id.button2);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(etCel.getText().toString(), null, tvUbicacion.getText().toString(), null, null);
            }
        });


        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) !=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }

        btnGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocationManager locationManager = (LocationManager) MainActivity.this.getSystemService(Context.LOCATION_SERVICE);


                LocationListener locationListener = new LocationListener() {
                    public void onLocationChanged(Location location) {
                        
                        Obtenercoordenadas.setText(""+location.getLatitude()+"   "+location.getLongitude());

                    }

                    public void onStatusChanged(String provider, int status, Bundle extras) {}

                    public void onProviderEnabled(String provider) {}

                    public void onProviderDisabled(String provider) {}
                };


                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener); //GPS provider es un método de localización que utiliza Android Studio.
            }
        });

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if(permissionCheck== PackageManager.PERMISSION_DENIED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                
            } else {

               
                ActivityCompat.requestPermissions(this,
                        new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
            }

        }

    }
}
