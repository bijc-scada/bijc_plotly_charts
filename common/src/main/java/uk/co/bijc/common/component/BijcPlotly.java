package uk.co.bijc.common.component;

import javax.swing.ImageIcon;

import com.inductiveautomation.ignition.common.jsonschema.JsonSchema;
import com.inductiveautomation.perspective.common.api.ComponentDescriptor;
import com.inductiveautomation.perspective.common.api.ComponentDescriptorImpl;

import uk.co.bijc.common.BijcPlotlyComponents;


/**
 * Describes the component to the Java registry so the gateway and designer know to look for the front end elements.
 * In a 'common' scope so that it's referencable by both gateway and designer.
 */
public class BijcPlotly  {

    // unique ID of the component which perfectly matches that provided in the javascript's ComponentMeta implementation
    public static String COMPONENT_ID = "bijc.display.plotly";

    public static JsonSchema getSchema(String resourcePath) {
        return JsonSchema.parse(BijcPlotlyComponents.class.getResourceAsStream("/" + resourcePath));
    }

    /**
     * Components register with the Java side ComponentRegistry but providing a ComponentDescriptor.  Here we
     * build the descriptor for this one component. Icons on the component palette are optional.
     */
    public static ComponentDescriptor DESCRIPTOR = ComponentDescriptorImpl.ComponentBuilder.newBuilder()
        .setPaletteCategory(BijcPlotlyComponents.COMPONENT_CATEGORY)
        .setId(COMPONENT_ID)
        .setSchema(getSchema("bijcplotly.props.json"))
        .setName("Plotly")
        .setDefaultMetaName("plotly")
        .setResources(BijcPlotlyComponents.BROWSER_RESOURCES)
        .addPaletteEntry("", "Plotly", "A simple component for displaying the size of the page", null, null)
        .setIcon(new ImageIcon(BijcPlotlyComponents.class.getResource("/size-icon.png")))
        
        .build();
}