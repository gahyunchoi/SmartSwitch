package com.example.user.myapplication.activities;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.example.user.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import me.riddhimanadib.library.BottomBarHolderActivity;
import me.riddhimanadib.library.NavigationPage;

/**
 * Created by gahyunchoi on 9/5/17.
 */

public class NavActivity extends BottomBarHolderActivity implements FirstFragment.OnFragmentInteractionListener, SecondFragment2.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NavigationPage page1 = new NavigationPage("온/오프", ContextCompat.getDrawable(this, R.drawable.ic_assessment_black_24dp), FirstFragment.newInstance());
        NavigationPage page2 = new NavigationPage("알람", ContextCompat.getDrawable(this, R.drawable.ic_home_black_24dp), BlankFragment.newInstance());
        NavigationPage page3 = new NavigationPage("온/습도", ContextCompat.getDrawable(this, R.drawable.ic_mail_black_24dp), BlankFragment2.newInstance());
        NavigationPage page4 = new NavigationPage("전력", ContextCompat.getDrawable(this, R.drawable.ic_person_black_24dp), ElecFragment.newInstance());


        List<NavigationPage> navigationPages = new ArrayList<>();
        navigationPages.add(page1);
        navigationPages.add(page2);
        navigationPages.add(page3);
        navigationPages.add(page4);

        super.setupBottomBarHolderActivity(navigationPages);
    }
}