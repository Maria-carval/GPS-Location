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
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    EditText NumeroCelular;
    Button boton_GPS;
    TextView Obtenercoordenadas;
    Button boton_Enviar;
    TextView Obtenertiempo;
    TextView Obtenerhora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true); //Para mostrar el ícono de la app en el action Bar
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        NumeroCelular = findViewById(R.id.editTextTextPersonName);
        Obtenercoordenadas = (TextView)findViewById(R.id.tvUbicacion);
        boton_GPS = (Button)findViewById(R.id.button);
        boton_Enviar = findViewById(R.id.button2);
        Obtenertiempo = (TextView) findViewById(R.id.texttime);
        Obtenerhora = (TextView) findViewById(R.id.texthora);

        boton_Enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(NumeroCelular.getText().toString(), null, Obtenercoordenadas.getText().toString(), null, null);
                smsManager.sendTextMessage(NumeroCelular.getText().toString(), null, Obtenertiempo.getText().toString(),null, null);
                smsManager.sendTextMessage(NumeroCelular.getText().toString(), null, Obtenerhora.getText().toString(),null, null);
            }
        });


        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) !=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }

        boton_GPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocationManager locationManager = (LocationManager) MainActivity.this.getSystemService(Context.LOCATION_SERVICE);


                LocationListener locationListener = new LocationListener() {
                    public void onLocationChanged(Location location) {
                        
                        Obtenercoordenadas.setText(""+location.getLatitude()+"   "+location.getLongitude());
                        Calendar tiempo = Calendar.getInstance();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                        String dataTime = simpleDateFormat.format(tiempo.getTime());
                        Obtenerhora.setText(dataTime);

                        Obtenercoordenadas.setText(""+location.getLatitude()+"   "+location.getLongitude());
                        Calendar fecha = Calendar.getInstance();
                        SimpleDateFormat simpleDate = new SimpleDateFormat("dd-MMM-yyyy");
                        String dataDate = simpleDate.format(fecha.getTime());
                        Obtenertiempo.setText(dataDate);

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
