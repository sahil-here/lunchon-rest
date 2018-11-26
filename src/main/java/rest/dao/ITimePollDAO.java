package rest.dao;

import exception.LOException;
import rest.dao.entity.TimePoll;

public interface ITimePollDAO {
    TimePoll createOrUpdate(TimePoll timePoll) throws LOException;
}
