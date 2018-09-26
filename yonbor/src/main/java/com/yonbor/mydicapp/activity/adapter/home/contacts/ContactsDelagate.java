package com.yonbor.mydicapp.activity.adapter.home.contacts;

import android.widget.TextView;

import com.yonbor.baselib.recyclerviewadapter.base.ItemViewDelegate;
import com.yonbor.baselib.recyclerviewadapter.base.ViewHolder;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.model.home.contacts.ContactsCityVo;

/**
 * @Description:
 * @Author: YinYongbo
 * @Time: 2018/9/26 14:43
 */
public class ContactsDelagate implements ItemViewDelegate<ContactsCityVo> {

    private ContactsAdapter adapter;
    private int curViewType;

    public ContactsDelagate(ContactsAdapter adapter, int curViewType) {
        this.adapter = adapter;
        this.curViewType = curViewType;
    }

    @Override
    public int getItemViewLayoutId() {
        if (curViewType == 0) {
            return R.layout.item_wave_contact;
        } else {
            return R.layout.item_pinned_header;
        }
    }

    @Override
    public boolean isForViewType(ContactsCityVo item, int position) {
        int type = adapter.getViewType(item, position);
        return type == curViewType;
    }

    @Override
    public void convert(ViewHolder holder, ContactsCityVo contactsCityVo, int position) {

        if (contactsCityVo.type == 1) {
            TextView city_tip = holder.getView(R.id.city_tip);
            city_tip.setText(contactsCityVo.pys.substring(0, 1));
        } else {
            TextView tv_contact_name = holder.getView(R.id.tv_contact_name);
            tv_contact_name.setText(contactsCityVo.name);
        }

    }
}
