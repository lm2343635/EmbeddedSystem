package edu.ut.softlab.raspberrypiinterface;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by limeng on 5/26/16.
 */
public class HttpGetTask extends AsyncTask<String, Void, String> {
    private final String DEFAULT_URL="http://192.168.11.18/~pi/top.php";

    static final int UID=0;
    static final int PROCESS=1;
    private final Activity parentActivity;

    private ProgressDialog mDialog=null;
    private String mUri=null;
    private int mGetType=UID;

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
    protected String doInBackground(String... params) {
        if(params[0]==null) {
            mUri=DEFAULT_URL+"?uid=";
            mGetType=UID;
        } else {
            mUri=DEFAULT_URL+"?uid="+params[0];
            mGetType=PROCESS;
        }
        return exec_get();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mDialog.dismiss();
        if(mGetType==UID) {
            Spinner uidSpinner=(Spinner)parentActivity.findViewById(R.id.spinner_uid);
            updateUidSpinner(uidSpinner, s);
        } else if(mGetType==PROCESS) {
            TextView textView=(TextView)parentActivity.findViewById(R.id.text_view_process);
            textView.setMovementMethod(ScrollingMovementMethod.getInstance());
            textView.setText(s);
        }
    }

    public void updateUidSpinner(Spinner spinner, String str) {
        Log.d("Test", "str"+str);
        String [] uids=str.split(",");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(parentActivity, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for(String uid: uids) {
            adapter.add(uid);
        }
        spinner.setAdapter(adapter);
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
