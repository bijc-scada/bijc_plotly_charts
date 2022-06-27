package uk.co.bijc.designer;

import com.inductiveautomation.ignition.common.BundleUtil;
import com.inductiveautomation.ignition.common.licensing.LicenseState;
import com.inductiveautomation.ignition.common.util.LoggerEx;
import com.inductiveautomation.ignition.designer.model.AbstractDesignerModuleHook;
import com.inductiveautomation.ignition.designer.model.DesignerContext;
import com.inductiveautomation.perspective.designer.DesignerComponentRegistry;
import com.inductiveautomation.perspective.designer.api.PerspectiveDesignerInterface;
import uk.co.bijc.common.component.Bijc3dCharts;
import uk.co.bijc.common.Bijc3dChartsComponents;


/**
 * The 'hook' class for the designer scope of the module.  Registered in the ignitionModule configuration of the
 * root build.gradle file.
 */
public class Bijc3dChartsDesignerHook extends AbstractDesignerModuleHook {
    private static final LoggerEx log = LoggerEx.newBuilder().build("Bijc3dCharts");
    private static final boolean isDebug = Bijc3dChartsComponents.isDebug;

    private DesignerContext context;
    private DesignerComponentRegistry registry;

    static {
        BundleUtil.get().addBundle("bijc3dcharts", Bijc3dChartsDesignerHook.class.getClassLoader(), "bijc3dcharts");
    }

    public Bijc3dChartsDesignerHook() {
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
        if (isDebug) log.info(Bijc3dCharts.COMPONENT_ID);
        registry.registerComponent(Bijc3dCharts.DESCRIPTOR);
    }


    @Override
    public void shutdown() {
        removeComponents();
    }

    private void removeComponents() {
        registry.removeComponent(Bijc3dCharts.COMPONENT_ID);
    }
}
