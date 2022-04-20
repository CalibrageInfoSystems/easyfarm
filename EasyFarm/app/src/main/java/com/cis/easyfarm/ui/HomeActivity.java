package com.cis.easyfarm.ui;
//
//
//import android.app.Activity;
//import android.os.Bundle;
//
//import androidx.appcompat.app.ActionBarDrawerToggle;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.drawerlayout.widget.DrawerLayout;
//
//import com.cis.easyfarm.Fragments.HomeFragment;
//import com.cis.easyfarm.R;
//import com.cis.easyfarm.common.BaseActivity;
//import com.cis.easyfarm.common.New_base_Activity;
//import com.cis.easyfarm.database.EasyFarmDatabse;
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.android.material.navigation.NavigationView;
//
//public class HomeActivity extends New_base_Activity {
//    public  static  final  String LOG_TAG = HomeActivity.class.getSimpleName();
//    private Toolbar toolbar;
//    private BottomNavigationView bottom_navigation;
//    private DrawerLayout dl;
//    private ActionBarDrawerToggle t;
//    private NavigationView nv;
//    FloatingActionButton myFab;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//        intView();
//        setViews();
//    }
//    private void intView() {
//        HomeFragment homeFragment = new HomeFragment();
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.content_frame, homeFragment, "homeTag")
//                .commit();
//    }
//    private void setViews() {
//    }
//}
