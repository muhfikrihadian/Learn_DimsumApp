/*
 * Copyright (c) 2020 Konekthing.
 */

package com.adhityafairus.restoapp.helpers;

import android.view.View;

import com.adhityafairus.restoapp.models.ModelMenu;

public interface ClickItemListener {
    void onClicked(View v, ModelMenu data);
}
