package com.unity3d.richardf.UnityBasedLauncher;

import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import java.io.ByteArrayOutputStream;

public final class AppInfo {

    @UsedViaReflection
    public final String label;

    @UsedViaReflection
    public final String name;

    @UsedViaReflection
    public final byte[] icon;

    AppInfo(ResolveInfo ri, PackageManager packageManager) {
        label = ri.loadLabel(packageManager).toString();
        name = ri.activityInfo.packageName;

        BitmapDrawable drawableIcon = (BitmapDrawable) ri.activityInfo.loadIcon(packageManager);
        Bitmap bmp = drawableIcon.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        icon = stream.toByteArray();
    }
}