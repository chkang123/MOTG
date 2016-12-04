package ml.diony.motg.Display;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ml.diony.motg.R;

public class MainActivity extends AppCompatActivity {
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    public static final int getColor(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return android.support.v4.content.ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    private SectionsPagerAdapter mSectionsPagerAdapter;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_main);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(1).select();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        Log.i("TEST", "작동하나?");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("식당명으로 검색..");
        SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(getColor(this, R.color.white));
        searchAutoComplete.setTextColor(getColor(this, R.color.white));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                View rootView = inflater.inflate(R.layout.fragment_main, container, false);
                Button btn_kor = (Button) rootView.findViewById(R.id.kor_btn);
                btn_kor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent1 = new Intent(getContext(), ml.diony.motg.Display.ReslistActivity.class);
                        intent1.putExtra("rtype", "한식");
                        startActivity(intent1);
                    }
                });

                Button btn_jap = (Button) rootView.findViewById(R.id.jap_btn);
                btn_jap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent1 = new Intent(getContext(), ReslistActivity.class);
                        intent1.putExtra("rtype", "일식");
                        startActivity(intent1);
                    }
                });

                Button btn_chn = (Button) rootView.findViewById(R.id.chn_btn);
                btn_chn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent1 = new Intent(getContext(), ReslistActivity.class);
                        intent1.putExtra("rtype", "중식");
                        startActivity(intent1);
                    }
                });

                Button btn_wes = (Button) rootView.findViewById(R.id.wes_btn);
                btn_wes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent1 = new Intent(getContext(), ReslistActivity.class);
                        intent1.putExtra("rtype", "양식");
                        startActivity(intent1);
                    }
                });
                return rootView;
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                View rootView = inflater.inflate(R.layout.fragment_main2, container, false);

                Button btn_yukang = (Button) rootView.findViewById(R.id.button_yukang);
                btn_yukang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), LocalRlistActivity.class);
                        intent.putExtra("location", "유강");
                        startActivity(intent);
                    }
                });

                Button btn_hyoja = (Button) rootView.findViewById(R.id.button_hyoja);
                btn_hyoja.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), LocalRlistActivity.class);
                        intent.putExtra("location", "효자");
                        startActivity(intent);
                    }
                });

                Button btn_hyosung = (Button) rootView.findViewById(R.id.button_hyosung);
                btn_hyosung.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), LocalRlistActivity.class);
                        intent.putExtra("location", "효성로");
                        startActivity(intent);
                    }
                });

                Button btn_edong = (Button) rootView.findViewById(R.id.button_edong);
                btn_edong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), LocalRlistActivity.class);
                        intent.putExtra("location", "이동");
                        startActivity(intent);
                    }
                });

                Button btn_ssangsa = (Button) rootView.findViewById(R.id.button_ssangsa);
                btn_ssangsa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), LocalRlistActivity.class);
                        intent.putExtra("location", "쌍사");
                        startActivity(intent);
                    }
                });

                Button btn_6st = (Button) rootView.findViewById(R.id.button_6st);
                btn_6st.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), LocalRlistActivity.class);
                        intent.putExtra("location", "육거리");
                        startActivity(intent);
                    }
                });

                Button btn_yeongill = (Button) rootView.findViewById(R.id.button_yeongill);
                btn_yeongill.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), LocalRlistActivity.class);
                        intent.putExtra("location", "영일대");
                        startActivity(intent);
                    }
                });

                Button btn_today = (Button) rootView.findViewById(R.id.button_todaymenu);
                btn_today.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Log.i("으악", "여기까진 되는데.");
                        Intent intent = new Intent(getContext(), TodaymenuActivity.class);
                        startActivity(intent);
                    }
                });

                return rootView;
            } else {
                View rootView = inflater.inflate(R.layout.fragment_main3, container, false);
                Button btn_view_hs = (Button) rootView.findViewById(R.id.View_history);
                Button btn_setting = (Button) rootView.findViewById(R.id.Setting_rank);
                btn_view_hs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent1 = new Intent(getContext(), ml.diony.motg.Display.HistoryActivity.class);
                        startActivity(intent1);
                    }
                });
                btn_setting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent3 = new Intent(getContext(), SettingActivity.class);
                        startActivity(intent3);
                    }
                });
                return rootView;

            }
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "업종";
                case 1:
                    return "지역";
                case 2:
                    return "설정";
            }
            return null;
        }
    }
}
