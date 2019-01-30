package com.w.assistantlib.remote;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

/**
 * 类似 jsonrpc
 * <p>
 * FangJW
 */
public class Request implements Parcelable {
    /**
     * 协议版本
     */
    public int version;
    /**
     * 请求方法
     */
    public String method;
    /**
     * 请求参数
     */
    public HashMap<String, String> params = new HashMap<String, String>();
    /**
     * 公用参数 拓展信息
     */
    public HashMap<String, String> exts = new HashMap<String, String>();

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.version);
        dest.writeString(this.method);
        dest.writeSerializable(this.params);
        dest.writeSerializable(this.exts);
    }

    public Request() {
    }

    protected Request(Parcel in) {
        this.version = in.readInt();
        this.method = in.readString();
        this.params = (HashMap<String, String>) in.readSerializable();
        this.exts = (HashMap<String, String>) in.readSerializable();
    }

    public static final Creator<Request> CREATOR = new Creator<Request>() {
        @Override
        public Request createFromParcel(Parcel source) {
            return new Request(source);
        }

        @Override
        public Request[] newArray(int size) {
            return new Request[size];
        }
    };
}
