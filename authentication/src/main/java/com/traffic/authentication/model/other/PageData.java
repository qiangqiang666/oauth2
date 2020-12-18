package com.traffic.authentication.model.other;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;

@Data
public class PageData<T> {
    List<T> list;
    int page;
    int size;
    int pages;
    int total;

    public PageData(IPage p) {
        if (p != null) {
            list = p.getRecords();
            page = (int) p.getCurrent();
            size = (int) p.getSize();
            pages = (int) p.getPages();
            total = (int) p.getTotal();
        }
    }
}
