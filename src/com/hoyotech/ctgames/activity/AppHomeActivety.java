package com.hoyotech.ctgames.activity;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;

import com.hoyotech.ctgames.R;

/**
 * Created by GGCoke on 13-12-3.
 */
public class AppHomeActivety extends TabActivity {
    private TabHost tabHost;
    private RadioGroup radioGroup;

    private static final String[] tabTags = {"tabReco", "tabColl", "tabCate"};

    private Intent appRecommend;
    private Intent appCollection;
    private Intent appCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs_layout);

        RadioButton rbRecom = (RadioButton) findViewById(R.id.rb_tab_left);
        RadioButton rbColle = (RadioButton) findViewById(R.id.rb_tab_middle);
        RadioButton rbCateg = (RadioButton) findViewById(R.id.rb_tab_right);

        rbRecom.setText(R.string.app_recommend);
        rbColle.setText(R.string.app_collection);
        rbCateg.setText(R.string.app_categories);

        tabHost = this.getTabHost();
        initIntent();
        radioGroup = (RadioGroup)findViewById(R.id.main_radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_tab_left:
                        tabHost.setCurrentTabByTag(tabTags[0]);
                        break;
                    case R.id.rb_tab_middle:
                        tabHost.setCurrentTabByTag(tabTags[1]);
                        break;
                    case R.id.rb_tab_right:
                        tabHost.setCurrentTabByTag(tabTags[2]);
                        break;
                    default:
                        break;
                }
            }
        });
        rbRecom.setChecked(true);
    }


    private void initIntent() {
        this.appRecommend = new Intent(this, AppRecommendActivity.class);
        this.appCollection = new Intent(this, AppCollectionActivity.class);
        this.appCategories = new Intent(this, AppCategoriesActivity.class);
        TabHost.TabSpec tsAppRecommend = this.tabHost.newTabSpec(tabTags[0])
                .setIndicator(getString(R.string.app_recommend)).setContent(appRecommend);
        TabHost.TabSpec tsAppCollection = this.tabHost.newTabSpec(tabTags[1])
                .setIndicator(getString(R.string.app_collection)).setContent(appCollection);
        TabHost.TabSpec tsAppCategories = this.tabHost.newTabSpec(tabTags[2])
                .setIndicator(getString(R.string.app_categories)).setContent(appCategories);

        this.tabHost.addTab(tsAppRecommend);
        this.tabHost.addTab(tsAppCollection);
        this.tabHost.addTab(tsAppCategories);
    }
}
