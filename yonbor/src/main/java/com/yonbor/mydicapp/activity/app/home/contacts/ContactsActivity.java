package com.yonbor.mydicapp.activity.app.home.contacts;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yonbor.baselib.widget.AppActionBar;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.activity.adapter.home.contacts.ContactsAdapter;
import com.yonbor.mydicapp.activity.base.BaseActivity;
import com.yonbor.mydicapp.model.home.contacts.ContactsCityVo;
import com.yonbor.mydicapp.view.sidebar.PinnedHeaderDecoration;
import com.yonbor.mydicapp.view.sidebar.WaveSideBarView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/home/contacts/contacts")
public class ContactsActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.side_view)
    WaveSideBarView sideBarView;

    private ContactsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        ButterKnife.bind(this);
        findView();

    }

    @Override
    public void findView() {
        findActionBar();
        actionBar.setTitle("联系人侧边栏快速导航");
        actionBar.setBackAction(new AppActionBar.Action() {

            @Override
            public void performAction(View view) {
                back();
            }

            @Override
            public int getDrawable() {
                return R.drawable.btn_back;
            }

            @Override
            public String getText() {
                return null;
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final PinnedHeaderDecoration decoration = new PinnedHeaderDecoration();
        decoration.registerTypePinnedHeader(1, new PinnedHeaderDecoration.PinnedHeaderCreator() {
            @Override
            public boolean create(RecyclerView parent, int adapterPosition) {
                return true;
            }
        });
        recyclerView.addItemDecoration(decoration);


        new Thread(new Runnable() {
            @Override
            public void run() {
                Type listType = new TypeToken<ArrayList<ContactsCityVo>>() {
                }.getType();
                Gson gson = new Gson();
                final List<ContactsCityVo> list = gson.fromJson(ContactsCityVo.DATA, listType);
                Collections.sort(list, new ComparatorLetter());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new ContactsAdapter(list);
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        }).start();

        sideBarView.setOnTouchLetterChangeListener(new WaveSideBarView.OnTouchLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                int pos = adapter.getLetterPosition(letter);

                if (pos != -1) {
                    recyclerView.scrollToPosition(pos);
                    LinearLayoutManager mLayoutManager =
                            (LinearLayoutManager) recyclerView.getLayoutManager();
                    mLayoutManager.scrollToPositionWithOffset(pos, 0);
                }
            }
        });


    }
}
