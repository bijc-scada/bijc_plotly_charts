import { ComponentMeta, ComponentRegistry } from '@inductiveautomation/perspective-client';
import { Plotly, PlotlyMeta } from './components/Plotly';

// export so the components are referencable, e.g. `BijcCalComponents['Image']
export { Plotly as BijcPlotly };

import '../scss/main';

// as new components are implemented, import them, and add their meta to this array
const components: Array<ComponentMeta> = [
    new PlotlyMeta()
];

// iterate through our components, registering each one with the registry.  Don't forget to register on the Java side too!
components.forEach((c: ComponentMeta) => ComponentRegistry.register(c));
