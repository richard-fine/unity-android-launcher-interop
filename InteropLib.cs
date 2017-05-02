using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class AppInfo
{
    public AppInfo() { }
    public string label;
    public string name;
    public Texture2D icon;

    public AppInfo(AndroidJavaObject jniObj)
    {
        name = jniObj.Get<string>("name");
        label = jniObj.Get<string>("label");

        byte[] pngBytes = jniObj.Get<byte[]>("icon");
        if (pngBytes != null)
        {
            icon = new Texture2D(1, 1);
            icon.LoadImage(pngBytes, true);
        }
    }
}

public static class InteropLib
{
    public static List<AppInfo> GetAllInstalledApps()
    {
        List<AppInfo> result;
#if UNITY_EDITOR
        result = new List<AppInfo>();
        for (int i = 0; i < 20; ++i)
            result.Add(new AppInfo { name = "Test name", label = "Test label" });
#else
        AndroidJavaClass unityPlayerClass = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
        AndroidJavaObject currentActivity = unityPlayerClass.GetStatic<AndroidJavaObject>("currentActivity");

        AndroidJavaObject provider = new AndroidJavaObject("com.unity3d.richardf.UnityBasedLauncher.AppInfoProvider", currentActivity);

        int appCount = provider.Call<int>("getAppCount");
        Debug.LogFormat("Retrieving {0} apps", appCount);

        result = new List<AppInfo>(appCount);
        for (int i = 0; i < appCount; ++i)
        {
            AndroidJavaObject app = provider.Call<AndroidJavaObject>("getApp", i);
            result.Add(new AppInfo(app));
        }
#endif

        return result;
    }

    public static void LaunchApp(string appName)
    {
#if UNITY_EDITOR
        Debug.LogFormat("Launching app {0}", appName);
#else
        AndroidJavaClass unityPlayerClass = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
        AndroidJavaObject currentActivity = unityPlayerClass.GetStatic<AndroidJavaObject>("currentActivity");

        AndroidJavaObject launcher = new AndroidJavaObject("com.unity3d.richardf.UnityBasedLauncher.AppLauncher", currentActivity);

        launcher.Call("launchApp", appName);
#endif
    }
}
