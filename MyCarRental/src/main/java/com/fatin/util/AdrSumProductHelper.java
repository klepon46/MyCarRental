/**
 * 
 */
package com.fatin.util;

import java.math.BigDecimal;

/**
 * @author garya
 * May 25, 2016
 */
public class AdrSumProductHelper {

	private BigDecimal tonnage;
	private BigDecimal cv;
	private BigDecimal tm;
	private BigDecimal ts;
	private BigDecimal ash;

	public AdrSumProductHelper() {
		// TODO Auto-generated constructor stub
	}

	public AdrSumProductHelper(BigDecimal tonnage, BigDecimal cv,  BigDecimal tm, BigDecimal ts,BigDecimal ash) {
		this.tonnage = tonnage;
		this.cv = cv;
		this.tm = tm;
		this.ts = ts;
		this.ash = ash;
	}

	public BigDecimal getTonnage() {
		if(tonnage == null)
			tonnage = BigDecimal.ZERO;
		return tonnage;
	}

	public void setTonnage(BigDecimal tonnage) {
		this.tonnage = tonnage;
	}

	public BigDecimal getCv() {
		if(cv == null)
			cv = BigDecimal.ZERO;
		return cv;
	}

	public void setCv(BigDecimal cv) {
		this.cv = cv;
	}

	public BigDecimal getTm() {
		if(tm == null)
			tm = BigDecimal.ZERO;
		return tm;
	}

	public void setTm(BigDecimal tm) {
		this.tm = tm;
	}

	public BigDecimal getTs() {
		if(ts == null)
			ts = BigDecimal.ZERO;
		return ts;
	}

	public void setTs(BigDecimal ts) {
		this.ts = ts;
	}

	public BigDecimal getAsh() {
		if(ash == null)
			ash = BigDecimal.ZERO;
		return ash;
	}

	public void setAsh(BigDecimal ash) {
		this.ash = ash;
	}

}
