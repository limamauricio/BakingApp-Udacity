package com.limamauricio.bakingapp.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class BakingAppWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new BakingWidgetFactoryAdapter(this,intent);
    }
}
