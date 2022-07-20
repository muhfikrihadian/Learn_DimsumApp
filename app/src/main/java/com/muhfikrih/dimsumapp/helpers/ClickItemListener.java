/*
 * Copyright (c) 2020 Konekthing.
 */

package com.muhfikrih.dimsumapp.helpers;

import android.view.View;

import com.muhfikrih.dimsumapp.models.ModelMenu;

public interface ClickItemListener {
    void onClicked(View v, ModelMenu data);
}
