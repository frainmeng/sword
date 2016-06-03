/**
 * 
 */
package com.sword.util.poi;

import java.io.Serializable;

/**
 * @author mengfanyuan
 *
 */
public class TestAccount implements Serializable {
	private static final long serialVersionUID = -8148484431924669599L;
	private int accountId;
	private int ecuserId;
	private double account;
	private double freezeAccount;
	private double backCount;
	private double freezeBackCount;
	private String lastModifyDate;
	private String userName;
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public int getEcuserId() {
		return ecuserId;
	}
	public void setEcuserId(int ecuserId) {
		this.ecuserId = ecuserId;
	}
	public double getAccount() {
		return account;
	}
	public void setAccount(double account) {
		this.account = account;
	}
	public double getFreezeAccount() {
		return freezeAccount;
	}
	public void setFreezeAccount(double freezeAccount) {
		this.freezeAccount = freezeAccount;
	}
	public double getBackCount() {
		return backCount;
	}
	public void setBackCount(double backCount) {
		this.backCount = backCount;
	}
	public double getFreezeBackCount() {
		return freezeBackCount;
	}
	public void setFreezeBackCount(double freezeBackCount) {
		this.freezeBackCount = freezeBackCount;
	}
	public String getLastModifyDate() {
		return lastModifyDate;
	}
	public void setLastModifyDate(String lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
