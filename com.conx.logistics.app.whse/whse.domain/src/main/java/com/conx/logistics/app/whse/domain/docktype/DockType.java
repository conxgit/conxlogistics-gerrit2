package com.conx.logistics.app.whse.domain.docktype;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import com.conx.logistics.mdm.domain.MultitenantBaseEntity;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="whdocktype")
public class DockType extends MultitenantBaseEntity {
}