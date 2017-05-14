package com.ada.koranosponso;

import android.content.Context;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alex on 30/04/2017.
 */

public class RestAPIWebServices {
    private Context context;
    private HashMap<String, String> hashMap;
    private String path;
    private static boolean post_ok;
    private JSONObject json;

    public RestAPIWebServices(Context context, HashMap hashMap, String path) {
        this.context = context;
        this.hashMap = hashMap;
        this.path = path;

    }

    public void responseApi(final VolleyCallback callback) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, path,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {

                        callback.onSuccess(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        Toast.makeText(context, "No se ha conectado", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request

                for (Map.Entry<String, String> entry : hashMap.entrySet()) {
                    params.put(entry.getKey(), entry.getValue());
                }
                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }

    public interface VolleyCallback {
        void onSuccess(String result);
    }
}
