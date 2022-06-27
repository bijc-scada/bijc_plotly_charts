package uk.co.bijc.gateway;

import java.util.Optional;

import com.inductiveautomation.ignition.common.licensing.LicenseState;
import com.inductiveautomation.ignition.common.util.LoggerEx;
import com.inductiveautomation.ignition.gateway.model.AbstractGatewayModuleHook;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import com.inductiveautomation.perspective.common.api.ComponentRegistry;
import com.inductiveautomation.perspective.gateway.api.PerspectiveContext;
import uk.co.bijc.common.Bijc3dChartsComponents;
import uk.co.bijc.common.component.Bijc3dCharts;

public class Bijc3dChartsGatewayHook extends AbstractGatewayModuleHook {

    private static final LoggerEx log = LoggerEx.newBuilder().build("bijc.gateway.Bijc3dChartsGateway");
    private static final boolean isDebug = Bijc3dChartsComponents.isDebug;

    private GatewayContext gatewayContext;
    private PerspectiveContext perspectiveContext;
    private ComponentRegistry componentRegistry;

    @Override
    public void setup(GatewayContext context) {
        this.gatewayContext = context;
        if (isDebug) log.info("Setting up BIJC Display Size module.");
    }

    @Override
    public void startup(LicenseState activationState) {
        if (isDebug) log.info("Starting up BIJC Display Size GatewayHook!");

        this.perspectiveContext = PerspectiveContext.get(this.gatewayContext);
        this.componentRegistry = this.perspectiveContext.getComponentRegistry();

        if (this.componentRegistry != null) {
            if (isDebug) log.info("Registering Bijc Display Size.");
            if (isDebug) log.info(Bijc3dCharts.COMPONENT_ID);
            this.componentRegistry.registerComponent(Bijc3dCharts.DESCRIPTOR);
        } else {
            if (isDebug) log.error("Reference to component registry not found, BIJC Display Size will fail to function!");
        }

    }

    @Override
    public void shutdown() {
        if (isDebug) log.info("Shutting down Bijc3dCharts module and removing registered components.");
        if (this.componentRegistry != null) {
            this.componentRegistry.removeComponent(Bijc3dCharts.COMPONENT_ID);
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
        return Optional.of(Bijc3dChartsComponents.URL_ALIAS);
    }

    @Override
    public boolean isFreeModule() {
        return true;
    }
}
