package com.app.dao;

import com.app.entity.HistoryEnt;
import com.app.exception.SystemException;

import java.util.List;

public interface HistoryDAO {
    public List<HistoryEnt> getHistoryList() throws SystemException;
}
