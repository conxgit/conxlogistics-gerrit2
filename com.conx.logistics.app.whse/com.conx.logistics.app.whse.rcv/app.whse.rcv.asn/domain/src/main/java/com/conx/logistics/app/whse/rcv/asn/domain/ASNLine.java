package com.conx.logistics.app.whse.rcv.asn.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.conx.logistics.app.whse.rcv.asn.shared.type.ASNLINESTATUS;
import com.conx.logistics.mdm.domain.MultitenantBaseEntity;
import com.conx.logistics.mdm.domain.product.Product;
import com.conx.logistics.mdm.domain.referencenumber.ReferenceNumber;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="whasnline")
public class ASNLine extends MultitenantBaseEntity {
    @ManyToOne(targetEntity = Product.class)
    @JoinColumn
    private Product product;
    
    @ManyToOne(targetEntity = ASN.class)
    @JoinColumn
    private ASN parentASN;    
    
    @OneToOne(targetEntity = ReferenceNumber.class)
    @JoinColumn
    private ReferenceNumber refNumber;

    private Integer lineNumber;

    private Integer expectedInnerPackCount;

    private Integer expectedOuterPackCount;

    private Double expectedTotalWeight;

    private Double expectedTotalLen;

    private Double expectedTotalWidth;

    private Double expectedTotalHeight;

    private Double expectedTotalVolume;

    @Enumerated(EnumType.STRING)
    private ASNLINESTATUS status;
    
	public ASN getParentASN() {
		return parentASN;
	}

	public void setParentASN(ASN parentASN) {
		this.parentASN = parentASN;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}

	public Integer getExpectedInnerPackCount() {
		return expectedInnerPackCount;
	}

	public void setExpectedInnerPackCount(Integer expectedInnerPackCount) {
		this.expectedInnerPackCount = expectedInnerPackCount;
	}

	public Integer getExpectedOuterPackCount() {
		return expectedOuterPackCount;
	}

	public void setExpectedOuterPackCount(Integer expectedOuterPackCount) {
		this.expectedOuterPackCount = expectedOuterPackCount;
	}

	public Double getExpectedTotalWeight() {
		return expectedTotalWeight;
	}

	public void setExpectedTotalWeight(Double expectedTotalWeight) {
		this.expectedTotalWeight = expectedTotalWeight;
	}

	public Double getExpectedTotalLen() {
		return expectedTotalLen;
	}

	public void setExpectedTotalLen(Double expectedTotalLen) {
		this.expectedTotalLen = expectedTotalLen;
	}

	public Double getExpectedTotalWidth() {
		return expectedTotalWidth;
	}

	public void setExpectedTotalWidth(Double expectedTotalWidth) {
		this.expectedTotalWidth = expectedTotalWidth;
	}

	public Double getExpectedTotalHeight() {
		return expectedTotalHeight;
	}

	public void setExpectedTotalHeight(Double expectedTotalHeight) {
		this.expectedTotalHeight = expectedTotalHeight;
	}

	public Double getExpectedTotalVolume() {
		return expectedTotalVolume;
	}

	public void setExpectedTotalVolume(Double expectedTotalVolume) {
		this.expectedTotalVolume = expectedTotalVolume;
	}

	public ASNLINESTATUS getStatus() {
		return status;
	}

	public void setStatus(ASNLINESTATUS status) {
		this.status = status;
	}

	public ReferenceNumber getRefNumber() {
		return refNumber;
	}

	public void setRefNumber(ReferenceNumber refNumber) {
		this.refNumber = refNumber;
	}
}
