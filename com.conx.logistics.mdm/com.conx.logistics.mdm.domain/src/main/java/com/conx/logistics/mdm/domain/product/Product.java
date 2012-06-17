package com.conx.logistics.mdm.domain.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.conx.logistics.mdm.domain.MultitenantBaseEntity;
import com.conx.logistics.mdm.domain.commercialrecord.CommercialRecord;
import com.conx.logistics.mdm.domain.organization.Organization;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="mdmproduct")
public class Product extends MultitenantBaseEntity {

    @OneToOne(targetEntity = ProductUnitConversion.class)
    @JoinColumn
    private ProductUnitConversion palletUnitConvesion;

    @OneToOne(targetEntity = PackUnit.class)
    @JoinColumn
    private PackUnit innerPackUnit;

    @OneToOne(targetEntity = PackUnit.class)
    @JoinColumn
    private PackUnit outerPackUnit;

    @OneToOne(targetEntity = WeightUnit.class)
    @JoinColumn
    private WeightUnit weightUnit;

    @OneToOne(targetEntity = DimUnit.class)
    @JoinColumn
    private DimUnit dimUnit;

    @OneToOne(targetEntity = DimUnit.class)
    @JoinColumn
    private DimUnit volUnit;

    @OneToOne(targetEntity = ProductMaster.class)
    @JoinColumn
    private ProductMaster productMaster;

    @ManyToOne(targetEntity = Commodity.class)
    @JoinColumn
    private Commodity commodity;

    @ManyToOne(targetEntity = Organization.class)
    @JoinColumn
    private Organization supplier;


    @ManyToOne(targetEntity = ProductType.class)
    @JoinColumn
    private ProductType productType;

    @OneToOne(targetEntity = CommercialRecord.class)
    @JoinColumn
    private CommercialRecord commercialRecord;   

    private Integer innerPackCount;

    private Integer outerPackCount;

    private Double weight;

    private Double len;

    private Double width;

    private Double height;

    private Double volume;

    private Boolean hasAvailableInventory;

	public ProductUnitConversion getPalletUnitConvesion() {
		return palletUnitConvesion;
	}

	public void setPalletUnitConvesion(ProductUnitConversion palletUnitConvesion) {
		this.palletUnitConvesion = palletUnitConvesion;
	}

	public PackUnit getInnerPackUnit() {
		return innerPackUnit;
	}

	public void setInnerPackUnit(PackUnit innerPackUnit) {
		this.innerPackUnit = innerPackUnit;
	}

	public PackUnit getOuterPackUnit() {
		return outerPackUnit;
	}

	public void setOuterPackUnit(PackUnit outerPackUnit) {
		this.outerPackUnit = outerPackUnit;
	}

	public WeightUnit getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(WeightUnit weightUnit) {
		this.weightUnit = weightUnit;
	}

	public DimUnit getDimUnit() {
		return dimUnit;
	}

	public void setDimUnit(DimUnit dimUnit) {
		this.dimUnit = dimUnit;
	}

	public DimUnit getVolUnit() {
		return volUnit;
	}

	public void setVolUnit(DimUnit volUnit) {
		this.volUnit = volUnit;
	}

	public ProductMaster getProductMaster() {
		return productMaster;
	}

	public void setProductMaster(ProductMaster productMaster) {
		this.productMaster = productMaster;
	}

	public Commodity getCommodity() {
		return commodity;
	}

	public void setCommodity(Commodity commodity) {
		this.commodity = commodity;
	}

	public Organization getSupplier() {
		return supplier;
	}

	public void setSupplier(Organization supplier) {
		this.supplier = supplier;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public CommercialRecord getCommercialRecord() {
		return commercialRecord;
	}

	public void setCommercialRecord(CommercialRecord commercialRecord) {
		this.commercialRecord = commercialRecord;
	}

	public Integer getInnerPackCount() {
		return innerPackCount;
	}

	public void setInnerPackCount(Integer innerPackCount) {
		this.innerPackCount = innerPackCount;
	}

	public Integer getOuterPackCount() {
		return outerPackCount;
	}

	public void setOuterPackCount(Integer outerPackCount) {
		this.outerPackCount = outerPackCount;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getLen() {
		return len;
	}

	public void setLen(Double len) {
		this.len = len;
	}

	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public Boolean getHasAvailableInventory() {
		return hasAvailableInventory;
	}

	public void setHasAvailableInventory(Boolean hasAvailableInventory) {
		this.hasAvailableInventory = hasAvailableInventory;
	}       
}
