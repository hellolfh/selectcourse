package com.xu.select.service.impl;

import com.xu.select.bean.Page;
import com.xu.select.service.PageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PageServiceImpl implements PageService {
    private int one_page_size = 60;
    @Override
    public Page subList(int page, List list) {
        Page paging = new Page();
        if (list.size() <= (page - 1) * one_page_size) {
            page = 1;
        }
        paging.setCurrentPage(page);
        int count = list.size();
        paging.setTotalPage(count % one_page_size == 0 ? count / one_page_size : count / one_page_size + 1);
        paging.setPageSize(one_page_size);
        paging.setStar((paging.getCurrentPage() - 1) * paging.getPageSize());
        paging.setDataList(list.subList(paging.getStar(), count - paging.getStar() > paging.getPageSize() ? paging.getStar() + paging.getPageSize() : count));
        return paging;
    }
}
