package com.example.a20464654j.bicing;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 20464654j on 07/02/17.
 */

public class HttpUtils {

    public static String get(String dataUrl) throws IOException {


        URL url = new URL(dataUrl);
        String resposta = null;

        HttpURLConnection coneccioURL = (HttpURLConnection) url.openConnection();

        try{
            InputStream in = new BufferedInputStream(coneccioURL.getInputStream());
            resposta = llegirStream(in);
        }finally {
            coneccioURL.disconnect();
        }

        return resposta;
    }

    private static String llegirStream(InputStream in) throws IOException {

        InputStreamReader isr = new InputStreamReader( in );
        BufferedReader br = new BufferedReader(isr);
        String linea;
        StringBuilder resposta = new StringBuilder();
        while( (linea = br.readLine() ) != null ){
            resposta.append(linea);
            resposta.append("\r");
        }
        br.close();
        return resposta.toString();
    }
}
