package example.com.myappattendance.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class MyModelData implements Parcelable {
    double LAT,LON;
    String token;

    public MyModelData() {
    }

    public MyModelData(double LAT, double LON, String token) {
        this.LAT = LAT;
        this.LON = LON;
        this.token = token;
    }

    protected MyModelData(Parcel in) {
        LAT = in.readDouble();
        LON = in.readDouble();
        token = in.readString();
    }

    public static final Creator<MyModelData> CREATOR = new Creator<MyModelData>() {
        @Override
        public MyModelData createFromParcel(Parcel in) {
            return new MyModelData(in);
        }

        @Override
        public MyModelData[] newArray(int size) {
            return new MyModelData[size];
        }
    };

    public double getLAT() {
        return LAT;
    }

    public void setLAT(double LAT) {
        this.LAT = LAT;
    }

    public double getLON() {
        return LON;
    }

    public void setLON(double LON) {
        this.LON = LON;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(LAT);
        parcel.writeDouble(LON);
        parcel.writeString(token);
    }
}
