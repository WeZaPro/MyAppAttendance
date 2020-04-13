package example.com.myappattendance.UI;

import androidx.appcompat.app.AppCompatActivity;
import example.com.myappattendance.Model.MyModelData;
import example.com.myappattendance.R;
import example.com.myappattendance.utils.OnActivityResultDataChanged;
import example.com.myappattendance.utils.OnScanQrButtonClicked;
import example.com.myappattendance.utils.callbackData;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import static example.com.myappattendance.UI.Main_ScanQrcode_Fragment.myModel;

// Todo - 1 = Create Fragment ==>OK
// - Main_ScanQrcode_Fragment
// - Second_Showdata_Fragment
// Set data
// Todo - 2 = Scan Qr code ==>OK
// Todo - 3 = get Location ==>OK
// Todo - 4 = get Token ==>OK
// save data to DB ==> PHP
// Todo - 5 = Create Php GET/POST + save to db
// send data to PHP ==> ANDROID
// Todo - 6 = send data
// Todo - 7 = Show data

public class MainActivity extends AppCompatActivity implements OnScanQrButtonClicked, callbackData {

    //callback qr code
    //Callback
    //test hide
    public static OnActivityResultDataChanged mOnActivityResultDataChanged;

    //get data qr code;
    String dataQrcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Main_ScanQrcode_Fragment main_scanQrcode_fragment = new Main_ScanQrcode_Fragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contentContainer, main_scanQrcode_fragment)
                    //.addToBackStack("")
                    .commit();
        }
    }

    // scan qr code
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "QR Code Scan Cancelled", Toast.LENGTH_SHORT).show();

            }
            {
                gotoShowFragment(result.getContents(), myModel());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void gotoShowFragment(String contents, MyModelData myModel) {
        //Toast.makeText(this, "Goto  getDataFragment*************: "+contents,Toast.LENGTH_SHORT).show();
        Second_Showdata_Fragment second_showdata_fragment = new Second_Showdata_Fragment();
        Bundle b = new Bundle();
        b.putString("QRCODE", contents);
        b.putDouble("LAT", myModel.getLAT());
        b.putDouble("LON", myModel.getLON());
        b.putString("TOKEN", myModel.getToken());
        second_showdata_fragment.setArguments(b);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contentContainer, second_showdata_fragment)
                .addToBackStack("")
                .commitAllowingStateLoss();

        Log.d("check", "QRCODE-Main->ShowFragment ==>" + contents);
        Log.d("check", "LAT-Main->ShowFragment ==>" + myModel.getLAT());
        Log.d("check", "LON-Main->ShowFragment ==>" + myModel.getLON());
        Log.d("check", "TOKEN-Main->ShowFragment ==>" + myModel.getToken());
    }

    @Override
    public void triggerScanQr() {
        System.out.println("Fragment Just Triggered MainActivity");
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(AnyOrientationCaptureActivity.class);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt("Scan something");
        integrator.setCameraId(0);
        integrator.setOrientationLocked(false);
        integrator.setBeepEnabled(false);
        integrator.initiateScan();
    }

    // callback get data from Main_ScanQrCodeFragment
    @Override
    public void listenData(String token, double lat, double lon) {
        MyModelData myModelData = new MyModelData();
        myModelData.setToken(token);
        myModelData.setLAT(lat);
        myModelData.setLON(lon);
    }
}
