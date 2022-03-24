package uk.co.bijc.common.component;

import com.inductiveautomation.ignition.common.gson.JsonObject;
import com.inductiveautomation.ignition.common.gson.JsonParser;
import com.inductiveautomation.ignition.common.jsonschema.JsonSchema;
import com.inductiveautomation.perspective.common.api.ComponentDescriptor;
import com.inductiveautomation.perspective.common.api.ComponentDescriptorImpl;

import uk.co.bijc.common.PlotlyComponents;

import java.io.InputStreamReader;
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

    static JsonObject getVariant(String fileName) {
        return (new JsonParser()).parse(new InputStreamReader(PlotlyComponents.class.getResourceAsStream(fileName))).getAsJsonObject();
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
            // .setEvents(Arrays.asList())
            .setName("Plotly")
            .setIcon(new ImageIcon(PlotlyComponents.class.getResource("/size-icon.png")))
            .addPaletteEntry("", "Plotly", "A Plotly.js chart component", null, null)
            .addPaletteEntry("plotly-bar", "Plotly Bar", "A Plotly.js bar component", null, getVariant("/variants/plotly.bar.props.json"))
            .addPaletteEntry("plotly-line", "Plotly Line", "A Plotly.js line component", null, getVariant("/variants/plotly.line.props.json"))
            // getVariantProps("/variants/bijcplotly.bar.props.json"))
            .setDefaultMetaName("plotly")
            .setResources(PlotlyComponents.BROWSER_RESOURCES)
            .build();
}