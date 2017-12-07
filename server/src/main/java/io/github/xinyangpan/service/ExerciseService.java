package io.github.xinyangpan.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.github.xinyangpan.core.BusinessUtils;
import io.github.xinyangpan.core.standard.Standard;
import io.github.xinyangpan.persistent.dao.CustomerDao;
import io.github.xinyangpan.persistent.dao.CustomerStatusDao;
import io.github.xinyangpan.persistent.dao.ExerciseDao;
import io.github.xinyangpan.persistent.dao.ExerciseTypeDao;
import io.github.xinyangpan.persistent.dao.MonthStandardDao;
import io.github.xinyangpan.persistent.po.CustomerPo;
import io.github.xinyangpan.persistent.po.CustomerStatusPo;
import io.github.xinyangpan.persistent.po.ExercisePo;
import io.github.xinyangpan.persistent.po.ExerciseTypePo;
import io.github.xinyangpan.persistent.po.MonthStandard;
import io.github.xinyangpan.persistent.po.type.YearMonth;
import io.github.xinyangpan.vo.ExerciseVo;
import io.github.xinyangpan.vo.MonthSummary;
import io.github.xinyangpan.vo.Rank;
import io.github.xinyangpan.vo.RankEntry;

@Service
@Transactional
public class ExerciseService {
	@Autowired
	private ExerciseDao exerciseDao;
	@Autowired
	private ExerciseTypeDao exerciseTypeDao;
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private CustomerStatusDao customerStatusDao;
	@Autowired
	private MonthStandardDao monthStandardDao;
	

	public Rank rank(long groupId, YearMonth yearMonth) {
		List<ExercisePo> exercisePos = exerciseDao.findByGroupIdAndMonth(groupId, yearMonth);
		MonthStandard monthStandard = monthStandardDao.findByGroupIdAndMonth(groupId, yearMonth);
		Standard standard = Standard.getStandard(monthStandard);
		Map<Long, CustomerPo> id2CustomerPo = customerDao.id2CustomerPo(groupId);
		Map<Long, CustomerStatusPo> id2CustomerStatusPo = customerStatusDao.id2CustomerStatusPo(groupId, yearMonth);
		String pioneer = BusinessUtils.getPioneer(monthStandard, id2CustomerPo);
		// rankEntries
		Map<Long, RankEntry> customerId2RankItem = customerId2RankItem(exercisePos, id2CustomerPo, id2CustomerStatusPo, standard);
		List<RankEntry> rankEntries = Lists.newArrayList(customerId2RankItem.values());
		Comparator<RankEntry> comparator = Comparator
			.comparing(RankEntry::isLeave)
			.thenComparing(RankEntry::getJogAmount, Comparator.reverseOrder())
			.thenComparing(RankEntry::getCount, Comparator.reverseOrder());
		Collections.sort(rankEntries, comparator);
		// 
		Rank rank = new Rank();
		rank.setYearMonth(yearMonth);
		rank.setRankEntries(rankEntries);
		rank.setPioneer(pioneer);
		return rank;
	}

	private Map<Long, RankEntry> customerId2RankItem(List<ExercisePo> exercisePos, Map<Long, CustomerPo> id2CustomerPo, Map<Long, CustomerStatusPo> id2CustomerStatusPo, Standard standard) {
		//
		Map<Long, RankEntry> customerId2RankItem = Maps.newHashMap(Maps.transformValues(id2CustomerPo, RankEntry::from));
		for (ExercisePo exercisePo : exercisePos) {
			long customerId = exercisePo.getCustomerId();
			RankEntry rankEntry = customerId2RankItem.get(customerId);
			rankEntry.setCount(rankEntry.getCount() + 1);
			if (exercisePo.getTypeId() == 1) {
				rankEntry.setJogAmount(rankEntry.getJogAmount().add(exercisePo.getAmount()));
				rankEntry.setJogCount(rankEntry.getJogCount() + 1);
			}
		}
		for (RankEntry rankEntry : customerId2RankItem.values()) {
			long customerId = rankEntry.getCustomerId();
			if (standard.eval(rankEntry)) {
				rankEntry.setMeetStandard(true);
			}
			if (BusinessUtils.isLeave(id2CustomerStatusPo.get(customerId))) {
				rankEntry.setLeave(true);
			}
		}
		return customerId2RankItem;
	}

	public void deleteExercisesByIds(List<Long> ids) {
		exerciseDao.deleteExercisesByIds(ids);
	}

	public MonthSummary monthHistory(long customerId, YearMonth yearMonth) {
		List<ExercisePo> exercisePos = exerciseDao.findByCustomerIdAndMonthOrderByTimeAsc(customerId, yearMonth);
		Map<Long, ExerciseTypePo> id2ExerciseTypePo = exerciseTypeDao.id2ExerciseTypePo();
		List<ExerciseVo> exerciseVos = exercisePos.stream()
			.map(exerciseVo -> ExerciseVo.from(exerciseVo, id2ExerciseTypePo.get(exerciseVo.getTypeId())))
			.collect(Collectors.toList());
		// 
		MonthSummary history = new MonthSummary();
		history.setYearMonth(yearMonth);
		history.setExerciseVos(exerciseVos);
		history.generateSummary(id2ExerciseTypePo);
		return history;
	}

}
