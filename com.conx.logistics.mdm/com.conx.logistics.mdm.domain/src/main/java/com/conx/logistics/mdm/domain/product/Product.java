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
@Table(name="refproduct")
public class Product extends MultitenantBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    
    @Version
    @Column(name = "version")
    private Integer version;
    

    @OneToOne(targetEntity = ProductUnitConversion.class, fetch = FetchType.EAGER)
    @JoinColumn
    private ProductUnitConversion palletUnitConvesion;

    @OneToOne(targetEntity = PackUnit.class, fetch = FetchType.EAGER)
    @JoinColumn
    private PackUnit innerPackUnit;

    @OneToOne(targetEntity = PackUnit.class, fetch = FetchType.EAGER)
    @JoinColumn
    private PackUnit outerPackUnit;

    @OneToOne(targetEntity = WeightUnit.class, fetch = FetchType.EAGER)
    @JoinColumn
    private WeightUnit weightUnit;

    @OneToOne(targetEntity = DimUnit.class, fetch = FetchType.EAGER)
    @JoinColumn
    private DimUnit dimUnit;

    @OneToOne(targetEntity = DimUnit.class, fetch = FetchType.EAGER)
    @JoinColumn
    private DimUnit volUnit;

    @OneToOne(targetEntity = ProductMaster.class)
    @JoinColumn
    private ProductMaster productMaster;

    @ManyToOne(targetEntity = Commodity.class, fetch = FetchType.EAGER)
    @JoinColumn
    private Commodity commodity;

    @ManyToOne(targetEntity = Organization.class, fetch = FetchType.EAGER)
    @JoinColumn
    private Organization supplier;


    @ManyToOne(targetEntity = ProductType.class, fetch = FetchType.EAGER)
    @JoinColumn
    private ProductType productType;

    @OneToOne(targetEntity = CommercialRecord.class, fetch = FetchType.LAZY)
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
}
