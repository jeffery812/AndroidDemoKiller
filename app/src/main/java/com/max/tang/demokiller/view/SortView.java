package com.max.tang.demokiller.view;

import java.util.List;

/**
 * Created by zhihuitang on 2016-11-29.
 */

public interface SortView<E> {
    void updateUI(final List<E> data);
    void finish(final String text);

}
