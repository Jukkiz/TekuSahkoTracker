package HttpService;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;

import com.github.mikephil.charting.data.BarDataSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;;

/**
 * Created by Juge on 26.10.2015.
 */
public class WebService extends AsyncTask<String, Integer, String> {

    onAsyncRequestComplete caller;
    Context context;


    public WebService(Fragment a, String m){
        caller = (onAsyncRequestComplete) a;
        context = a.getActivity();
    }

    public interface onAsyncRequestComplete{
        public void HttpResponse(String response);
    }

    public String doInBackground(String... urls){
        String address = urls[0].toString();

        return get(address);
    }
    public void onPreExecute() {

    }
    public void onPostExecute(String response){

        caller.HttpResponse(response);
    }

    private String get(String address){

        try{
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(address);
            HttpResponse response = client.execute(post);

            return stringifyResponse(response);
        }
         catch (ClientProtocolException e) {
             // TODO Auto-generated catch block
         }
        catch (IOException e) {

        }
        return null;
    }

    private String stringifyResponse(HttpResponse response){
        BufferedReader reader;

        try{
            reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuffer sb = new StringBuffer("");
            String Line = "";
            while((Line = reader.readLine()) != null ){
                sb.append(Line);
            }
            reader.close();

            return  sb.toString();
        } catch (IOException e) {

        }

        return null;
    }


}
