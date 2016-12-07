package ml.diony.motg.Display;

import android.app.Activity;
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

import ml.diony.motg.Authentication.Base;
import ml.diony.motg.Communication.Interaction;
import ml.diony.motg.R;

//세 가지의 홈화면을 출력해주는 Class이다.
//TabLayout을 이용해 swipe 또는 탭 클릭을 통해 다른 탭으로 이동할 수 있다
//첫번째 홈화면은 업종별로 분류된 버튼이 있어 버튼을 누르면 각 업종에 해당하는 식당 목록을 볼 수 있다.
//두번째 홈화면은 지도 위에 지역별로 분류된 버튼이 있고 각 버튼은 해당 지역에 위치한 식당 목록을 보여준다.
//세번째 홈화면은 과거 방문기록을 보거나 평가기준의 우선순위를 설정할 수 있는 기능으로 연결되는 버튼들이 있다.
//어플리케이션이 처음 실행되면 두번째 홈화면이 가장 먼저 표시된다.
public class MainActivity extends AppCompatActivity {
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */


    Interaction IA = new Interaction(this, this);
    Context CONTEXT = this;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    public static final int getColor(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return android.support.v4.content.ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("LoginCheck", (new Base()).getLoginInformation().toString());

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

        //두번째 홈화면이 처음에 출력되도록 설정한다.
        tabLayout.getTabAt(1).select();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        Log.i("TEST", "작동하나?");
        getMenuInflater().inflate(R.menu.menu_main, menu);

        //검색기능을 제공한다.
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("식당명으로 검색..");
        SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(getColor(this, R.color.white));
        searchAutoComplete.setTextColor(getColor(this, R.color.white));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            //검색창에서 입력이 완료되어 사용자가 검색 버튼을 누른 경우 아래의 코드가 실행된다.
            @Override
            public boolean onQueryTextSubmit(String s) {

                //검색 결과에 해당하는 식당들의 리스트를 출력하기 위해 SearchRlistActivity로 넘어간다.
                Intent intent1 = new Intent(CONTEXT, ml.diony.motg.Display.SearchRlistActivity.class);
                IA.getSpecified("ALL", "NAME", s, intent1);

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

            final Interaction IA = new Interaction((Activity) getContext(), getContext());

            //첫번째 홈화면에 존재하는 버튼들의 다음 행동을 설정해준다.
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                View rootView = inflater.inflate(R.layout.fragment_main, container, false);
                Button btn_kor = (Button) rootView.findViewById(R.id.kor_btn);
                btn_kor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent1 = new Intent(getContext(), ml.diony.motg.Display.ReslistActivity.class);
                        intent1.putExtra("rtype", "한식");
                        IA.getAll("KR", intent1);

                    }
                });

                Button btn_jap = (Button) rootView.findViewById(R.id.jap_btn);
                btn_jap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent1 = new Intent(getContext(), ReslistActivity.class);
                        intent1.putExtra("rtype", "일식");
                        IA.getAll("JP", intent1);
                    }
                });

                Button btn_chn = (Button) rootView.findViewById(R.id.chn_btn);
                btn_chn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent1 = new Intent(getContext(), ReslistActivity.class);
                        intent1.putExtra("rtype", "중식");
                        IA.getAll("CN", intent1);
                    }
                });

                Button btn_wes = (Button) rootView.findViewById(R.id.wes_btn);
                btn_wes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent1 = new Intent(getContext(), ReslistActivity.class);
                        intent1.putExtra("rtype", "양식");
                        IA.getAll("WE", intent1);
                    }
                });
                return rootView;
            }

            //두번째 홈화면에 존재하는 버튼들의 다음 행동을 설정해준다.
            else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                View rootView = inflater.inflate(R.layout.fragment_main2, container, false);

                Button btn_yukang = (Button) rootView.findViewById(R.id.button_yukang);
                btn_yukang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), LocalRlistActivity.class);
                        intent.putExtra("location", "유강");
                        IA.getSpecified("ALL", "REGION", "6", intent);
                    }
                });

                Button btn_hyoja = (Button) rootView.findViewById(R.id.button_hyoja);
                btn_hyoja.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), LocalRlistActivity.class);
                        intent.putExtra("location", "효자");
                        IA.getSpecified("ALL", "REGION", "1", intent);
                    }
                });

                Button btn_hyosung = (Button) rootView.findViewById(R.id.button_hyosung);
                btn_hyosung.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), LocalRlistActivity.class);
                        intent.putExtra("location", "효성로");
                        IA.getSpecified("ALL", "REGION", "5", intent);
                    }
                });

                Button btn_edong = (Button) rootView.findViewById(R.id.button_edong);
                btn_edong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), LocalRlistActivity.class);
                        intent.putExtra("location", "이동");
                        IA.getSpecified("ALL", "REGION", "0", intent);
                    }
                });

                Button btn_ssangsa = (Button) rootView.findViewById(R.id.button_ssangsa);
                btn_ssangsa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), LocalRlistActivity.class);
                        intent.putExtra("location", "쌍사");
                        IA.getSpecified("ALL", "REGION", "4", intent);
                    }
                });

                Button btn_6st = (Button) rootView.findViewById(R.id.button_6st);
                btn_6st.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), LocalRlistActivity.class);
                        intent.putExtra("location", "육거리");
                        IA.getSpecified("ALL", "REGION", "2", intent);
                    }
                });

                Button btn_yeongill = (Button) rootView.findViewById(R.id.button_yeongill);
                btn_yeongill.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), LocalRlistActivity.class);
                        intent.putExtra("location", "영일대");
                        IA.getSpecified("ALL", "REGION", "3", intent);
                    }
                });

                Button btn_today = (Button) rootView.findViewById(R.id.button_todaymenu);
                btn_today.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Log.i("으악", "여기까진 되는데.");
                        Intent intent = new Intent(getContext(), TodaymenuActivity.class);
                        IA.getRecommended(intent);
                    }
                });

                return rootView;
            }

            //세번째 홈화면에 존재하는 버튼들의 다음 행동을 설정해준다.
            else {
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

        //전체 페이지 수를 설정해준다.
        @Override
        public int getCount() {
            return 3;
        }

        //탭 부분에 나타낼 문자들을 설정해준다.
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
