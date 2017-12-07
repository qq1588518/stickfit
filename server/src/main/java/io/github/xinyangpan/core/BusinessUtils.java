package io.github.xinyangpan.core;

import java.util.Map;

import io.github.xinyangpan.persistent.po.CustomerPo;
import io.github.xinyangpan.persistent.po.CustomerStatusPo;
import io.github.xinyangpan.persistent.po.MonthStandard;

public class BusinessUtils {

	public static boolean isLeave(Map<Long, CustomerStatusPo> id2CustomerStatusPo, long customerId) {
		CustomerStatusPo customerStatusPo = id2CustomerStatusPo.get(customerId);
		if (customerStatusPo == null) {
			return false;
		}
		return customerStatusPo.getStatusEnum() == StatusEnum.LEAVE;
	}

	public static boolean isLeave(CustomerStatusPo customerStatusPo) {
		if (customerStatusPo == null) {
			return false;
		}
		return customerStatusPo.getStatusEnum() == StatusEnum.LEAVE;
	}

	public static String getPioneer(MonthStandard monthStandard, Map<Long, CustomerPo> id2CustomerPo) {
		if (monthStandard == null) {
			return null;
		}
		Long pioneerId = monthStandard.getPioneerId();
		if (pioneerId == null) {
			return null;
		}
		return id2CustomerPo.get(pioneerId).getUsername();
	}

}
