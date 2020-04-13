package example.com.myappattendance.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class MyModelQrCode implements Parcelable {
    String qrcode;

    public MyModelQrCode() {
    }

    public MyModelQrCode(String qrcode) {
        this.qrcode = qrcode;
    }

    protected MyModelQrCode(Parcel in) {
        qrcode = in.readString();
    }

    public static final Creator<MyModelQrCode> CREATOR = new Creator<MyModelQrCode>() {
        @Override
        public MyModelQrCode createFromParcel(Parcel in) {
            return new MyModelQrCode(in);
        }

        @Override
        public MyModelQrCode[] newArray(int size) {
            return new MyModelQrCode[size];
        }
    };

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(qrcode);
    }
}
