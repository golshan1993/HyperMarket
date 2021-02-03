package com.example.hypermarket.utils;

import android.view.View;
import android.widget.TextView;

public class UiUtils {

    public static void setBadgeicon(int bagSize , TextView basketCountTextView) {
        if (basketCountTextView != null) {
            if (bagSize == 0) {
                basketCountTextView.setVisibility(View.GONE);
            } else {
                basketCountTextView.setText(String.valueOf(Math.min(bagSize, 99)));
                basketCountTextView.setVisibility(View.VISIBLE);
            }
        }
    }
}
