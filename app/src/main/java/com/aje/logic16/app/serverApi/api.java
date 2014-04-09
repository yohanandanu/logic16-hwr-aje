package com.aje.logic16.app.serverApi;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Engin on 09.04.14.
 */
public class api {
    private static final String hostUrl = "http://bllije.de";
    private static final String postFileName= "HS_post_DB.php";
    private static final String postDataName = "na";
    private static final String postDataScore = "sc";
    private static final String getHighScoreFileName = "get_HS.php";
    private static final String getHighScoreParam = "?en=";
    private static final String getFormFileName = "get_rand_form.php";

    private static final String postURL = hostUrl + "/" + postFileName;
    private static final String getHighScoreURL = hostUrl + "/" + getHighScoreFileName + "/" + getHighScoreParam;
    private static final String getFormURL = hostUrl + "/" + getFormFileName;

    private HttpClient httpClient = new DefaultHttpClient();


    private int get(String url, ApiResponse out){

        out.response = "";
        return 0;
    }

    private int post(String URL, List<NameValuePair> Data){
        // Create a new HttpClient and Post Header
        HttpPost httppost = new HttpPost(URL);

        try {
            // Add the given data
            httppost.setEntity(new UrlEncodedFormEntity(Data, HTTP.UTF_8));
            // Execute HTTP Post Request
            HttpResponse response = this.httpClient.execute(httppost);
            if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK)
                 return 1;
            else return response.getStatusLine().getStatusCode();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
        return 0;
    }

    private int postScore(String name, int score)
    {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair(postDataName, name));
        nameValuePairs.add(new BasicNameValuePair(postDataScore, Integer.toString(score)));
        return post(postURL,nameValuePairs);
    }
    private String getForm(){

        return "";
    }
}


class ApiResponse<T> {
    public ApiResponse(String value) {
        this.response = value;
    }
    public String response;
}
