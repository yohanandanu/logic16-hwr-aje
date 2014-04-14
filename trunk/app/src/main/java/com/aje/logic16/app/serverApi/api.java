package com.aje.logic16.app.serverApi;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;

import com.aje.logic16.app.HighScoreLogic.HighscoreListAdapter;
import com.aje.logic16.app.HighScoreLogic.highScore;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

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

    //private HttpClient httpClient = new DefaultHttpClient();


    private int get(String url, ApiResponse out){
        try {
            out.response = new HttpGetTask().execute(url).get();
            return 1;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e)
        {
            e.printStackTrace();
        }


        return 0;
    }



    private int post(String URL, List<NameValuePair> Data){
        // Create a new HttpClient and Post Header
            AsyncTask resp = new HttpPostTask(Data).execute(URL);
            return 1;
    }

    private int postScore(String name, int score)
    {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair(postDataName, name));
        nameValuePairs.add(new BasicNameValuePair(postDataScore, Integer.toString(score)));
        return post(postURL,nameValuePairs);
    }
    private String getForm(){
        ApiResponse<String> response = new ApiResponse("");

        int res = get(getFormURL,response);
        if (res == 1)
        {
            return response.response;
        }
        else return Integer.toString(res);
    }

   private String getScore(int pos){
            ApiResponse<String> response = new ApiResponse("");

        String highScoreUrl = getHighScoreURL + Integer.toString(pos);
        int res = get(highScoreUrl,response);
        if (res == 1)
        {
            return response.response;
        }
        else return Integer.toString(res);
    }


    public String getFormula()
    {
        return getForm();
        //return "";
    }

    public highScore getHighScore(int pos) throws IllegalArgumentException
    {
        String name = "";
        int score = 0;

        String response = this.getScore(pos);
        if (response.charAt(0) == '#')
        {
            response = response.substring(1);

            String[] responseSplit = response.split("#");

            if (responseSplit[0].length() <= 0 || responseSplit[1].length() <= 0)
                throw new IllegalArgumentException();

            name = responseSplit[0];
            score = Integer.parseInt(responseSplit[1]);
            return new highScore(name,score);
        }
        else throw new IllegalArgumentException();
    }

    public void addScore(highScore score)
    {
        postScore(score.getName(),score.getScore());
    }
}


class ApiResponse<T> {
    public ApiResponse(String value) {
        this.response = value;
    }
    public String response;
}


class HttpGetTask extends AsyncTask<String,Integer,String>
{

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        // update textview here
    }


    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String URL = params[0];
        HttpGet requestGet = new HttpGet(URL);
        requestGet.addHeader("User-Agent", HTTP.USER_AGENT);
        return handleRequest(requestGet);

    }


    private String handleRequest(HttpUriRequest request)
    {
        HttpClient httpclient = new DefaultHttpClient();

        try
        {
            HttpResponse response = httpclient.execute(request);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                System.out.println("Response : " +
                        result.toString());
                return result.toString();
            } else
            {
                throw new NetworkErrorException();
            }

        } catch (ClientProtocolException e) {
            e.getMessage();
        } catch (IOException e) {
            e.getMessage();
        } catch (Exception e)
        {
            e.getMessage();
        }
        return "";
    }
}


class HttpPostTask extends AsyncTask<String,Integer,String>
{
    private List<NameValuePair> mData = null;// post data

    /**
     * constructor
     */
    public HttpPostTask(List<NameValuePair> data) {
        mData = data;
    }

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        // update textview here
    }


    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String URL = params[0];
        HttpPost request = (HttpPost) new HttpPost(URL);

        try {
            request.setEntity(new UrlEncodedFormEntity(mData, HTTP.UTF_8));
            return handleRequest(request);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }


    private String handleRequest(HttpUriRequest request)
    {
        HttpClient httpclient = new DefaultHttpClient();

        try
        {
            HttpResponse response = httpclient.execute(request);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                System.out.println("Response : " +
                        result.toString());
                return result.toString();
            } else
            {
                throw new NetworkErrorException();
            }

        } catch (ClientProtocolException e) {
            e.getMessage();
        } catch (IOException e) {
            e.getMessage();
        } catch (Exception e)
        {
            e.getMessage();
        }
        return "";
    }
}