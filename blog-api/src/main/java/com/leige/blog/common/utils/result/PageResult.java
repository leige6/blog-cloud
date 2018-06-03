package com.leige.blog.common.utils.result;

import java.util.List;


/**
  * @Title:
  * @Description: 分页实体类，用于与easyui整合
  * @param 
  * @return 
  * @author 张亚磊
  * @date 2018年03月08日 15:33:51
  */
public class PageResult {
    private long total; // 总记录
    private List rows; //显示的记录
    public PageResult() {}

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
