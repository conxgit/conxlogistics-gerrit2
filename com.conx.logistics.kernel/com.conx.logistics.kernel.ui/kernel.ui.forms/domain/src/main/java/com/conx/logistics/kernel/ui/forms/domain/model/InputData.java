/*

 *



 *

 *





 */
package com.conx.logistics.kernel.ui.forms.domain.model;




public class InputData extends Data {

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && obj instanceof InputData;
    }

    @Override
    public int hashCode() {
        return super.hashCode() * 37 + 28851;
    }
}
