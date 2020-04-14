package example.com.myappattendance.UI;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

import example.com.myappattendance.Model.MyModelSaveDB;
import example.com.myappattendance.R;
import example.com.myappattendance.utils.Backable;

import static example.com.myappattendance.utils.Constants.URL_GET_DB;
import static example.com.myappattendance.utils.Constants.URL_SAVE_DB;

public class Second_Showdata_Fragment extends Fragment {

    View v;
    TextView tv_token, tv_lon, tv_lat, tv_qrcode,tv_dt,tv_id;//,tv_lastId;
    //EditText et_getText;
    //Button btn_loadData;
    String qrcode, token,datetime;
    double la, lo;
    // add to php database
    MyModelSaveDB myModelSaveDB;
    RequestQueue requestQueue_send, requestQueue_get;
    StringRequest stringRequest_send, stringRequest_get;

    public Second_Showdata_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_second__showdata, container, false);
        tv_token = v.findViewById(R.id.tv_token);
        tv_lon = v.findViewById(R.id.tv_lon);
        tv_lat = v.findViewById(R.id.tv_lat);
        tv_qrcode = v.findViewById(R.id.tv_qrcode);
        tv_id = v.findViewById(R.id.tv_id);
        //btn_loadData = v.findViewById(R.id.btn_loadData);
        //et_getText = v.findViewById(R.id.et_getText);
        tv_dt = v.findViewById(R.id.tv_dt);
        //tv_lastId = v.findViewById(R.id.tv_lastId);

        if (getArguments() != null) {
            qrcode = getArguments().getString("QRCODE");
            la = getArguments().getDouble("LAT");
            lo = getArguments().getDouble("LON");
            token = getArguments().getString("TOKEN");
            token = getArguments().getString("TOKEN");

            // show get data
            /*tv_qrcode.setText(qrcode);
            tv_token.setText(token);
            tv_lat.setText(""+la);
            tv_lon.setText(""+lo);*/

            // init data to MyModelSaveDB
            myModelSaveDB = new MyModelSaveDB();
            myModelSaveDB.setSave_qrcode(qrcode);
            myModelSaveDB.setSave_token(token);
            myModelSaveDB.setSave_lat(la);
            myModelSaveDB.setSave_lon(lo);

            saveToDatabase(getActivity(), myModelSaveDB);
        }
        return v;
    }

    private void saveToDatabase(final Activity activity, final MyModelSaveDB myModelSaveDB) {
        try {
            stringRequest_send = new StringRequest(Request.Method.POST, URL_SAVE_DB,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("response", "ShowAuthenFragment ==>response 123 ==> " + response);
                            // ตอนแรก getActivity แล้ว error เพราะค่าที่รับมาเป็น null
                            //Toast.makeText(activity, "insert complete ..." + response, Toast.LENGTH_LONG).show();

                            // set qrcode key to getData
                            getDataFromDB(myModelSaveDB.getSave_qrcode());
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("response", "ShowAuthenFragment ==>error ==> " + error);
                    // ตอนแรก getActivity แล้ว error เพราะค่าที่รับมาเป็น null
                    //Toast.makeText(activity, "insert error ...please value is emtry "+error, Toast.LENGTH_LONG).show();

                    //test แก้เรื่อง Timeout error ทำให้ save database เบิ้ล 2 ครั้ง
                    //Todo test edit TimeoutError ==>1
                    /*if (error instanceof NetworkError) {
                    } else if (error instanceof ServerError) {
                    } else if (error instanceof AuthFailureError) {
                    } else if (error instanceof ParseError) {
                    } else if (error instanceof NoConnectionError) {
                    } else if (error instanceof TimeoutError) {
                        Toast.makeText(activity,
                                "Oops. Timeout error!",
                                Toast.LENGTH_LONG).show();
                    }*/

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();
                    if (getArguments() == null) {
                        Toast.makeText(activity, "Please insert value ", Toast.LENGTH_LONG).show();

                    } else {
                        params.put("token", myModelSaveDB.getSave_token());
                        params.put("lat", String.valueOf(myModelSaveDB.getSave_lat()));
                        params.put("lon", String.valueOf(myModelSaveDB.getSave_lon()));
                        params.put("qrcode", String.valueOf(myModelSaveDB.getSave_qrcode()));
                    }
                    return params;
                }
            };
            //Todo test edit TimeoutError ==>2
            /*stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    6000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));*/

        } catch (Exception e) {
            Toast.makeText(activity, "Exception error " + e, Toast.LENGTH_LONG).show();
        }

        // Creating RequestQueue.
        requestQueue_send = Volley.newRequestQueue(getActivity());
        // Adding the StringRequest object into requestQueue.
        requestQueue_send.add(stringRequest_send);
        // set qrcode key to getData
        //getDataFromDB(myModelSaveDB.getSave_qrcode());
    }

    private void getDataFromDB(final String save_qrcode) {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_GET_DB, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("check","response==>"+response);
                tv_token.setText("Data : "+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    tv_id.setText("id :"+jsonObject.getString("id"));
                    tv_token.setText("token :"+jsonObject.getString("token"));
                    tv_lat.setText("LAT :"+jsonObject.getString("lat"));
                    tv_lon.setText("LON :"+jsonObject.getString("lon"));
                    tv_qrcode.setText("qrcode :"+jsonObject.getString("qrcode"));
                    tv_dt.setText("date time :"+jsonObject.getString("dt"));
                    //tv_lastId.setText("last id:"+jsonObject.getString("lastId"));
                }
                catch (Exception e){
                    e.printStackTrace();
                    tv_token.setText("Failed to Parse Json");
                    Log.d("check","Exception==>"+e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv_token.setText("Data : Response Failed");
                Log.d("check","ERROR==>"+error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("qrcode_id", save_qrcode);

                return params;
            }
        };

        RequestQueue queue= Volley.newRequestQueue(getActivity());
        queue.add(stringRequest);
    }


}
