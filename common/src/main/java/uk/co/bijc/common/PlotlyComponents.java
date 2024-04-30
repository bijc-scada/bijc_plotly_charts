package uk.co.bijc.common;

import java.util.Set;

import com.inductiveautomation.perspective.common.api.BrowserResource;

public class PlotlyComponents {
    public static final boolean isDebug = false;

    public static final String MODULE_ID = "uk.co.bijc.plotly";
    public static final String URL_ALIAS = "bijcplotly";
    public static final String COMPONENT_CATEGORY = "BIJC";
    public static final Set<BrowserResource> BROWSER_RESOURCES =
        Set.of(
            new BrowserResource(
                "bijc-plotly-components-js",
                String.format("/res/%s/PlotlyClientComponents.js", URL_ALIAS),
                BrowserResource.ResourceType.JS
            ),
            new BrowserResource(
                "bijc-plotly-components-css",
                String.format("/res/%s/PlotlyClientComponents.css", URL_ALIAS),
                BrowserResource.ResourceType.CSS
            )
        );
}