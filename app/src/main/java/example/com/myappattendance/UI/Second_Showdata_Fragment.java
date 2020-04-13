package example.com.myappattendance.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import example.com.myappattendance.R;
import example.com.myappattendance.utils.Backable;

public class Second_Showdata_Fragment extends Fragment implements Backable {

    View v;
    TextView tv_token,tv_lon,tv_lat,tv_qrcode;
    String qrcode,token;
    double la,lo;

    public Second_Showdata_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v =  inflater.inflate(R.layout.fragment_second__showdata, container, false);
        tv_token = v.findViewById(R.id.tv_token);
        tv_lon = v.findViewById(R.id.tv_lon);
        tv_lat = v.findViewById(R.id.tv_lat);
        tv_qrcode = v.findViewById(R.id.tv_qrcode);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null){
            qrcode = getArguments().getString("QRCODE");
            la = getArguments().getDouble("LAT");
            lo = getArguments().getDouble("LON");
            token = getArguments().getString("TOKEN");

            tv_qrcode.setText(qrcode);
            tv_token.setText(token);
            tv_lat.setText(""+la);
            tv_lon.setText(""+lo);
        }
    }

    @Override
    public boolean onBackPressed() {
        Toast.makeText(getActivity(), "go to main fragment... ", Toast.LENGTH_SHORT).show();
        Main_ScanQrcode_Fragment main_scanQrcode_fragment = new Main_ScanQrcode_Fragment();

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contentContainer, main_scanQrcode_fragment)
                //.addToBackStack("")\
                //.commitAllowingStateLoss();
                .commit();
        return true;
    }
}
