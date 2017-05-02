package com.unity3d.richardf.UnityBasedLauncher;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.ArrayList;
import java.util.List;

@UsedViaReflection
public final class AppInfoProvider {
    private final PackageManager packageManager;
    private final List<AppInfo> apps;

    @UsedViaReflection
    public AppInfoProvider(Context context) {
        packageManager = context.getPackageManager();
        apps = new ArrayList<AppInfo>();
        refresh();
    }

    @UsedViaReflection
    public final void refresh() {
        apps.clear();

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> availableActivities = packageManager.queryIntentActivities(i, 0);
        for (ResolveInfo ri : availableActivities) {
            AppInfo app = new AppInfo(ri, packageManager);
            apps.add(app);
        }
    }

    @UsedViaReflection
    public final int getAppCount() {
        return apps.size();
    }

    @UsedViaReflection
    public final AppInfo getApp(int index) {
        return apps.get(index);
    }
}