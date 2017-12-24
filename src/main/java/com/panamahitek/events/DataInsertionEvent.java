package com.panamahitek.events;

import com.panamahitek.PanamaHitek_DataBuffer;
import java.util.EventObject;

public class DataInsertionEvent extends EventObject {
    PanamaHitek_DataBuffer buffer;

    public DataInsertionEvent(Object source, PanamaHitek_DataBuffer buffer) {
        super(source);
        this.buffer = buffer;
    }
}