package es.source.code.br;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import es.source.code.service.UpdateService;

/**
 * Created by sail on 2018/10/31.
 */

public class DeviceStartedListener extends BroadcastReceiver {

    private static final String TAG = "BootCompleteReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, UpdateService.class);
        context.startService(service);
        Log.i(TAG, "Boot Complete. Starting UpdateService...");
    }


}
