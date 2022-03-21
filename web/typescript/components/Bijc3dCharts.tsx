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

// import * as d3 from 'd3';

// the 'key' or 'id' for this component type.  Component must be registered with this EXACT key in the Java side as well
// as on the client side.  In the client, this is done in the index file where we import and register through the
// ComponentRegistry provided by the perspective-client API.
export const COMPONENT_TYPE = "bijc.display.3dcharts";

interface Bijc3dChartsBoxProps {
    data: any;
    layout: any;
    config: any;
}

interface Bijc3dChartsState {
    data: any;
    layout: any;
    config: any;
}

@observer
export class Bijc3dCharts extends Component<ComponentProps<Bijc3dChartsBoxProps>, Bijc3dChartsState> {
    // private webcamRef = React.createRef<any>();

    /*
    handleCsv = (err: any, rows: any) => {
        console.log(err);

        function unpack(rows: any, key: any) {
            let rowArr: Array<number> = [];

            for (let index = 0; index < rows.length; index++) {
                rowArr.push(rows[index][key]);
            }

            return rowArr;
        }

        let z_data = [];
        for (let i = 0; i < 24; i++) {
            z_data.push(unpack(rows, i));
        }

        this.setState({
            data: [{
                z: z_data,
                type: 'surface'
            }]
        });

        this.setState({
            layout: {
                title: 'Mt Bruno Elevation',
                margin: {
                    l: 65,
                    r: 50,
                    b: 65,
                    t: 90
                }
            }
        });
    }
    */

    componentDidMount = () => {
        // d3.csv('https://raw.githubusercontent.com/plotly/datasets/master/api_docs/mt_bruno_elevation.csv', this.handleCsv);

        const testData = [
            [0, 5, 12, 38, 20, 50],
            [5, 10, 27, 21, 49, 63],
            [9, 22, 23, 47, 70, 70],
            [15, 24, 46, 68, 79, 78],
            [25, 45, 59, 73, 75, 81],
            [43, 60, 67, 69, 82, 87]
        ];

        this.setState({
            data: [{
                z: testData,
                type: 'surface'
            }],
            layout: {
                title: 'Mt Bruno Elevation'
            },
            config: {
                displaylogo: false,
                responsive: true,
                autosizable: true
            }
        });
    }

    render() {
        const { props, emit } = this.props;

        let plot = (<div />);

        if (this.state != null) {
            plot = (<Plot
                data={props.data}
                layout={props.layout}
                config={props.config}
                style={{width: "100%", height: "100%"}}
            />);
        }

        return (
            <div {...emit()}>
                {plot}
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
            data: tree.read("data"),
            layout: tree.read("layout"),
            config: tree.read("config")
        };
    }
}
