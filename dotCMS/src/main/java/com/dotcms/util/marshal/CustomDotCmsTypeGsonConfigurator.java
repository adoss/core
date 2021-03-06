package com.dotcms.util.marshal;

import com.dotcms.api.system.event.Payload;
import com.dotcms.repackage.com.google.gson.*;
import com.dotcms.util.deserializer.ContentletDeserializer;
import com.dotcms.util.deserializer.PayloadAdapter;
import com.dotmarketing.beans.Host;
import com.dotmarketing.portlets.contentlet.model.Contentlet;
import com.dotmarketing.portlets.htmlpageasset.model.HTMLPageAsset;

/**
 * Encapsulates some of the custom type serializer and deserializer for dotcms own types.
 * @author jsanca
 */
public class CustomDotCmsTypeGsonConfigurator implements GsonConfigurator {

    @Override
    public void configure(final GsonBuilder gsonBuilder) {

        gsonBuilder.registerTypeAdapter( Payload.class, new PayloadAdapter() );
        gsonBuilder.registerTypeAdapter(Contentlet.class, new ContentletDeserializer() );
        gsonBuilder.registerTypeAdapter(HTMLPageAsset.class, new ContentletDeserializer() );
        gsonBuilder.registerTypeAdapter(Host.class, new ContentletDeserializer() );
    }

    @Override
    public boolean excludeDefaultConfiguration() {

        return false;
    }
} // E:O:F:CustomDotCmsTypeGsonConfigurator.
