package uk.co.bijc.gateway;

import java.util.Optional;

import com.inductiveautomation.ignition.common.licensing.LicenseState;
import com.inductiveautomation.ignition.common.util.LoggerEx;
import com.inductiveautomation.ignition.gateway.model.AbstractGatewayModuleHook;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import com.inductiveautomation.perspective.common.api.ComponentRegistry;
import com.inductiveautomation.perspective.gateway.api.PerspectiveContext;
import uk.co.bijc.common.BijcPlotlyComponents;
import uk.co.bijc.common.component.BijcPlotly;

public class BijcPlotlyGatewayHook extends AbstractGatewayModuleHook {

    private static final LoggerEx log = LoggerEx.newBuilder().build("bijc.gateway.BijcPlotlyGateway");
    private static final boolean isDebug = BijcPlotlyComponents.isDebug;

    private GatewayContext gatewayContext;
    private PerspectiveContext perspectiveContext;
    private ComponentRegistry componentRegistry;

    @Override
    public void setup(GatewayContext context) {
        this.gatewayContext = context;
        if (isDebug) log.info("Setting up BIJC Plotly module.");
    }

    @Override
    public void startup(LicenseState activationState) {
        if (isDebug) log.info("Starting up BIJC Plotly GatewayHook!");

        this.perspectiveContext = PerspectiveContext.get(this.gatewayContext);
        this.componentRegistry = this.perspectiveContext.getComponentRegistry();

        if (this.componentRegistry != null) {
            if (isDebug) log.info("Registering Bijc Plotly.");
            if (isDebug) log.info(BijcPlotly.COMPONENT_ID);
            this.componentRegistry.registerComponent(BijcPlotly.DESCRIPTOR);
        } else {
            if (isDebug) log.error("Reference to component registry not found, BIJC Plotly will fail to function!");
        }

    }

    @Override
    public void shutdown() {
        if (isDebug) log.info("Shutting down BijcPlotly module and removing registered components.");
        if (this.componentRegistry != null) {
            this.componentRegistry.removeComponent(BijcPlotly.COMPONENT_ID);
        } else {
            if (isDebug) log.warn("Component registry was null, could not unregister Bijc Components.");
        }

    }

    @Override
    public Optional<String> getMountedResourceFolder() {
        return Optional.of("mounted");
    }

    // Lets us use the route http://<gateway>/res/bijccomponents/*
    @Override
    public Optional<String> getMountPathAlias() {
        return Optional.of(BijcPlotlyComponents.URL_ALIAS);
    }

    @Override
    public boolean isFreeModule() {
        return true;
    }
}
