package uk.co.bijc.designer;

import com.inductiveautomation.ignition.common.BundleUtil;
import com.inductiveautomation.ignition.common.licensing.LicenseState;
import com.inductiveautomation.ignition.common.util.LoggerEx;
import com.inductiveautomation.ignition.designer.model.AbstractDesignerModuleHook;
import com.inductiveautomation.ignition.designer.model.DesignerContext;
import com.inductiveautomation.perspective.designer.DesignerComponentRegistry;
import com.inductiveautomation.perspective.designer.api.PerspectiveDesignerInterface;
import uk.co.bijc.common.component.BijcPlotly;
import uk.co.bijc.common.BijcPlotlyComponents;


/**
 * The 'hook' class for the designer scope of the module.  Registered in the ignitionModule configuration of the
 * root build.gradle file.
 */
public class BijcPlotlyDesignerHook extends AbstractDesignerModuleHook {
    private static final LoggerEx log = LoggerEx.newBuilder().build("BijcPlotly");
    private static final boolean isDebug = BijcPlotlyComponents.isDebug;

    private DesignerContext context;
    private DesignerComponentRegistry registry;

    static {
        BundleUtil.get().addBundle("bijcplotly", BijcPlotlyDesignerHook.class.getClassLoader(), "bijcplotly");
    }

    public BijcPlotlyDesignerHook() {
        if (isDebug) log.info("Registering Bijc Components in Designer!");
    }

    @Override
    public void startup(DesignerContext context, LicenseState activationState) {
        this.context = context;
        init();
    }

    private void init() {
        if (isDebug) log.debug("Initializing registry entrants...");

        PerspectiveDesignerInterface pdi = PerspectiveDesignerInterface.get(context);

        registry = pdi.getDesignerComponentRegistry();

        // register components to get them on the palette
        if (isDebug) log.info(BijcPlotly.COMPONENT_ID);
        registry.registerComponent(BijcPlotly.DESCRIPTOR);
    }


    @Override
    public void shutdown() {
        removeComponents();
    }

    private void removeComponents() {
        registry.removeComponent(BijcPlotly.COMPONENT_ID);
    }
}
