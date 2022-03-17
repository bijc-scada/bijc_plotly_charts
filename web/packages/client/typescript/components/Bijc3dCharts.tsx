import * as React from 'react';
import {
    Component,
    ComponentMeta,
    ComponentProps,
    PComponent,
    PropertyTree,
    SizeObject
} from '@inductiveautomation/perspective-client';
import { observer } from 'mobx-react';

import Plot from 'react-plotly.js';

// the 'key' or 'id' for this component type.  Component must be registered with this EXACT key in the Java side as well
// as on the client side.  In the client, this is done in the index file where we import and register through the
// ComponentRegistry provided by the perspective-client API.
export const COMPONENT_TYPE = "bijc.display.3dcharts";

interface Bijc3dChartsBoxProps {

}

@observer
export class Bijc3dCharts extends Component<ComponentProps<Bijc3dChartsBoxProps>, any> {
    // private webcamRef = React.createRef<any>();

    render() {
        const { emit } = this.props;

        return (
            <div {...emit()}>
                <Plot
                    data={[{ x: [1, 2, 3], y: [2, 6, 3], type: 'scatter', mode: 'lines+markers', marker: { color: 'red' } }, { type: 'bar', x: [1, 2, 3], y: [2, 5, 3] }]}
                    layout={{ width: 320, height: 240, title: 'A Fancy Plot' }}
                />
            </div>
        );
    }
}

// this is the actual thing that gets registered with the component registry
export class Bijc3dChartsMeta implements ComponentMeta {

    getComponentType(): string {
        return COMPONENT_TYPE;
    }

    getViewComponent(): PComponent {
        return Bijc3dCharts;
    }

    getDefaultSize(): SizeObject {
        return ({
            width: 600,
            height: 480
        });
    }

    getPropsReducer(tree: PropertyTree): Bijc3dChartsBoxProps {
        return {
            enable: tree.readBoolean("enable", true),
            devices: tree.readArray("devices", []),
            imageSmoothing: tree.readBoolean("imageSmoothing", false),
            mirrored: tree.readBoolean("mirrored", true),
            audio: tree.readBoolean("audio", true),
            audioConstraints: tree.read("audioConstraints", undefined),
            videoConstraints: tree.read("videoConstraints", undefined),
            enableImageCapture: tree.readBoolean("enableImageCapture", false),
            imageCapture: tree.read("imageCapture", false),
            writeDevices: (dList: Array<string>) => tree.write("devices", dList)
        };
    }
}
