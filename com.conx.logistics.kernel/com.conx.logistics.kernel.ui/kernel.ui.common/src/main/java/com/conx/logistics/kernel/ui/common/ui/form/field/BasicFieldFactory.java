package com.conx.logistics.kernel.ui.common.ui.form.field;

import java.util.Collection;

import com.conx.logistics.common.utils.Validator;
import com.conx.logistics.kernel.ui.common.data.container.EntityTypeContainerFactory;
import com.conx.logistics.mdm.domain.BaseEntity;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextField;

public class BasicFieldFactory extends DefaultFieldFactory  {
	
	private static EntityTypeContainerFactory etcFactory;
	private Collection<?> visibleProperties;
	private Collection<?> editableProps;

	/**
	 * @param roProps 
	 * 
	 */
	public BasicFieldFactory(EntityTypeContainerFactory etcFactory,Collection<?> visibleProperties, Collection<?> roProps)
	{
		this.etcFactory = etcFactory;
		this.visibleProperties = visibleProperties;
		this.editableProps = roProps;
	}
	
    /**
     * Creates fields based on the property type.
     * <p>
     * The default field type is {@link TextField}. Other field types generated
     * by this method:
     * <p>
     * <b>Boolean</b>: {@link CheckBox}.<br/>
     * <b>Date</b>: {@link DateField}(resolution: day).<br/>
     * <b>Item</b>: {@link Form}. <br/>
     * <b>default field type</b>: {@link TextField}.
     * <p>
     * 
     * @param type
     *            the type of the property
     * @return the most suitable generic {@link Field} for given type
     */
    public static Field createFieldByPropertyType(Class<?> type) {
    	//-- Entity
        if (BaseEntity.class.isAssignableFrom(type)) {
        	//-- Let's try getting a container for this type
            Container fieldContainer = etcFactory.createEntityTypeContainer(type);
            
            if (Validator.isNotNull(fieldContainer))
            {
            	Select select = new Select("", fieldContainer);
            	select.setItemCaptionPropertyId("name");
            	return select;
            }
            else
            	return DefaultFieldFactory.createFieldByPropertyType(type); 
        }
        else
        {
        	Field field = DefaultFieldFactory.createFieldByPropertyType(type);
        	if (TextField.class.isAssignableFrom(field.getClass()))
        	{
        			((TextField)field).setNullRepresentation("");
        	}
        	return field;
        }
    }

    
    public Field createField(Item item, Object propertyId, Component uiContext) {
    	if (visibleProperties.contains(propertyId))
    	{
	        Class<?> type = item.getItemProperty(propertyId).getType();
	        Field field = BasicFieldFactory.createFieldByPropertyType(type);       
	        field.setCaption(super.createCaptionByPropertyId(propertyId));
	        
	        if (editableProps.contains(propertyId))
	        	field.setReadOnly(false);
	        else
	        	field.setReadOnly(true);
	        
	        return field;
    	}
    	else
    		return null;
    }    
}
