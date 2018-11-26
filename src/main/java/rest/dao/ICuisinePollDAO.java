package rest.dao;

import exception.LOException;
import rest.dao.entity.CuisinePoll;

import java.util.List;

public interface ICuisinePollDAO {
    CuisinePoll createOrUpdate(CuisinePoll cuisinePoll) throws LOException;
}
