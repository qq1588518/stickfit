package io.github.xinyangpan.vo;

import io.github.xinyangpan.persistent.po.CustomerPo;
import io.github.xinyangpan.persistent.po.type.YearMonth;

public class CustomerStatusVo {
	private YearMonth yearMonth;
	private CustomerPo customerPo;
	private boolean leave;

	@Override
	public String toString() {
		return String.format("CustomerStatusVo [yearMonth=%s, customerPo=%s, leave=%s]", yearMonth, customerPo, leave);
	}

	public YearMonth getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(YearMonth yearMonth) {
		this.yearMonth = yearMonth;
	}

	public CustomerPo getCustomerPo() {
		return customerPo;
	}

	public void setCustomerPo(CustomerPo customerPo) {
		this.customerPo = customerPo;
	}

	public boolean isLeave() {
		return leave;
	}

	public void setLeave(boolean leave) {
		this.leave = leave;
	}

}
