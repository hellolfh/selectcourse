package com.xu.select.service;


import com.xu.select.bean.Page;

import java.util.List;

public interface PageService {
    public Page subList(int page, List list);
}
