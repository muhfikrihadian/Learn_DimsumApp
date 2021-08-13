/*
 * Copyright (c) 2020 Konekthing.
 */

package com.example.restoapp.helpers;

import android.view.View;

import com.example.restoapp.models.ModelMenu;

public interface ClickItemListener {
    void onClicked(View v, ModelMenu data);
}
