package uk.co.bijc.common.component;

import java.io.InputStreamReader;

import javax.swing.ImageIcon;

import com.inductiveautomation.ignition.common.gson.JsonObject;
import com.inductiveautomation.ignition.common.gson.JsonParser;
import com.inductiveautomation.ignition.common.jsonschema.JsonSchema;
import com.inductiveautomation.perspective.common.api.ComponentDescriptor;
import com.inductiveautomation.perspective.common.api.ComponentDescriptorImpl;

import uk.co.bijc.common.BijcPlotlyComponents;

/**
 * Describes the component to the Java registry so the gateway and designer know
 * to look for the front end elements.
 * In a 'common' scope so that it's referencable by both gateway and designer.
 */
public class BijcPlotly {

    // unique ID of the component which perfectly matches that provided in the
    // javascript's ComponentMeta implementation
    public static String COMPONENT_ID = "bijc.display.plotly";

    public static JsonSchema getSchema(String resourcePath) {
        return JsonSchema.parse(BijcPlotlyComponents.class.getResourceAsStream("/" + resourcePath));
    }

    static JsonObject getVariantProps(String fileName) {
        return (new JsonParser()).parse(new InputStreamReader(BijcPlotlyComponents.class.getResourceAsStream(fileName)))
                .getAsJsonObject();
    }

    /**
     * Components register with the Java side ComponentRegistry but providing a
     * ComponentDescriptor. Here we
     * build the descriptor for this one component. Icons on the component palette
     * are optional.
     */
    public static ComponentDescriptor DESCRIPTOR = ComponentDescriptorImpl.ComponentBuilder.newBuilder()
            .setPaletteCategory(BijcPlotlyComponents.COMPONENT_CATEGORY)
            .setId(COMPONENT_ID)
            .setSchema(getSchema("bijcplotly.props.json"))
            .setName("Plotly")
            .setDefaultMetaName("plotly")
            .setResources(BijcPlotlyComponents.BROWSER_RESOURCES)
            .addPaletteEntry("", "Plotly", "A Plotly.js chart component", null, null)
            .addPaletteEntry("bijcplotly-bar", "Plotly Bar", "A Plotly.js bar component", null,
                    getVariantProps("/variants/bijc.bar.props.json"))
            .addPaletteEntry("bijcplotly-line", "Plotly Bar", "A Plotly.js line component", null,
                    getVariantProps("/variants/bijc.line.props.json"))
            .setIcon(new ImageIcon(BijcPlotlyComponents.class.getResource("/size-icon.png")))

            .build();
}