package com.conx.logistics.kernel.ui.editors.domain.model.table;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.conx.logistics.kernel.ui.editors.domain.model.EntityEditorComponentRepresentation;
import com.conx.logistics.mdm.domain.metadata.DefaultEntityMetadata;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="sysedtablerepresentation")
public class TableRepresentation extends EntityEditorComponentRepresentation {
	@ManyToOne
	private DefaultEntityMetadata entityMetadata;
	
    @OneToMany(mappedBy="parentTable",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<TableColumnRepresentation> columns = new java.util.HashSet<TableColumnRepresentation>();
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<TableColumnRepresentation> visibleColumns = new java.util.HashSet<TableColumnRepresentation>();    
}
