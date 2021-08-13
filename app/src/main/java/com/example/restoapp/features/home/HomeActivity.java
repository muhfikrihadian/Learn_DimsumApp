package com.example.restoapp.features.home;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.restoapp.R;
import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {
    @BindView(R.id.navBottom)
    BubbleNavigationLinearView bubbleNavigation;

    String TAG = "", TAG_HOME = "HOME_FRAGMENT", TAG_CART = "CART_FRAGMENT", TAG_FAVORITE = "Favorite_FRAGMENT";
    Fragment selectedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment(), TAG_HOME).commit();
        bubbleNavigation.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                switch (position) {
                    case 0:
                        selectedFragment = new HomeFragment();
                        TAG = TAG_HOME;
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment, TAG).addToBackStack(TAG).commit();
                        break;
                    case 1:
                        selectedFragment = new ShopFragment();
                        TAG = TAG_CART;
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment, TAG).addToBackStack(TAG).commit();
                        break;
                    case 2:
                        selectedFragment = new FavoriteFragment();
                        TAG = TAG_FAVORITE;
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment, TAG).addToBackStack(TAG).commit();
                        break;
                }
            }
        });
    }
}