package uk.co.bijc.common;

import java.util.Set;

import com.inductiveautomation.perspective.common.api.BrowserResource;

public class Bijc3dChartsComponents {
    public static final boolean isDebug = false;

    public static final String MODULE_ID = "uk.co.bijc.3dcharts";
    public static final String URL_ALIAS = "bijc3dcharts";
    public static final String COMPONENT_CATEGORY = "BIJC";
    public static final Set<BrowserResource> BROWSER_RESOURCES =
        Set.of(
            new BrowserResource(
                "bijc-3dcharts-components-js",
                String.format("/res/%s/Bijc3dCharts.js", URL_ALIAS),
                BrowserResource.ResourceType.JS
            ),
            new BrowserResource(
                "bijc-3dcharts-components-css",
                String.format("/res/%s/Bijc3dCharts.css", URL_ALIAS),
                BrowserResource.ResourceType.CSS
            )
        );
}