package com.yonbor.mydicapp.activity.adapter.home.contacts;

import com.yonbor.baselib.recyclerviewadapter.MultiItemTypeAdapter;
import com.yonbor.mydicapp.model.home.contacts.ContactsCityVo;

import java.util.List;

/**
 * @Description:
 * @Author: YinYongbo
 * @Time: 2018/9/26 13:53
 */
public class ContactsAdapter extends MultiItemTypeAdapter<ContactsCityVo> {


    public ContactsAdapter(List<ContactsCityVo> datas) {
        super(datas);
        addItemViewDelegate(new ContactsDelagate(this, 0)); // 联系人item
        addItemViewDelegate(new ContactsDelagate(this, 1)); // 首字母header
    }

    protected int getViewType(ContactsCityVo item, int position) {
        return item.type;
    }


    public int getLetterPosition(String letter) {
        for (int i = 0; i < getDatas().size(); i++) {
            if (getDatas().get(i).type == 1 && getDatas().get(i).pys.equals(letter)) {
                return i;
            }
        }
        return -1;
    }
}
