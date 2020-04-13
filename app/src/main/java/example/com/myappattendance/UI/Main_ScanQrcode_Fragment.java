package example.com.myappattendance.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import example.com.myappattendance.Model.MyModelData;
import example.com.myappattendance.R;
import example.com.myappattendance.utils.Backable;
import example.com.myappattendance.utils.OnActivityResultDataChanged;
import example.com.myappattendance.utils.OnScanQrButtonClicked;
import example.com.myappattendance.utils.callbackData;

import static android.content.ContentValues.TAG;

public class Main_ScanQrcode_Fragment extends Fragment implements OnActivityResultDataChanged {

    Button btn_Scan_Qrcode;
    View v;
    // interface Qrcode
    private OnScanQrButtonClicked mOnScanQrButtonClickedListener;
    //test send data with callback
    //callbackData callbackData;


    public Main_ScanQrcode_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_main__scan_qrcode, container, false);
        btn_Scan_Qrcode = v.findViewById(R.id.btn_Scan_Qrcode);

        //callback Scan qr code
        //Test Hide
        /*MainActivity.setOnActivityResultDataChanged((this));*/
        return v;
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
    public static MyModelData myModel(){
        MyModelData sendDataTosave = new MyModelData();
        sendDataTosave.setLAT(100.00);
        sendDataTosave.setLON(200.00);
        sendDataTosave.setToken("TOKEN");
        return sendDataTosave;
    }

    @Override
    public void onDataReceived(String data) {
        //Toast.makeText(getActivity(),"FM-Scan Data is : "+data,Toast.LENGTH_SHORT).show();
        Log.d("response","ScanFragment ==> "+data);
    }

    /*@Override
    public boolean onBackPressed() {
        Toast.makeText(getActivity(), "finish app... ", Toast.LENGTH_SHORT).show();
        getActivity().finish();
        return true;
    }*/
}
