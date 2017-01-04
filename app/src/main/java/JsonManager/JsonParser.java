package JsonManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.InputStream;

import Utils.EConstants;

/**
 * Created by kuush on 1/4/2017.
 */

public class JsonParser {

    static InputStream is = null;
    static String verify = null;
    public  String varification = "";
    public String pdf_URL = "";


    public String ParseString(String s) {

        String g_Table = null;
        try {
            Object json = new JSONTokener(s).nextValue();
            if (json instanceof JSONObject) {
                JSONObject obj = new JSONObject(s);
                g_Table = obj.optString("JSON_GetRegistrationResult");
                return g_Table;
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String ParsePdfUrl(String s) {

        String g_Table = null;
        try {
            Object json = new JSONTokener(s).nextValue();
            if (json instanceof JSONObject) {
                JSONObject obj = new JSONObject(s);
                g_Table = obj.optString(EConstants.InstructionsResult);
                return g_Table;
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *  Logout
     * @author Kush Kumar Dhawan
     */

   /* public String getPdfURL(String url) {

        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        try {

            response = httpclient.execute(new HttpGet(url));
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = response.getEntity();
                is = httpEntity.getContent();
            } else {
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 32);
            //    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 16);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            pdf_URL = sb.toString();
            System.out.println(pdf_URL);

        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
            return "";
        }

        return pdf_URL;
    }*/

/*

    public String checkLogin(String url) {

        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        try {

            response = httpclient.execute(new HttpGet(url));

            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = response.getEntity();
                is = httpEntity.getContent();

            } else {
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 32);
            //    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 16);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");

            }
            is.close();
            varification = sb.toString();
            System.out.println("varification is" + varification);

        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
            return "";
        }

        return varification;
    }*/
}
