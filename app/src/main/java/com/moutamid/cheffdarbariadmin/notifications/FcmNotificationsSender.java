package com.moutamid.cheffdarbariadmin.notifications;

import android.app.Activity;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class FcmNotificationsSender {
    String body;
    private final String fcmServerKey = "AAAAzzvbhX8:APA91bGavDjgYZn9tdcqZCSxPEZtmvOxUSRbNxSrpakLAvMAZ8uZ5pmaqBxo4AVmpued6aKR-Nwkj8pngfV_yhNvdAytaTh_8wuGcZ-ueTYe90LFF_zgwzVXtEyYLQv42JJae9SWdHC9";
    Activity mActivity;
    Context mContext;
    private final String postUrl = "https://fcm.googleapis.com/fcm/send";
    private RequestQueue requestQueue;
    String title;
    String userFcmToken;

    public FcmNotificationsSender(String userFcmToken2, String title2, String body2, Context mContext2, Activity mActivity2) {
        this.userFcmToken = userFcmToken2;
        this.title = title2;
        this.body = body2;
        this.mContext = mContext2;
        this.mActivity = mActivity2;
    }

    public void SendNotifications() {
        this.requestQueue = Volley.newRequestQueue(this.mActivity);
        JSONObject mainObj = new JSONObject();
        try {
            mainObj.put("to", this.userFcmToken);
            JSONObject notiObject = new JSONObject();
            notiObject.put("title", this.title);
            notiObject.put("body", this.body);
            notiObject.put("icon", "icon");
            mainObj.put("notification", notiObject);
            this.requestQueue.add(new JsonObjectRequest(1, "https://fcm.googleapis.com/fcm/send", mainObj, new Response.Listener<JSONObject>() {
                public void onResponse(JSONObject response) {
                }
            }, new Response.ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                }
            }) {
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> header = new HashMap<>();
                    header.put("content-type", "application/json");
                    header.put("authorization", "key=" + fcmServerKey);
//                    header.put("authorization", "key=AAAApf24zxI:APA91bGR1OY2AYOcCJ9Nt156xLrOXrkzJKbwM6hj4d03d4YenZWBxgFTI4fnQnOMmZzFlXOlvr_VsGo39waxcVH4oyJYLZWK-YeMQgP5KDiOkdimuTFa93PoJY-1fRh5NeOhP0IMlGeZ");
                    return header;
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

