package example.com.myappattendance.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class MyModelSaveDB implements Parcelable {
    String save_qrcode,save_token;
    double save_lon,save_lat;

    public MyModelSaveDB() {
    }

    public MyModelSaveDB(String save_qrcode, String save_token, double save_lon, double save_lat) {
        this.save_qrcode = save_qrcode;
        this.save_token = save_token;
        this.save_lon = save_lon;
        this.save_lat = save_lat;
    }

    protected MyModelSaveDB(Parcel in) {
        save_qrcode = in.readString();
        save_token = in.readString();
        save_lon = in.readDouble();
        save_lat = in.readDouble();
    }

    public static final Creator<MyModelSaveDB> CREATOR = new Creator<MyModelSaveDB>() {
        @Override
        public MyModelSaveDB createFromParcel(Parcel in) {
            return new MyModelSaveDB(in);
        }

        @Override
        public MyModelSaveDB[] newArray(int size) {
            return new MyModelSaveDB[size];
        }
    };

    public String getSave_qrcode() {
        return save_qrcode;
    }

    public void setSave_qrcode(String save_qrcode) {
        this.save_qrcode = save_qrcode;
    }

    public String getSave_token() {
        return save_token;
    }

    public void setSave_token(String save_token) {
        this.save_token = save_token;
    }

    public double getSave_lon() {
        return save_lon;
    }

    public void setSave_lon(double save_lon) {
        this.save_lon = save_lon;
    }

    public double getSave_lat() {
        return save_lat;
    }

    public void setSave_lat(double save_lat) {
        this.save_lat = save_lat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(save_qrcode);
        parcel.writeString(save_token);
        parcel.writeDouble(save_lon);
        parcel.writeDouble(save_lat);
    }
}
