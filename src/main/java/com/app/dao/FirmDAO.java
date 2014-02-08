package com.app.dao;

import com.app.entity.FirmEnt;
import com.app.exception.BusinessException;

import java.util.List;

public interface FirmDAO {
    public FirmEnt getFirm(final String title);
    public List<FirmEnt> getFirmList();
    public FirmEnt addFirm(final FirmEnt firm) throws BusinessException;
    public FirmEnt addFirm(final String title, final Integer year, final String county) throws BusinessException;
    public FirmEnt updateFirm(final FirmEnt firm);
    public void deleteFirm(final String title) throws BusinessException;
}
