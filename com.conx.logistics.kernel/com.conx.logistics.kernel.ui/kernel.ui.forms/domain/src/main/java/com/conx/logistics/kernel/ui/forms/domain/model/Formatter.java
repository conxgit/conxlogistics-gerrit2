/*

 *



 *

 *





 */
package com.conx.logistics.kernel.ui.forms.domain.model;

import java.util.Map;

public interface Formatter {

    Object format(Object object);

    Map<String, Object> getDataMap();
}
