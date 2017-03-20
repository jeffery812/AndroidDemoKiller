package com.max.tang.demokiller.main.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.max.tang.demokiller.R;
import com.max.tang.demokiller.activity.BaseActivity;
import com.max.tang.demokiller.fragment.OnFragmentInteractionListener;
import com.max.tang.demokiller.fragment.PlusOneFragment;
import com.max.tang.demokiller.main.fragment.DemoListFragment;
import com.max.tang.demokiller.main.model.GoogleSignIn;
import com.max.tang.demokiller.utils.log.Logger;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class NavigationActivity extends BaseActivity
    implements NavigationView.OnNavigationItemSelectedListener,
    PlusOneFragment.OnFragmentInteractionListener, OnFragmentInteractionListener, SignContract.View {

    TextView textViewTitle;
    TextView textViewSubtitle;
    ImageView imageProfile;
    SignContract.Presenter presenter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        /**
         * https://plus.google.com/+AndroidDevelopers/posts/Z1Wwainpjhd
         * replacing that custom theme with the standard theme before calling super.onCreate()
         */
        setTheme(R.style.AppTheme_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle =
            new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setCheckedItem(R.id.demo_list);
        if (savedInstanceState == null) {
            navigationView.getMenu().performIdentifierAction(R.id.demo_list, 0);
        }

        ButterKnife.bind(this);
        Logger.d("onCreate");

        textViewTitle = (TextView) navigationView.getHeaderView(0).findViewById(R.id.text_title);
        textViewSubtitle =
            (TextView) navigationView.getHeaderView(0).findViewById(R.id.text_subtitle);
        imageProfile = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.image_profile);
        presenter = new GoogleSignIn(this, this);
        presenter.signInSilently();
    }

    @Override public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody") @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Logger.d("Navigation Item Clicked: " + item.getItemId());

        if (id == R.id.demo_list) {
            fragment = DemoListFragment.newInstance("", "");
            ft.replace(R.id.content_navigation, fragment);
        } else if (id == R.id.plus_one) {
            fragment = PlusOneFragment.newInstance("", "");
            ft.replace(R.id.content_navigation, fragment);
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.sign_in) {
            presenter.signIn();
        } else if (id == R.id.sign_out) {
            presenter.signOut();
        }
        ft.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override public void onFragmentInteraction(Uri uri) {
        Logger.d("onFragmentInteraction: " + uri.getHost());
    }

    @Override public void startDemo(Class className) {
        Intent intent = new Intent(this, className);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, R.anim.slide_out_left);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        presenter.onConnectionFailed(connectionResult);
    }

    @Override public void signInSucceed(GoogleSignInAccount account) {
        textViewTitle.setText(account.getDisplayName());
        textViewSubtitle.setText(account.getEmail());
        if (account.getPhotoUrl() != null) {
            //Glide.with(this).load(account.getPhotoUrl()).into(imageProfile);
            Glide.with(this)
                .load(account.getPhotoUrl())
                .bitmapTransform(new CropCircleTransformation(this))
                .into(imageProfile);
        }

        Toast.makeText(this, "Sign-In succeed", Toast.LENGTH_SHORT).show();
    }

    @Override public void signInFailed() {
        Toast.makeText(this, "Sign-In failed", Toast.LENGTH_SHORT).show();
    }

    @Override public void signedOut() {
        textViewTitle.setText("Click Menu to SignIn");
        textViewSubtitle.setText("not signed in");
        imageProfile.setImageDrawable(null);
        Toast.makeText(this, "User Signed-Out succeed", Toast.LENGTH_SHORT).show();
    }

    @Override protected void onResumeFragments() {
        super.onResumeFragments();
        presenter.start();
    }

    @Override public void setPresenter(SignContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override public void initViews(View view) {

    }
}
