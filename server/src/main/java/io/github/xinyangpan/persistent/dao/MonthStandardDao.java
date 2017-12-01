package io.github.xinyangpan.persistent.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import io.github.xinyangpan.persistent.po.MonthStandard;
import io.github.xinyangpan.persistent.po.type.YearMonth;

public interface MonthStandardDao extends PagingAndSortingRepository<MonthStandard, Long> {

	List<MonthStandard> findByGroupIdAndMonth(long groupId, YearMonth yearMonth);

}
