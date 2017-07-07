package com.max.tang.demokiller.main.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.max.tang.demokiller.BuildConfig;
import com.max.tang.demokiller.DataLoader;
import com.max.tang.demokiller.R;
import com.max.tang.demokiller.adapter.DemoAdapter;
import com.max.tang.demokiller.fragment.OnFragmentInteractionListener;
import com.max.tang.demokiller.itemanimator.ItemAnimatorFactory;
import com.max.tang.demokiller.model.DemoEntity;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DemoListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DemoListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    @BindView(R.id.recycler_view_main)
    RecyclerView mRecyclerView;

    @BindView(R.id.text_view_build_info) TextView mTextViewBuildInfo;

    private OnFragmentInteractionListener mListener;

    public DemoListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DemoListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DemoListFragment newInstance(String param1, String param2) {
        DemoListFragment fragment = new DemoListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_demo_list_fragmenty, container, false);
        ButterKnife.bind(this, view);

        // load content
        List<DemoEntity> demoEntities = DataLoader.getInstance().getTestList();
        DemoAdapter demoAdapter = new DemoAdapter(mListener, demoEntities);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(ItemAnimatorFactory.slidein()); //设置Iem动画
        mRecyclerView.setAdapter(demoAdapter);

        Date buildDate = new Date(BuildConfig.TIMESTAMP);
        final String text = String.format(Locale.getDefault(), "%s(%d) Build at %s", BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE, buildDate.toString());
        mTextViewBuildInfo.setText(text);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
