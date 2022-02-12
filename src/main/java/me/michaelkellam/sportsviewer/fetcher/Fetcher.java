package me.michaelkellam.sportsviewer.fetcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Fetcher {

    protected URL url;
    protected char blankPlaceHolder;
    protected String[] params;

    private String lastSuccessfulString = "";

    public Fetcher(String urlStr, char blankPlaceholder, String... params) throws Exception {
        if (numPlaceholders(urlStr, blankPlaceholder) != params.length) {
            throw new IllegalArgumentException("Number of URL params not equal to constructor param length");
        }

        url = new URL(formatUrl(urlStr, blankPlaceholder, params, 0));

        this.url = url;
        this.blankPlaceHolder = blankPlaceholder;
        this.params = params;
    }

    public String fetchData() throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        int status = con.getResponseCode();

        if (status == HttpURLConnection.HTTP_OK) {
            lastSuccessfulString = content.toString();
        } else {
            System.out.println("Unsuccessful content");
            return "BAD";
        }
        return content.toString();
    }

    public void resetUrlObject(String urlStr, char blankPlaceholder, String[] params) throws MalformedURLException {
        String formattedUrl = formatUrl(urlStr, blankPlaceholder, params, 0);
        url = new URL(formattedUrl);
    }

    private String formatUrl(String urlStr, char blankPlaceholder, String[] params, int currIndex) {
        if (params.length == 0)
            return urlStr;

        if (currIndex >= params.length)
            return urlStr;

        urlStr = urlStr.replaceFirst(String.valueOf(blankPlaceholder), params[currIndex]);
        return formatUrl(urlStr, blankPlaceholder, params, currIndex + 1);
    }

    private int numPlaceholders(String str, char blankPlaceholder) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(0) == blankPlaceholder) {
                count++;
            }
        }

        return count;
    }

    public String getLastSuccessfulString() {
        return lastSuccessfulString;
    }
}
