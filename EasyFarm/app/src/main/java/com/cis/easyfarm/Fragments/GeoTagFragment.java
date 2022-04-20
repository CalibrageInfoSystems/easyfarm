//package com.cis.easyfarm.Fragments;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//
//import androidx.appcompat.widget.Toolbar;
//
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.cis.easyfarm.R;
//import com.cis.easyfarm.cloudHelper.ApplicationThread;
//import com.cis.easyfarm.common.BaseFragment;
//import com.cis.easyfarm.common.CommonConstants;
//import com.cis.easyfarm.common.CommonUtils;
//import com.cis.easyfarm.common.DataManager;
//import com.cis.easyfarm.common.GeoBoundaries;
//import com.cis.easyfarm.Interface.LatLongListener;
//import com.cis.easyfarm.common.LocationProvider;
//import com.cis.easyfarm.common.LocationService;
//import com.cis.easyfarm.Interface.UpdateUiListener;
//
//
//public class GeoTagFragment extends BaseFragment {
//
//    private static final String LOG_TAG = GeoTagFragment.class.getName();
//    private Toolbar toolbar;
//    private TextView lattitudeTxt, titleHeader;
//    private TextView longtudeTxt;
//    private ImageView geotag;
//    private Button farmerSaveBtn,retakeGeoTagBtn;
//    private TextView addressTxt;
//    private double latitude, longitude;
//    public UpdateUiListener updateUiListener;
//    public GeoBoundaries geoBoundaries = null;
//    private static LocationProvider mLocationProvider;
//    private double currentLatitude, currentLongitude;
//
//
//    public void setUpdateUiListener(UpdateUiListener updateUiListener) {
//        this.updateUiListener = updateUiListener;
//    }
//
//    private BroadcastReceiver mLbsMessageReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (intent != null && intent.getAction() != null) {
//                if (intent.getAction().equals(LocationService.ACTION_LOCATION_UPDATED)) {
//                    latitude = intent.getDoubleExtra(LocationService.KEY_LATITUDE, -1);
//                    longitude = intent.getDoubleExtra(LocationService.KEY_LONGITUDE, -1);
//                    ApplicationThread.uiPost(LOG_TAG, "location details", new Runnable() {
//                        @Override
//                        public void run() {
//                            lattitudeTxt.setText("" + latitude);
//                            longtudeTxt.setText("" + longitude);
//
//                            farmerSaveBtn.setText("Save");
//                            titleHeader.setVisibility(View.GONE);
//                            retakeGeoTagBtn.setVisibility(View.VISIBLE);
//                        }
//                    });
//                    Log.d(LOG_TAG, "### in locationReceiver latitude: " + latitude + " longitude: " + longitude);
//                    CommonUtils.getAddressByLocation(getActivity(),
//                            latitude, longitude, false, new ApplicationThread.OnComplete<String>() {
//                                @Override
//                                public void execute(boolean success, final String zipCode, final String geoCountry) {
//                                    Log.d(LOG_TAG, "### in getAddressByLocation from :" + LOG_TAG);
//                                    CommonUtils.stopLocationService(getActivity(), mLbsMessageReceiver);
//                                    if (success) {
//                                        ApplicationThread.uiPost(LOG_TAG, "location details", new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                addressTxt.setText(geoCountry);
//                                                CommonConstants.GEO_TAG_ADDRESS = geoCountry;
//                                            }
//                                        });
//                                    } else {
//                                        CommonUtils.stopLocationService(getActivity(), mLbsMessageReceiver);
//                                    }
//                                }
//                            });
//                }
//            }
//        }
//    };
//
//    public GeoTagFragment() {
//
//    }
//    private static  String latLong="";
//
//    public static LocationProvider getLocationProvider(Context context, boolean showDialog){
//        if(mLocationProvider == null){
//            mLocationProvider = new LocationProvider(context, new LatLongListener() {
//
//                public void getLatLong(String mLatLong) {
//                    latLong = mLatLong;
//                }
//            });
//
//        }
//        if(mLocationProvider.getLocation(showDialog)){
//            return mLocationProvider;
//        } else{
//            return null;
//        }
//
//    }
//
//    public  String getLatLong(Context context,boolean showDialog) {
//
//        mLocationProvider = getLocationProvider(context,showDialog);
//
//        if(mLocationProvider != null){
//            latLong = mLocationProvider.getLatitudeLongitude();
//
//
//        }
//        return latLong;
//    }
//    @Override
//    public void Initialize() {
//        LayoutInflater inflater = LayoutInflater.from(getActivity());
//        View rootView = inflater.inflate(R.layout.fragment_adduser_fifthpage, null, false);
//        baseLayout.addView(rootView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        setTile(getActivity().getResources().getString(R.string.create_geo_tag));
//
//        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
////        lattitudeTxt = (TextView) rootView.findViewById(R.id.lattitudeTxt);
////        longtudeTxt = (TextView) rootView.findViewById(R.id.longtudeTxt);
////        geotag = (ImageView) rootView.findViewById(R.id.geotag);
////        farmerSaveBtn = (Button) rootView.findViewById(R.id.createGeoTagBtn);
////        retakeGeoTagBtn = (Button) rootView.findViewById(R.id.retakeGeoTagBtn);
////        addressTxt = (TextView) rootView.findViewById(R.id.addressTxt);
////        titleHeader = (TextView) rootView.findViewById(R.id.titleHeader);
//        titleHeader.setVisibility(View.VISIBLE);
//        farmerSaveBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (farmerSaveBtn.getText().toString().equalsIgnoreCase("Save") && latitude != 0 && longitude != 0) {
//                    setGeoTagData();
//                } else {
//                    if (CommonUtils.isLocationPermissionGranted(getActivity())) {
//                        CommonUtils.startLocationService(getActivity(), mLbsMessageReceiver);
//                    }
//                }
//            }
//        });
//
//        retakeGeoTagBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (CommonUtils.isLocationPermissionGranted(getActivity())) {
//                    CommonUtils.startLocationService(getActivity(), mLbsMessageReceiver);
//                }
//
//            }
//        });
//
//        geoBoundaries = (GeoBoundaries) DataManager.getInstance().getDataFromManager(DataManager.PLOT_GEO_TAG);
//        if (geoBoundaries != null) {
//            String latlong[]= getLatLong(getActivity(),false).split("@");
//            // Comment by Sushil to change LatLong Logic
//            // actualDistance = CommonUtils.distance(currentLatitude, currentLongitude, Double.parseDouble(yieldDataArr[0]), Double.parseDouble(yieldDataArr[1]), 'm');
//            currentLatitude=Double.parseDouble(latlong[0]);
//            currentLongitude=Double.parseDouble(latlong[1]);
//            latitude = geoBoundaries.getLatitude();
//            longitude = geoBoundaries.getLongitude();
//            lattitudeTxt.setText(""+currentLatitude);
//            longtudeTxt.setText(""+currentLongitude);
//            addressTxt.setText(CommonConstants.GEO_TAG_ADDRESS);
//            farmerSaveBtn.setText("Save");
//            retakeGeoTagBtn.setVisibility(View.VISIBLE);
//
//        }
//    }
//
//
//    public void setGeoTagData() {
//        String latlong[]= getLatLong(getActivity(),false).split("@");
//        // Comment by Sushil to change LatLong Logic
//        // actualDistance = CommonUtils.distance(currentLatitude, currentLongitude, Double.parseDouble(yieldDataArr[0]), Double.parseDouble(yieldDataArr[1]), 'm');
//        currentLatitude=Double.parseDouble(latlong[0]);
//        currentLongitude=Double.parseDouble(latlong[1]);
//        if (currentLatitude != 0 && currentLongitude != 0) {
//            geoBoundaries = new GeoBoundaries();
//            geoBoundaries.setLatitude(currentLatitude);
//            geoBoundaries.setLongitude(currentLongitude);
//            if(CommonUtils.isFromCropMaintenance()){
//                geoBoundaries.setGeocategorytypeid(256);
//            }else {
//                geoBoundaries.setGeocategorytypeid(207);
//            }
//
//            DataManager.getInstance().addData(DataManager.PLOT_GEO_TAG, geoBoundaries);
//            updateUiListener.updateUserInterface(0);
//        }
//        getFragmentManager().popBackStack();
//    }
//
//}
