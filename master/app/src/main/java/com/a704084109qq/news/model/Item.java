package com.a704084109qq.news.model;

public interface Item {
    int TYPE_HEADER = 0;
    int TYPE_FOOTER = 1;

    /**
     * 返回item类型，其值不能为0或者1；
     *
     * @return
     */
    int getType();
}
