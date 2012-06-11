package com.conx.logistics.mdm.domain;

import javax.persistence.MappedSuperclass;


@MappedSuperclass
public class MultitenantBaseEntity  extends  BaseEntity{
    private long tenantId;
}