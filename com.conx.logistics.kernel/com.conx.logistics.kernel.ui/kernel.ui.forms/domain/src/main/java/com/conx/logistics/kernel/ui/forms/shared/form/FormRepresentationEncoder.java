package com.conx.logistics.kernel.ui.forms.shared.form;

import java.util.List;
import java.util.Map;

import com.conx.logistics.kernel.ui.forms.domain.model.FormItemRepresentation;
import com.conx.logistics.kernel.ui.forms.domain.model.FormRepresentation;
import com.conx.logistics.kernel.ui.forms.shared.menu.MenuItemDescription;

public interface FormRepresentationEncoder {

    String encode(FormRepresentation form) throws FormEncodingException;
    
    String encode(FormItemRepresentation item) throws FormEncodingException;

    String encodeMenuItemsMap(Map<String, List<MenuItemDescription>> items) throws FormEncodingException;
}
