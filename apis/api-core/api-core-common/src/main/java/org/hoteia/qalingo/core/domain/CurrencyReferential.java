/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.apache.commons.lang.StringUtils;

@Entity
@Table(name="TECO_CURRENCY_REFERENTIAL", uniqueConstraints = {@UniqueConstraint(columnNames= {"code"})})
public class CurrencyReferential extends AbstractEntity {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -8773291277705820667L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@Version
	@Column(name="VERSION", nullable=false, columnDefinition="int(11) default 1")
	private int version;
	
	@Column(name="NAME")
	private String name;

	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="CODE")
	private String code;
	
	@Column(name="SIGN")
	private String sign;
	
	@Column(name="ABBREVIATED")
	private String abbreviated;

    @Column(name = "FORMAT_LOCALE")
    private String formatLocale;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATE")
	private Date dateCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATE")
	private Date dateUpdate;
	
	public CurrencyReferential() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getAbbreviated() {
		return abbreviated;
	}

	public void setAbbreviated(String abbreviated) {
		this.abbreviated = abbreviated;
	}
	
	public String getFormatLocale() {
        return formatLocale;
    }
	
    public Locale getLocale() {
        if(StringUtils.isNotEmpty(formatLocale)){
            String[] split = formatLocale.split("_");
            if(split.length == 1){
                return new Locale(split[0]);
            } else if(split.length == 2){
                return new Locale(split[0], split[1]);
            }
        }
        return Locale.ENGLISH;
    }
    
	public void setFormatLocale(String formatLocale) {
        this.formatLocale = formatLocale;
    }
	
	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public Date getDateUpdate() {
		return dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}
	
    public NumberFormat getStandardCurrencyformat(){
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        if(StringUtils.isNotEmpty(getFormatLocale())){
            formatter = NumberFormat.getCurrencyInstance(getLocale());
        }
        Currency currency = Currency.getInstance(getCode());
        formatter.setCurrency(currency);
        return formatter;
    }
    
    public NumberFormat getEcoCurrencyformat(){
        NumberFormat formatter = getStandardCurrencyformat();
        formatter.setGroupingUsed(true);
        formatter.setParseIntegerOnly(false);
        formatter.setRoundingMode(RoundingMode.HALF_EVEN);

        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(2);

        formatter.setMaximumIntegerDigits(1000000);
        formatter.setMinimumIntegerDigits(1);
        return formatter;
    }

    public String formatPriceWithStandardCurrencySign(BigDecimal price){
        NumberFormat formatter = getEcoCurrencyformat();
        return formatter.format(price);
    }
	
}