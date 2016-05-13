package com.qiito.umepal.holder;

import java.util.List;

/**
 * Created by abin on 2/9/15.
 */
public class Category {

    private int category_id;
    private String category_name;
    private String category_icon;
    List<SubCategories>sub_categories;

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_icon() {
        return category_icon;
    }

    public void setCategory_icon(String category_icon) {
        this.category_icon = category_icon;
    }

    public List<SubCategories> getSub_categories() {
        return sub_categories;
    }

    public void setSub_categories(List<SubCategories> sub_categories) {
        this.sub_categories = sub_categories;
    }
}
