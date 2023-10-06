package com.burrow.auxiliary;

public class WidgetAuxiliaryFunctions {
    public boolean wasResizedMax(LayoutData data, BoxFrame frame) {
         return !(data.maxWidth == frame.width &&
            data.maxHeight == frame.height);
    }

    public boolean wasResizedMin(LayoutData data, BoxFrame frame) {
         return !(data.minWidth == frame.width &&
            data.minHeight == frame.height);
    }
}
