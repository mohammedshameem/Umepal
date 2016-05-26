package com.qiito.umepal.gcm;


import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.iid.InstanceIDListenerService;
import com.qiito.umepal.R;

import java.io.IOException;

/**
 * Created by abin on 26/05/16.
 */
public class GcmIDListenerService extends InstanceIDListenerService {
    private String token;

    @Override
    public void onTokenRefresh() {
        InstanceID instanceID = InstanceID.getInstance(getApplicationContext());
        try {
            token = instanceID.getToken(getApplicationContext().getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
