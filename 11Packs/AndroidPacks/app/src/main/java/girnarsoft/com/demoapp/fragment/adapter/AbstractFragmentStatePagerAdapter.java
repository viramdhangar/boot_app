package girnarsoft.com.demoapp.fragment.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The type Abstract fragment pager adapter.
 *
 * @param <T> the type parameter
 */
public class AbstractFragmentStatePagerAdapter<T> extends FragmentStatePagerAdapter {
    /**
     * The Fragments.
     */
    private List<T> fragments;
    private List<String> fragmentTitles;

    /**
     * Instantiates a new Abstract fragment pager adapter.
     *
     * @param fm the fm
     */
    public AbstractFragmentStatePagerAdapter(@Nullable FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
        fragmentTitles = new ArrayList<>();
    }

    /**
     * Instantiates a new Abstract fragment pager adapter.
     *
     * @param fragmentManager the fragmentManager
     * @param fragmentTitles  the fragment titles
     * @param fragments       the fragments
     */
    public AbstractFragmentStatePagerAdapter(@Nullable FragmentManager fragmentManager, List<String> fragmentTitles, List<T> fragments) {
        super(fragmentManager);
        this.fragmentTitles = fragmentTitles;
        this.fragments = fragments;
    }


    /**
     * Instantiates a new Abstract fragment state pager adapter.
     *
     * @param fragmentManager the fragment manager
     * @param fragmentTitles  the fragment titles
     * @param fragments       the fragments
     */
    public AbstractFragmentStatePagerAdapter(@Nullable FragmentManager fragmentManager, String[] fragmentTitles, List<T> fragments) {
        super(fragmentManager);
        this.fragments = fragments;
        if (null != fragmentTitles) {
            this.fragmentTitles = Arrays.asList(fragmentTitles);
        }
    }

    /**
     * Instantiates a new Abstract fragment state pager adapter.
     *
     * @param fragmentManager the fragment manager
     * @param fragments       the fragments
     */
    public AbstractFragmentStatePagerAdapter(@Nullable FragmentManager fragmentManager, List<T> fragments) {
        super(fragmentManager);
        this.fragments = fragments;

    }

    /**
     * Add fragment.
     *
     * @param fragmentTitle the fragment title
     * @param fragmentList  the fragment
     */
    public void addFragment(String[] fragmentTitle, @Nullable List<T> fragmentList) {
        if (null == fragments) {
            this.fragments = new ArrayList<>();
        }
        if (null == fragmentTitles) {
            this.fragmentTitles = new ArrayList<>();
        }
        if (null != fragmentList) {
            this.fragments.addAll(fragmentList);
        }
        fragmentTitles.addAll(Arrays.asList(fragmentTitle));
    }

    /**
     * Add fragment.
     *
     * @param fragment the fragment
     * @param title    the title
     */
    public void addFragment(T fragment, @Nullable String title) {
        if (null == fragments) {
            fragments = new ArrayList<>();
        }
        if (null == fragmentTitles) {
            fragmentTitles = new ArrayList<>();
        }
        fragments.add(fragment);
        fragmentTitles.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return (Fragment) fragments.get(position);
    }

    @Override
    public int getCount() {
        return null != fragments ? fragments.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null != fragmentTitles ? fragmentTitles.get(position) : "";
    }
}