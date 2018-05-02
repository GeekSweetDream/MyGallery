package com.dreamsofpines.mygallery.util;

import com.dreamsofpines.mygallery.model.LentaImageItem;
import com.dreamsofpines.mygallery.ui.interfaces.Notify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SaveImageUtils {

    private static final int ADD_ITEM = 0;
    private static final int REMOVE_ITEM = 1;
    private static final int CLEAR_ALL_ITEM = 2;


    private static HashMap<String,LentaImageItem> list = new HashMap<>();
    private static HashMap<String,Notify> subs = new HashMap<>();

    public static void addSelect(LentaImageItem item){
        list.put(item.getName(),item);
        notifySubs(ADD_ITEM);
    }

    public static void removeSelect(LentaImageItem item){
        list.remove(item.getName());
        notifySubs(REMOVE_ITEM);
    }

    public static void addSub(String name,Notify notify){
        subs.put(name,notify);
    }

    public static void removeSub(String name){
        subs.remove(name);
    }

    public static void clearAll(){
        for(Map.Entry<String,LentaImageItem> entry: list.entrySet()){
            entry.getValue().setSelect(false);
        }
        notifySubs(CLEAR_ALL_ITEM);
        list.clear();
    }

    private static void notifySubs(int action){
        int oldSize = 0;
        int newSize = 0;
        switch (action){
            case ADD_ITEM:{
                oldSize = list.size()-1;
                newSize = list.size();
                break;
            }
            case REMOVE_ITEM:{
                oldSize = list.size()+1;
                newSize = list.size();
                break;
            }
            case CLEAR_ALL_ITEM:{
                oldSize = list.size();
                newSize = 0;
                break;
            }
        }
        for(Map.Entry<String,Notify> entry: subs.entrySet()){
            entry.getValue().notify(oldSize,newSize);
        }
    }



}
