package edu.ut.softlab.raspberrypiled;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by limeng on 6/2/16.
 */
public class HttpGetTask extends AsyncTask<Integer, Void, Void> {
    private final String DEFAULT_URL="http://192.168.11.61/~pi/ledtest.php";

    private final Activity parentActivity;

    private ProgressDialog mDialog=null;
    private String mUri=null;

    public HttpGetTask(Activity parentActivity) {
        super();
        this.parentActivity=parentActivity;
    }

    @Override
    protected void onPreExecute() {
        mDialog=new ProgressDialog(parentActivity);
        mDialog.setMessage("Gettting message...");
        mDialog.show();
    }

    @Override
    protected Void doInBackground(Integer... params) {
        mUri=DEFAULT_URL+"?num="+params[0].toString()+"&stat="+params[1].toString();
        exec_get();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mDialog.dismiss();
    }

    private String exec_get() {
        HttpURLConnection http=null;
        InputStream in=null;
        String src="";
        try {
            URL url=new URL(mUri);
            http=(HttpURLConnection)url.openConnection();
            http.setRequestMethod("GET");
            http.connect();
            in=http.getInputStream();
            byte [] line=new byte[1024];
            int size;
            while (true) {
                size=in.read(line);
                if(size<=0) {
                    break;
                }
                src+=new String(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(http!=null) {
                    http.disconnect();
                }
                if(in!=null) {
                    in.close();
                }
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }
        return src;
    }
}
