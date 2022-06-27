import {ComponentMeta, ComponentRegistry} from '@inductiveautomation/perspective-client';
import { Bijc3dCharts, Bijc3dChartsMeta } from './components/Bijc3dCharts';

// export so the components are referencable, e.g. `BijcComponents['Image']
export {Bijc3dCharts};

import '../scss/main';

// as new components are implemented, import them, and add their meta to this array
const components: Array<ComponentMeta> = [
    new Bijc3dChartsMeta()
];

// iterate through our components, registering each one with the registry.  Don't forget to register on the Java side too!
components.forEach((c: ComponentMeta) => ComponentRegistry.register(c) );
