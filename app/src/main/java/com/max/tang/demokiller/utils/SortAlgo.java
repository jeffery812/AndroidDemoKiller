package com.max.tang.demokiller.utils;

import com.max.tang.demokiller.view.SortView;
import java.util.List;

/**
 * Created by zhihuitang on 2016-11-29.
 */

public class SortAlgo {
    static public void bubbleSort(SortView view, List<Integer> data) {
        for( int i = 0; i < data.size(); i++ ){
            for (int j = i+1; j < data.size(); j++) {
                if(data.get(i) > data.get(j)) {
                    int tmp = data.get(i);
                    data.set(i, data.get(j));
                    data.set(j, tmp);
                    view.updateUI(data);
                }

            }
        }
    }
}
