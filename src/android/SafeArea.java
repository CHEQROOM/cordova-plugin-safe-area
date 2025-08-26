package cordova.plugin.safearea;

import android.os.Build;
import android.view.WindowInsets;
import android.view.DisplayCutout;

import org.apache.cordova.*;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

public class SafeArea extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) {
        if (action.equals("getInsets")) {
            cordova.getActivity().runOnUiThread(() -> {
                try {
                    callbackContext.success(getSafeAreaInsets());
                } catch (Exception e) {
                    callbackContext.error("Failed to get insets: " + e.getMessage());
                }
            });
            return true;
        }
        return false;
    }

    public JSONObject getSafeAreaInsets() throws JSONException {
        // Edge-to-Edge only works on Android 15+
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.VANILLA_ICE_CREAM) {
            return this.result(0, 0, 0, 0);
        }

        DisplayCutout displayCutout = null;
        WindowInsets windowInsets = cordova.getActivity().getWindow().getDecorView().getRootWindowInsets();
        if (windowInsets == null) {
            return this.result(0, 0, 0, 0);
        }

        int top = windowInsets.getStableInsetTop();
        int left = windowInsets.getStableInsetLeft();
        int right = windowInsets.getStableInsetRight();
        int bottom = windowInsets.getStableInsetBottom();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            displayCutout = windowInsets.getDisplayCutout();
            if (displayCutout != null) {
                top = Math.max(displayCutout.getSafeInsetTop(), top);
                left = Math.max(displayCutout.getSafeInsetLeft(), left);
                right = Math.max(displayCutout.getSafeInsetRight(), right);
                bottom = Math.max(displayCutout.getSafeInsetBottom(), bottom);
            }
        }
        return this.result(top, left, right, bottom);
    }

    public JSONObject result(int top, int left, int right, int bottom) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("top", dpToPixels(top));
        json.put("left", dpToPixels(left));
        json.put("right", dpToPixels(right));
        json.put("bottom", dpToPixels(bottom));
        return json;
    }

    private int dpToPixels(int dp) {
        float density = this.getDensity();
        return (int) Math.round(dp / density);
    }

    private float getDensity() {
        return cordova.getActivity().getResources().getDisplayMetrics().density;
    }
}
