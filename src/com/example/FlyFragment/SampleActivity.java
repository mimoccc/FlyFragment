package com.example.FlyFragment;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class SampleActivity extends Activity implements ObservableScrollView.Observable {

    private static final int SENSITIVITY = 3;

    private ObservableScrollView mObservableScrollView;
    private FlyFragment mFlyFragment;

    private boolean mIsFragmentVisible = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mObservableScrollView = (ObservableScrollView) findViewById(R.id.scroll);
        mObservableScrollView.setCallbacks(this);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (savedInstanceState != null) {
            mFlyFragment = (FlyFragment) getFragmentManager().findFragmentById(R.id.fly_fragment);
            ft.hide(mFlyFragment);
            ft.commit();
        } else {
            mFlyFragment = new FlyFragment();
            ft.add(R.id.fly_fragment, mFlyFragment);
            ft.hide(mFlyFragment);
            ft.commit();
        }

    }

    @Override
    public void onScrollChanged(int currentY, int previousY) {

        final boolean isSensitivityCorrect = isCorrectSensitivity(currentY, previousY);

        if (currentY > previousY && isSensitivityCorrect) {
            if (!mIsFragmentVisible) {
                hideFlyFragment();
                mIsFragmentVisible = true;
            }
        } else if (currentY < previousY && isSensitivityCorrect) {
            if (mIsFragmentVisible) {
                showFlyFragment();
                mIsFragmentVisible = false;
            }
        }
    }

    private boolean isCorrectSensitivity(int currentY, int previousY) {
        return Math.abs(currentY - previousY) > SENSITIVITY;
    }

    private void showFlyFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_up);
        ft.show(mFlyFragment);
        ft.commit();
    }

    private void hideFlyFragment() {
        if (mFlyFragment != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.slide_out_up, R.anim.slide_out_up);
            ft.hide(mFlyFragment);
            ft.commit();
        }
    }

}
