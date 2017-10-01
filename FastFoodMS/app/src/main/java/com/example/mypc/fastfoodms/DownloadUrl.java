package com.example.mypc.fastfoodms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by MY PC on 9/30/2017.
 */

public class DownloadUrl {

    public String readUrl(String myUrl) throws IOException{
        String data = "";
        InputStream in = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(myUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            in = urlConnection.getErrorStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuffer sb=new StringBuffer();
            String line = "";
            while((line= br.readLine())!= null){
                sb.append(line);
            }
            data=sb.toString();
            br.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(in !=null){in.close();}
            urlConnection.disconnect();
        }

        return data;
    }
}
