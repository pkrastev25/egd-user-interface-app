//package com.egd.userinterface.controllers;
//
///**
// * Created by Evgeniy Bond on 24.11.2017.
// */
//
//import java.util.List;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.net.wifi.ScanResult;
//import android.net.wifi.WifiManager;
//
//
//public class WiFiDistanceDetector /*extends BaseAdapter*/ {
//    private WifiManager mainWifi;
//    private WifiReceiver receiverWifi;
//    List<ScanResult> wifiList;
//    Context localContext;
//
//    public WiFiDistanceDetector (Context newContext ){
//        this.localContext = newContext;
//    }
//    public void init(){
//        receiverWifi = new WifiReceiver();
//        mainWifi = (WifiManager) localContext.getSystemService(Context.WIFI_SERVICE);
//        System.out.println(Context.WIFI_SERVICE);
//
//
//        localContext.registerReceiver(receiverWifi, new IntentFilter(
//                WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
//        scanWifiList();
//    }
//    private void scanWifiList() {
//        mainWifi.startScan();
//        wifiList = mainWifi.getScanResults();
//        System.out.println("Hello, World");
//
//    }
//
//    class WifiReceiver extends BroadcastReceiver {
//        public void onReceive(Context c, Intent intent) {
//            System.out.println("с");
//            System.out.println("Hello, World");
//
//
//        }
//    }
//}