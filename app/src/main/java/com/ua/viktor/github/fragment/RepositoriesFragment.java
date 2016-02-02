package com.ua.viktor.github.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ua.viktor.github.R;
import com.ua.viktor.github.adapter.RepositoriesAdapter;
import com.ua.viktor.github.model.Repositories;
import com.ua.viktor.github.retrofit.RepositoryService;
import com.ua.viktor.github.retrofit.ServiceGenerator;
import com.ua.viktor.github.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RepositoriesFragment extends Fragment {
    private Call<List<Repositories>> call;
    private RepositoriesAdapter mRepositoriesAdapter;
    private static final String KEY_REQ = "key";
    private List<Repositories> mRepositoriesList;

    // TODO: Rename and change types of parameters
    private String key;

    public RepositoriesFragment() {
        // Required empty public constructor
    }

    public static RepositoriesFragment newInstance(String key) {
        RepositoriesFragment fragment = new RepositoriesFragment();
        Bundle args = new Bundle();
        args.putString(KEY_REQ, key);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            key = getArguments().getString(KEY_REQ);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_repositories, container, false);

        mRepositoriesAdapter = new RepositoriesAdapter(mRepositoriesList);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.repo_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mRepositoriesAdapter);
        mRepositoriesAdapter.SetOnClickListener(new RepositoriesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), "" + position, Toast.LENGTH_SHORT).show();
            }
        });

        RepositoryService client = ServiceGenerator.createService(RepositoryService.class);

        if (key.equals(Constants.KEY_YOUR)) {
            call = client.userRepositories("7space7", Constants.CLIENT_ID, Constants.CLIENT_SECRET);
        } else if (key.equals(Constants.KEY_STARRED)) {
            call = client.repoStarred("7space7", Constants.CLIENT_ID, Constants.CLIENT_SECRET);
        }else if(key.equals(Constants.KEY_WATCHED)){
            call = client.repoStarred("7space7", Constants.CLIENT_ID, Constants.CLIENT_SECRET);
        }

        call.enqueue(new Callback<List<Repositories>>() {
            @Override
            public void onResponse(Response<List<Repositories>> response) {
                mRepositoriesList = response.body();
                mRepositoriesAdapter.swapList(mRepositoriesList);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        call.cancel();
    }

}
