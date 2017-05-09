package com.example.shubham.ginnie;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shubham.ginnie.model.Message;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;

public class GinnieFragment extends Fragment implements GinniePresenter.View {

    @BindView(R.id.messages_recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.empty_tv)
    TextView emptyTv;

    @BindView(R.id.input_command)
    EditText inputEt;


    @BindView(R.id.send_command)
    Button sendCommandButton;

    @Inject
    GinniePresenter mPresenter;

    private MessagesListAdapter mAdapter;

    private Unbinder mUnbinder;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inject Presenter
        DaggerGinnieComponent.builder()
                .appComponent(((GinnieApp) getActivity().getApplication()).getAppComponent())
                .build()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ginnie, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        setUpToolBar();
        setUpListView();
        mPresenter.register(this);
        setHasOptionsMenu(true);
        return view;
    }

    private void setUpToolBar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
    }

    private void setUpListView() {
        mAdapter = new MessagesListAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }


    @Override
    public void showMessage(int error) {
        Snackbar.make(getView(), getString(error), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public Observable<String> onMessageEntered() {
        return RxView.clicks(sendCommandButton)
                .switchMap(ignored -> Observable.just(inputEt.getText().toString()));
    }

    @Override
    public void showEmptyView(boolean visible) {
        emptyTv.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void openYoutube(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void showMessages(List<Message> messages) {
        mAdapter.showMessages(messages);
        recyclerView.scrollToPosition(messages.size() - 1);
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public void onDestroy() {
        mPresenter.unregister();
        mUnbinder.unbind();
        super.onDestroy();
    }
}
