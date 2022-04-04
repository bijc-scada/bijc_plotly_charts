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
import { PlotDatum, PlotMouseEvent } from 'plotly.js';

// import * as d3 from 'd3';

// the 'key' or 'id' for this component type.  Component must be registered with this EXACT key in the Java side as well
// as on the client side.  In the client, this is done in the index file where we import and register through the
// ComponentRegistry provided by the perspective-client API.
export const COMPONENT_TYPE = "bijc.display.plotly";

interface PlotlyCompProps {
    data: any;
    layout: any;
    config: any;
}

interface PlotlyCompState {
    data: any;
    layout: any;
    config: any;
}

@observer
export class Plotly extends Component<ComponentProps<PlotlyCompProps>, PlotlyCompState> {
    sanitisePoints = (points: Array<PlotDatum>) => {
        let newPoints = [] as Array<object>;

        points.forEach((point: PlotDatum) => {
            let newDatum = {};

            for (let key in point) {
                if (key == "xaxis" || key == "yaxis" || key == "data" || key == "fullData") {
                    continue;
                }
                newDatum[key] = point[key];
            }

            newPoints.push(newDatum);
        });

        return newPoints;
    }

    // private webcamRef = React.createRef<any>();
    handleOnClick = (event: Readonly<PlotMouseEvent>) => {
        this.props.componentEvents.fireComponentEvent("onClick", { points: this.sanitisePoints(event.points) });
    }

    render() {
        const { props, emit } = this.props;

        let layoutProp = props.layout;

        // Needed for fitting into containers correctly
        layoutProp.autosize = true;

        // Set background to transparent if they aren't defined
        if (layoutProp.paper_bgcolor == undefined) {
            layoutProp.paper_bgcolor = '#00000000';
        }

        if (layoutProp.plot_bgcolor == undefined) {
            layoutProp.plot_bgcolor = '#00000000';
        }

        return (
            <div {...emit()}>
                <Plot
                    data={props.data}
                    layout={props.layout}
                    config={props.config}
                    style={{ width: "100%", height: "100%" }}
                    useResizeHandler={true}

                    onClick={this.handleOnClick}
                />
            </div>
        );
    }
}

// this is the actual thing that gets registered with the component registry
export class PlotlyMeta implements ComponentMeta {

    getComponentType(): string {
        return COMPONENT_TYPE;
    }

    getViewComponent(): PComponent {
        return Plotly;
    }

    getDefaultSize(): SizeObject {
        return ({
            width: 600,
            height: 480
        });
    }

    getPropsReducer(tree: PropertyTree): PlotlyCompProps {
        return {
            data: tree.read("data"),
            layout: tree.read("layout"),
            config: tree.read("config")
        };
    }
}
