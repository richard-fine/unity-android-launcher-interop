package com.unity3d.richardf.UnityBasedLauncher;

import android.content.Context;
import android.content.Intent;

@UsedViaReflection
public final class AppLauncher {
    private final Context context;

    @UsedViaReflection
    public AppLauncher(Context _context) {
        context = _context;
    }

    @UsedViaReflection
    public void launchApp(String appName) {
        Intent i = context.getPackageManager().getLaunchIntentForPackage(appName);
        context.startActivity(i);
    }
}
