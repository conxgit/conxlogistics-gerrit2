package com.conx.logistics.kernel.ui.forms.shared.form;

import java.util.List;
import java.util.Map;

import com.conx.logistics.kernel.ui.forms.domain.model.FormItemRepresentation;
import com.conx.logistics.kernel.ui.forms.domain.model.FormRepresentation;
import com.conx.logistics.kernel.ui.forms.shared.menu.MenuItemDescription;

public interface FormRepresentationDecoder {

    FormRepresentation decode(String json) throws FormEncodingException;

    FormItemRepresentation decodeItem(String json) throws FormEncodingException;

    Object decode(Map<String, Object> data) throws FormEncodingException;

    Map<String, List<MenuItemDescription>> decodeMenuItemsMap(String json)
            throws FormEncodingException;

}
