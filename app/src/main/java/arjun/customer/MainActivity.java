package arjun.customer;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.telephony.TelephonyManager;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_CALL_LOG)){
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_CALL_LOG}, 1);
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_CALL_LOG}, 1);
            }
        }
        else{

            //lol
            TextView textView = (TextView) findViewById(R.id.textView);
            textView.setText(getCallDetails());
        }
    }

       @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
           switch (requestCode){
               case 1:{
                   if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                       if(ContextCompat.checkSelfPermission(MainActivity.this,
                               Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED){

                           Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();

                           //lol
                           TextView textView = (TextView) findViewById(R.id.textView);
                           textView.setText(getCallDetails());

                       }
                   } else {
                       Toast.makeText(this, "No permission!", Toast.LENGTH_SHORT).show();
                   }
                   return;
               }
           }

       }





       private String getCallDetails(){
           StringBuffer sb = new StringBuffer();
           Cursor managedCursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
           int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
           int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
           int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
           int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);

           TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
           String imei = telephonyManager.getDeviceId();


           String reqString = Build.MANUFACTURER
                   + " " + Build.MODEL + " " + Build.VERSION.RELEASE
                   + " " + Build.VERSION_CODES.class.getFields()[android.os.Build.VERSION.SDK_INT].getName();




           sb.append( "Device details\n" + "IMEI NUMBER   :" + imei  + "\n"+  "Model" + reqString + "\n\n" + "call details:\n"  );
           while (managedCursor.moveToNext()){

               String phNumber = managedCursor.getString(number);
               String callType = managedCursor.getString(type);
               String callDate = managedCursor.getString(date);
               Date callDayTime = new Date(Long.valueOf(callDate));
               SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy HH:mm");
               String dateString = formatter.format(callDayTime);
               String callDuration = managedCursor.getString(duration);
               String dir = null;
               int dircode = Integer.parseInt(callType);
               switch (dircode){
                   case CallLog.Calls.OUTGOING_TYPE:
                       dir = "OUTGOING";
                       break;
                   case CallLog.Calls.INCOMING_TYPE:
                       dir = "INCOMING";
                       break;
                   case CallLog.Calls.MISSED_TYPE:
                       dir = "MISSED";
                       break;

               }

               sb.append("\nPhone Number: " + phNumber + " \nCallType: " + dir + " \n Call Date " + dateString + "\nCall Duration:" + callDuration);
               sb.append("\n-----------------------------------------");


           }

           managedCursor.close();

           return sb.toString();

       }

}








