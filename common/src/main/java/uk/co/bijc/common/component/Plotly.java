package uk.co.bijc.common.component;

import com.inductiveautomation.ignition.common.gson.JsonObject;
import com.inductiveautomation.ignition.common.gson.JsonParser;
import com.inductiveautomation.ignition.common.jsonschema.JsonSchema;
import com.inductiveautomation.perspective.common.api.ComponentDescriptor;
import com.inductiveautomation.perspective.common.api.ComponentDescriptorImpl;
import com.inductiveautomation.perspective.common.api.ComponentEventDescriptor;

import uk.co.bijc.common.PlotlyComponents;

import java.io.InputStreamReader;
import java.util.Arrays;

import javax.swing.*;

/**
 * Describes the component to the Java registry so the gateway and designer know
 * to look for the front end elements.
 * In a 'common' scope so that it's referencable by both gateway and designer.
 */
public class Plotly {

    // unique ID of the component which perfectly matches that provided in the
    // javascript's ComponentMeta implementation
    public static String COMPONENT_ID = "bijc.display.plotly";

    public static JsonSchema SCHEMA = JsonSchema
            .parse(PlotlyComponents.class.getResourceAsStream("/plotly.props.json"));

    static JsonObject getVariant(String chartName) {
        return (new JsonParser())
                .parse(new InputStreamReader(PlotlyComponents.class
                        .getResourceAsStream(String.format("/variants/plotly.%s.props.json", chartName))))
                .getAsJsonObject();
    }

    public static final ComponentEventDescriptor ONCLICK_HANDLER = new PlotlyEventDescriptor("onClick");
    public static final ComponentEventDescriptor ONDOUBLECLICK_HANDLER = new PlotlyEventDescriptor("onDoubleClick");
    public static final ComponentEventDescriptor ONHOVER_HANDLER = new PlotlyEventDescriptor("onHover");
    public static final ComponentEventDescriptor ONUNHOVER_HANDLER = new PlotlyEventDescriptor("onUnHover");
    public static final ComponentEventDescriptor ONLEGENDCLICK_HANDLER = new PlotlyEventDescriptor("onLegendClick");
    public static final ComponentEventDescriptor ONLEGENDDOUBLECLICK_HANDLER = new PlotlyEventDescriptor(
            "onLegendDoubleClick");
    public static final ComponentEventDescriptor ONSELECTING_HANDLER = new PlotlyEventDescriptor("onSelecting");
    public static final ComponentEventDescriptor ONSELECTED_HANDLER = new PlotlyEventDescriptor("onSelected");

    public static JsonSchema getSchema(String resourcePath) {
        return JsonSchema.parse(PlotlyComponents.class.getResourceAsStream(resourcePath));
    }

    public static class PlotlyEventDescriptor extends ComponentEventDescriptor {
        public PlotlyEventDescriptor(String name) {
            super(name, Plotly.getSchema(String.format("/events/plotly.%s.json", name)));
        }
    }

    /**
     * Components register with the Java side ComponentRegistry but providing a
     * ComponentDescriptor. Here we
     * build the descriptor for this one component. Icons on the component palette
     * are optional.
     */
    public static ComponentDescriptor DESCRIPTOR = ComponentDescriptorImpl.ComponentBuilder.newBuilder()
            .setPaletteCategory(PlotlyComponents.COMPONENT_CATEGORY)
            .setId(COMPONENT_ID)
            .setModuleId(PlotlyComponents.MODULE_ID)
            .setSchema(SCHEMA)
            .setEvents(Arrays.asList(ONCLICK_HANDLER, ONHOVER_HANDLER, ONUNHOVER_HANDLER, ONLEGENDCLICK_HANDLER,
                    ONLEGENDDOUBLECLICK_HANDLER, ONSELECTING_HANDLER, ONSELECTED_HANDLER))
            .setName("Plotly")
            .setIcon(new ImageIcon(PlotlyComponents.class.getResource("/plotly.png")))
            .addPaletteEntry("", "Plotly", "A Plotly.js chart component", null, null)
            .addPaletteEntry("plotly-bar", "Plotly Bar", "A Plotly.js bar component", null, getVariant("bar"))
            .addPaletteEntry("plotly-line", "Plotly Line", "A Plotly.js line component", null, getVariant("line"))
            .addPaletteEntry("plotly-area", "Plotly Area", "A Plotly.js area component", null, getVariant("area"))
            .addPaletteEntry("plotly-boxplot", "Plotly Boxplot", "A Plotly.js boxplot component", null,
                    getVariant("boxplot"))
            .addPaletteEntry("plotly-bubble", "Plotly Bubble", "A Plotly.js bubble component", null,
                    getVariant("bubble"))
            .addPaletteEntry("plotly-sunburst", "Plotly Sunburst", "A Plotly.js sunburst component", null,
                    getVariant("sunburst"))
            .addPaletteEntry("plotly-treemap", "Plotly Treemap", "A Plotly.js treemap component", null,
                    getVariant("treemap"))
            .addPaletteEntry("plotly-contour", "Plotly Contour", "A Plotly.js contour component", null,
                    getVariant("contour"))
            .addPaletteEntry("plotly-heatmap", "Plotly Heatmap", "A Plotly.js heatmap component", null,
                    getVariant("heatmap"))
            .addPaletteEntry("plotly-radar", "Plotly Radar", "A Plotly.js radar component", null, getVariant("radar"))
            .addPaletteEntry("plotly-waterfall", "Plotly Waterfall", "A Plotly.js waterfall component", null,
                    getVariant("waterfall"))
            .addPaletteEntry("plotly-gauge", "Plotly Gauge", "A Plotly.js gauge component", null, getVariant("gauge"))
            .addPaletteEntry("plotly-3dsurface", "Plotly 3D Surface", "A Plotly.js 3D surface component", null,
                    getVariant("3dsurface"))
            .addPaletteEntry("plotly-scatter3d", "Plotly Scatter 3D", "A Plotly.js scatter 3D component", null,
                    getVariant("scatter3d"))
            .addPaletteEntry("plotly-streamtube", "Plotly Streamtube", "A Plotly.js streamtube component", null,
                    getVariant("streamtube"))
            .addPaletteEntry("plotly-isosurface", "Plotly Isosurface", "A Plotly.js isosurface component", null,
                    getVariant("isosurface"))
            .addPaletteEntry("plotly-ribbon", "Plotly Ribbon", "A Plotly.js ribbon component", null,
                    getVariant("ribbon"))
            // getVariantProps("/variants/bijcplotly.bar.props.json"))
            .setDefaultMetaName("plotly")
            .setResources(PlotlyComponents.BROWSER_RESOURCES)
            .build();
}