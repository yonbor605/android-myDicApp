package com.yonbor.mydicapp.activity.app.home.contacts;


import com.yonbor.mydicapp.model.home.contacts.ContactsCityVo;

import java.util.Comparator;


/**
 * @author vondear
 */
public class ComparatorLetter implements Comparator<ContactsCityVo> {

    @Override
    public int compare(ContactsCityVo l, ContactsCityVo r) {
        if (l == null || r == null) {
            return 0;
        }

        String lhsSortLetters = l.pys.substring(0, 1).toUpperCase();
        String rhsSortLetters = r.pys.substring(0, 1).toUpperCase();
        if (lhsSortLetters == null || rhsSortLetters == null) {
            return 0;
        }
        return lhsSortLetters.compareTo(rhsSortLetters);
    }
}