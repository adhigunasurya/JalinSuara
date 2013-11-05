package com.example.viewpagerexample;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.view.View;

public class CrossFadeActivity {
	private View mContentView;
	private View mLoadingView;
	private int mShortAnimationDuration;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crossfade);
		
		mContentView = findViewById(R.id.content);
		mLoadingView = findViewById(R.id.loading_spinner);
		
		mContentView.setVisibility(View.GONE);

        // Retrieve and cache the system's default "short" animation time.
        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);
	}
	
	private void crossfade(){
		mContentView.setAlpha(0f);
		mContentView.setVisibility(View.VISIBLE);
		
		mContentView.animate().alpha(1f).setDuration(mShortAnimationDuration).setListener(null);
		mLoadingView.animate().alpha(0f).setDuration(mShortAnimationDuration).setListener(new AnimatorListenerAdapter(){
			@Override
			public void onAnimationEnd(Animator animation){
				mLoadingView.setVisibility(View.GONE);
			}
			
			
		});
		
	}
}
