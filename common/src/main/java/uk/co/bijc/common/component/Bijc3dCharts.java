package uk.co.bijc.common.component;

import javax.swing.ImageIcon;

import com.inductiveautomation.ignition.common.jsonschema.JsonSchema;
import com.inductiveautomation.perspective.common.api.ComponentDescriptor;
import com.inductiveautomation.perspective.common.api.ComponentDescriptorImpl;

import uk.co.bijc.common.Bijc3dChartsComponents;


/**
 * Describes the component to the Java registry so the gateway and designer know to look for the front end elements.
 * In a 'common' scope so that it's referencable by both gateway and designer.
 */
public class Bijc3dCharts  {

    // unique ID of the component which perfectly matches that provided in the javascript's ComponentMeta implementation
    public static String COMPONENT_ID = "bijc.display.3dcharts";

    public static JsonSchema getSchema(String resourcePath) {
        return JsonSchema.parse(Bijc3dChartsComponents.class.getResourceAsStream("/" + resourcePath));
    }

    /**
     * Components register with the Java side ComponentRegistry but providing a ComponentDescriptor.  Here we
     * build the descriptor for this one component. Icons on the component palette are optional.
     */
    public static ComponentDescriptor DESCRIPTOR = ComponentDescriptorImpl.ComponentBuilder.newBuilder()
        .setPaletteCategory(Bijc3dChartsComponents.COMPONENT_CATEGORY)
        .setId(COMPONENT_ID)
        .setSchema(getSchema("bijc3dcharts.props.json"))
        .setName("3D Charts")
        .setDefaultMetaName("3dCharts")
        .setResources(Bijc3dChartsComponents.BROWSER_RESOURCES)
        .addPaletteEntry("", "3D Charts", "A simple component for displaying the size of the page", null, null)
        .setIcon(new ImageIcon(Bijc3dChartsComponents.class.getResource("/size-icon.png")))
        
        .build();
}