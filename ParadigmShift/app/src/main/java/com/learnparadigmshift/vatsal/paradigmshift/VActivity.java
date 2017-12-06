package com.learnparadigmshift.vatsal.paradigmshift;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.learnparadigmshift.vatsal.paradigmshift.data.*;
import com.learnparadigmshift.vatsal.paradigmshift.fragments.*;
import com.learnparadigmshift.vatsal.paradigmshift.other.AboutFragment;
import com.learnparadigmshift.vatsal.paradigmshift.other.BackupFragment;

public class VActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainListFragment.IMainListCommunication, AccountingListFragment.IAccountingListCommunication, AddAccountingFragment.IAddAccountingCommunication, InformationAccountingFragment.IInformationAccountingCommunication, InformationFriendFragment.IInformationFriendCommunication, FriendListFragment.IFriendListCommunication, AddFriendFragment.IAddFriendCommunication, BackupFragment.IBackupCommunication, HistoryAccountingListFragment.IHistoryAccountingListCommunication {

    Fragment currentFragment;
    MainListModel currentActiveFriend;
    Friend currentFriend;
    Accounting currentAccounting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initialize();
    }

    private void initialize() {
            changeFragment(new MainListFragment(), false);
    }

    private void changeFragment(Fragment fragment, boolean addToBackStack){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.put_fragment_here, fragment); // f2_container is your FrameLayout container
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if(addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.commit();
        currentFragment=fragment;
    }

    private void popFragment(){
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(currentFragment.getClass().equals(FriendListFragment.class)){
                changeFragment(new MainListFragment(),false);
            }
            else{
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.v, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id==R.id.nav_main_list){
            changeFragment(new MainListFragment(),false);
        }
        else if(id==R.id.nav_friend_list){
            changeFragment(new FriendListFragment(),true);
        }
        else if(id==R.id.nav_backup){
            changeFragment(new BackupFragment(),true);
        }
        else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_about) {
            changeFragment(new AboutFragment(),true);
        }
        else if(id==R.id.nav_help){
            String uri=getResources().getString(R.string.webUrl);
            Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public Friend getCurrentFriend() {
        return currentFriend;
    }

    @Override
    public Accounting getCurrentAccounting() {
        return currentAccounting;
    }

    @Override
    public MainListModel getCurrentActiveFriend() {
        return currentActiveFriend;
    }

    @Override
    public void showHistoryAccountings() {
        changeFragment(new HistoryAccountingListFragment(),true);
    }

    @Override
    public void openAddNewFriend() {
        changeFragment(new AddFriendFragment(),true);
    }

    @Override
    public void showAccountings(MainListModel friend) {
        currentActiveFriend=friend;
        changeFragment(new AccountingListFragment(),true);
    }

    @Override
    public void friendsRemoved() {
        Toast.makeText(this,"Selected friends removed.",Toast.LENGTH_SHORT).show();
        popFragment();
        changeFragment(new MainListFragment(),false);
    }

    @Override
    public void showFriendDetails(Friend fr) {
        currentFriend=fr;
        changeFragment(new InformationFriendFragment(),true);
    }

    @Override
    public void newAccountingAdded(double amount) {
        Toast.makeText(this,"New detail added..",Toast.LENGTH_SHORT).show();
        currentActiveFriend.setAmount(currentActiveFriend.getAmount()+amount);
        popFragment();
    }

    @Override
    public void openAddNewAccounting() {
        changeFragment(new AddAccountingFragment(),true);
    }

    @Override
    public void showMoreInformationOfAccounting(Accounting ac) {
        currentAccounting=ac;
        changeFragment(new InformationAccountingFragment(),true);
    }

    @Override
    public void accountingsRemoved() {
        Toast.makeText(this,"Selected accountings removed..",Toast.LENGTH_SHORT);
        popFragment();
    }

    @Override
    public void accountCleared() {
        Toast.makeText(this,"Account Cleared..!!",Toast.LENGTH_SHORT).show();
        changeFragment(new MainListFragment(),false);
    }

    @Override
    public void newFriendAdded() {
        Toast.makeText(this,"Friend Added..",Toast.LENGTH_SHORT).show();
        popFragment();
    }

    @Override
    public void dataRestored() {
        popFragment();
    }
}
