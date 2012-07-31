package com.conx.logistics.kernel.ui.editors.domain.model.masterdetail;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.conx.logistics.kernel.ui.editors.domain.model.EntityEditorComponentRepresentation;
import com.conx.logistics.kernel.ui.editors.domain.model.table.TableRepresentation;
import com.conx.logistics.mdm.domain.metadata.DefaultEntityMetadata;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="sysedmasterdetailentityeditorrepresentation")
public class MasterDetailEntityEditorRepresentation extends EntityEditorComponentRepresentation {
	@ManyToOne
	private DefaultEntityMetadata entityMetadata;
	
	@OneToOne
	private TableRepresentation mainGrid;
}
