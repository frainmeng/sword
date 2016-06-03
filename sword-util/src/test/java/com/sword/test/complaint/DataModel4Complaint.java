/**
 * 
 */
package com.sword.test.complaint;

import com.sword.util.poi.PoiCell;

/**
 * @author mengfanyuan
 *
 */
public class DataModel4Complaint {
	
	/**
	 * 商家Id 
	 */
	@PoiCell(cellIndex=0)
	private String venderId;
	
	/**
	 * 手机号
	 */
	@PoiCell(cellIndex=2)
	private String mobile;

	public String getVenderId() {
		return venderId;
	}

	public void setVenderId(String venderId) {
		this.venderId = venderId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
}
