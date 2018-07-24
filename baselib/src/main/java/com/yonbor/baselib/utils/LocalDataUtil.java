package com.yonbor.baselib.utils;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * yonbor605
 */
public class LocalDataUtil {

    public static final String QUEUE_ORG_HIS_LIST = "queue_org_his_list";//排队叫号
    public static final String CONSULT_SEARCH = "consultSearch";
    public static final String PHONE = "phone";
    private static LocalDataUtil localDataUtil = null;
    private static String uniqueKey;

    private LocalDataUtil() {

    }

    public static void init(String key) {
        uniqueKey = key;
    }

    public static LocalDataUtil getInstance() {
        if (localDataUtil == null) {
            localDataUtil = new LocalDataUtil();
        }
        return localDataUtil;
    }

    /**
     * 设置对象数据到本地
     **/
    public void saveDatatoLocal(String key, Object data) {
        if (data != null) {
            SharedPreferencesUtil.getInstance().setStringData(key, JSON.toJSONString(data));
        } else {
            SharedPreferencesUtil.getInstance().setStringData(key, "");
        }
    }

    public void saveDatatoLocal(String key, boolean data) {
        SharedPreferencesUtil.getInstance().setBooleanData(key, data);
    }

    /**
     * 从本地获取对象数据
     **/
    public <T> T getDataFromLocal(String key, Class<T> clazz) {
        try {
            String json = SharedPreferencesUtil.getInstance().getStringData(key);
            if (json != null && !json.equals("")) {
                return JSON.parseObject(json, clazz);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从本地获取列表数据
     **/
    public <T> List<T> getListFromLocal(String key, Class<T> clazz) {
        try {
            String json = SharedPreferencesUtil.getInstance().getStringData(key);
            if (json != null && !json.equals("")) {
                return JSON.parseArray(json, clazz);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 咨询记录搜索的历史记录
     *
     * @param stringList
     */
    public void setConsultSearchList(List<String> stringList) {
        saveDatatoLocal(CONSULT_SEARCH, stringList);
    }

    /**
     * 咨询记录搜索搜索的历史记录
     */
    public List<String> getConsultSearchList() {
        return getListFromLocal(CONSULT_SEARCH, String.class);
    }

    /**
     * 获取唯一值
     *
     * @param str
     * @return
     */
    private String getUnique(String str) {
        return uniqueKey + str;
    }


    public String getPhone() {
        String phone = SharedPreferencesUtil.getInstance().getStringData(PHONE);
        return phone;
    }

    public void setPhone(String phoneStr) {
        SharedPreferencesUtil.getInstance().setStringData(PHONE, phoneStr);
    }


//    public List<ModuleVo> getModuleVoList() {
//        return getListFromLocal("moduleVoList", ModuleVo.class);
//    }

//    public void setModuleVoList(List<ModuleVo> moduleVoList) {
//        saveDatatoLocal("moduleVoList", moduleVoList);
//    }

    /**
     * 保存轮播图列表
     */
//    public void setBannerList(String type, List<BannerVo> list) {
//        if (type != null && !type.equals("")) {
//            type = "banner_" + type;
//
//            saveDatatoLocal(type, list);
//        }
//    }

    /**
     * 获取轮播图列表
     */
//    public List<BannerVo> getBannerList(String type) {
//        if (type != null && !type.equals("")) {
//            type = "banner_" + type;
//            return getListFromLocal(type, BannerVo.class);
//        }
//        return null;
//    }

//    public void setBanner(String type, BannerVo vo) {
//        if (type != null && !type.equals("")) {
//            type = "banner_" + type;
//
//            saveDatatoLocal(type, vo);
//        }
//    }

//    public BannerVo getBanner(String type) {
//        if (type != null && !type.equals("")) {
//            type = "banner_" + type;
//            return getDataFromLocal(type, BannerVo.class);
//        }
//        return null;
//    }

    /**
     * 保存排队叫号近期选择过的医院列表
     *
     * @param dataList
     */
//    public void saveQueueOrgList(ArrayList<OrgBaseVo> dataList) {
//        saveDatatoLocal(getUnique(QUEUE_ORG_HIS_LIST), dataList);
//    }

    /**
     * 获取排队叫号近期选择过的医院列表
     *
     * @return
     */
//    public ArrayList<OrgBaseVo> getQueueOrgList() {
//        List<OrgBaseVo> list = getListFromLocal(getUnique(QUEUE_ORG_HIS_LIST), OrgBaseVo.class);
//        if (list != null) {
//            return new ArrayList<>(list);
//        }
//        return null;
//    }


}
