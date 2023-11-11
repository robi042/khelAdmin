package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import code.fortomorrow.kheloNowAdmin.Fragments.Fragment_accepted;
import code.fortomorrow.kheloNowAdmin.Fragments.Fragment_rejected;
import code.fortomorrow.kheloNowAdmin.R;

public class Withdraw_money_list_activity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_money_list);

        init_view();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());
        viewPageAdapter.addFragment(new Fragment_accepted("Withdraw Money"), "Accepted");
        viewPageAdapter.addFragment(new Fragment_rejected("Withdraw Money"), "Rejected");

        //viewPager.setCurrentItem(1);
        viewPager.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void init_view() {

        backButton = findViewById(R.id.backButton);
        tabLayout = findViewById(R.id.tab_layoutID);
        viewPager = findViewById(R.id.view_pagerLayoutID);
    }

    class ViewPageAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        public ViewPageAdapter(@NonNull FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}