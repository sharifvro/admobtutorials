package com.example.admobtutorials

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class MainActivity : AppCompatActivity() {
    lateinit var mAdView: AdView
    private var mInterstitialAd : InterstitialAd? = null
    private var mInterstitialAd2 : InterstitialAd? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // banner code
        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.adViewMainActivity)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        loadInterstitial()
        loadInterstitial2()

        // interstitial code
        val buttonShowInterstitial : Button = findViewById(R.id.btn_showInters)
        buttonShowInterstitial.setOnClickListener{
            showInter()
        }

        val buttonShowIntersWithOpenActivity : Button = findViewById(R.id.btn_showActivityWithInters)
        buttonShowIntersWithOpenActivity.setOnClickListener{
            showInter2()
        }
    }

    private fun showInter2() {
        if (mInterstitialAd2 != null) {
            mInterstitialAd2?.fullScreenContentCallback = object : FullScreenContentCallback(){
                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    super.onAdFailedToShowFullScreenContent(p0)
                }

                override fun onAdDismissedFullScreenContent() {
                    val intent = Intent (this@MainActivity,SecondActivity::class.java)
                    startActivity(intent)
                }
            }

            mInterstitialAd2?.show(this)
        } else {
            val intent = Intent (this,SecondActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadInterstitial2() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(this,getString(R.string.ID_IntersVIDEO), adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd2 = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd2 = interstitialAd
            }
        })    }

    private fun showInter() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(this)
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.")
        }
    }

    private fun loadInterstitial() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(this,getString(R.string.ID_Interstitial), adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
            }
        })
    }
}