package com.dreamsofpines.mygallery.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dreamsofpines.mygallery.R;
import com.dreamsofpines.mygallery.ui.fragment.LentaFragment;
import com.dreamsofpines.mygallery.util.ScreenUtils;

public class GalleryActivity extends AppCompatActivity {

    private FragmentManager fm = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ScreenUtils.init(this);
        LentaFragment lF = new LentaFragment();
        lF.setFragmentManager(fm);
        fm.beginTransaction()
                .add(R.id.main_frame_layout,lF)
                .commit();
    }




}
