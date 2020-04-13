package example.com.myappattendance.UI;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Locale;

import example.com.myappattendance.Model.MyModelData;
import example.com.myappattendance.R;
import example.com.myappattendance.utils.Constants;
import example.com.myappattendance.utils.OnActivityResultDataChanged;
import example.com.myappattendance.utils.OnScanQrButtonClicked;

import static android.content.ContentValues.TAG;

public class Main_ScanQrcode_Fragment extends Fragment implements OnActivityResultDataChanged {

    // Fragment get data

    Button btn_Scan_Qrcode;
    View v;
    // interface Qrcode
    private OnScanQrButtonClicked mOnScanQrButtonClickedListener;

    // sharePreference (get token)
    private static SharedPreferences sharedPref;
    private static String token;

    //Google location
    FusedLocationProviderClient client;
    Geocoder geocoder;
    public static double la, lo;


    public Main_ScanQrcode_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_main__scan_qrcode, container, false);
        btn_Scan_Qrcode = v.findViewById(R.id.btn_Scan_Qrcode);

        //googlemap
        requestPermissionGooglemap();
        // location
        client = LocationServices.getFusedLocationProviderClient(getActivity());
        getLocationAddress();

        // Share get Token
        sharedPref = getActivity().getSharedPreferences(Constants.MY_PREFS, Context.MODE_PRIVATE);
        return v;
    }

    // request permission google map
    private void requestPermissionGooglemap() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }

    private void getLocationAddress() {
        /*client = LocationServices.getFusedLocationProviderClient(getActivity());*/
        client.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                //addAddress(location);
                /*List<Address> addresses;*/
                Log.d("location", "LOCATION==> " + location);
                geocoder = new Geocoder(getActivity(), Locale.getDefault());
                // Sep Address
                /*addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                address1 = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getLocality();
                String tambon = addresses.get(0).getSubLocality();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                String amphoe = addresses.get(0).getSubAdminArea();*/

                la = location.getLatitude();
                lo = location.getLongitude();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_Scan_Qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // send to callback
                mOnScanQrButtonClickedListener.triggerScanQr();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnScanQrButtonClickedListener = (OnScanQrButtonClicked) getActivity();

            //test send data with callback
            //callbackData = (callbackData) getActivity();

        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnScanQrButtonClickedListener = null;

        //test send data with callback
        //callbackData = null;
    }

    // set data model sent to...
    public static MyModelData myModel() {

        token = sharedPref.getString(Constants.TOKEN, "");
        MyModelData sendDataTosave = new MyModelData();
        sendDataTosave.setLAT(la);
        sendDataTosave.setLON(lo);
        sendDataTosave.setToken(token);

        //  Log.d("check","TOKEN==> "+token);
        return sendDataTosave;
    }

    @Override
    public void onDataReceived(String data) {
        //Toast.makeText(getActivity(),"FM-Scan Data is : "+data,Toast.LENGTH_SHORT).show();
        Log.d("response", "ScanFragment ==> " + data);
    }
}
