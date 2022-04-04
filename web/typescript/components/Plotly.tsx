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
import { LegendClickEvent, PlotDatum, PlotMouseEvent, PlotSelectionEvent, SliderChangeEvent, SliderEndEvent, SliderStartEvent } from 'plotly.js';

// import * as d3 from 'd3';

// the 'key' or 'id' for this component type.  Component must be registered with this EXACT key in the Java side as well
// as on the client side.  In the client, this is done in the index file where we import and register through the
// ComponentRegistry provided by the perspective-client API.
export const COMPONENT_TYPE = "bijc.display.plotly";

interface PlotlyCompProps {
    data: any;
    layout: any;
    config: any;
    events: PlotlyEvents;
}

interface PlotlyEvents {
    disabledOnLegendClick: boolean;
    disabledOnLegendDoubleClick: boolean;
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
            let newDatum = {} as any;

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

    sanitiseMouseEvent = (mouseEvent: MouseEvent) => {
        let newMouseEvent = {} as any;

        newMouseEvent.altKey = mouseEvent.altKey;
        newMouseEvent.ctrlKey = mouseEvent.ctrlKey;
        newMouseEvent.shiftKey = mouseEvent.shiftKey;
        newMouseEvent.metaKey = mouseEvent.metaKey;

        return newMouseEvent;
    }

    handleOnButtonClicked = (event: Readonly<Plotly.ButtonClickEvent>) => {
        console.log(event);
    }

    handleOnClick = (event: Readonly<PlotMouseEvent>) => {
        this.props.componentEvents.fireComponentEvent("onClick", { mouseEvent: this.sanitiseMouseEvent(event.event), points: this.sanitisePoints(event.points) });
    }

    handleOnClickAnotation = (event: Readonly<Plotly.ClickAnnotationEvent>) => {
        console.log("onClickAnnotation");
        console.log(event);
    }

    handleOnHover = (event: Readonly<PlotMouseEvent>) => {
        this.props.componentEvents.fireComponentEvent("onHover", { points: this.sanitisePoints(event.points) });
    }

    handleOnUnhover = (event: Readonly<PlotMouseEvent>) => {
        this.props.componentEvents.fireComponentEvent("onUnHover", { points: this.sanitisePoints(event.points) });
    }

    handleOnLegendClick = (event: Readonly<LegendClickEvent>) => {
        const curveNumber = event.curveNumber;
        const curveData = event.data[event.curveNumber];

        this.props.componentEvents.fireComponentEvent("onLegendClick", { mouseEvent: this.sanitiseMouseEvent(event.event), curveNumber: curveNumber, curveData: curveData });

        if (this.props.props.events.disabledOnLegendClick) {
            return false;
        } else {
            return true;
        }
    }
    handleOnLegendDoubleClick = (event: Readonly<LegendClickEvent>) => {
        const curveNumber = event.curveNumber;
        const curveData = event.data[event.curveNumber];

        this.props.componentEvents.fireComponentEvent("onLegendClick", { mouseEvent: this.sanitiseMouseEvent(event.event), curveNumber: curveNumber, curveData: curveData });

        if (this.props.props.events.disabledOnLegendDoubleClick) {
            return false;
        } else {
            return true;
        }
    }

    handleOnSelected = (event: Readonly<PlotSelectionEvent>) => {
        console.log("onSelected");
        console.log(event);
    }
    handleOnSelecting = (event: Readonly<PlotSelectionEvent>) => {
        console.log("onSelecting");
        console.log(event);
    }
    handleOnSliderChange = (event: Readonly<SliderChangeEvent>) => {
        console.log("onSliderChange");
        console.log(event);
    }
    handleOnSliderEnd = (event: Readonly<SliderEndEvent>) => {
        console.log("onSliderEnd");
        console.log(event);
    }
    handleOnSliderStart = (event: Readonly<SliderStartEvent>) => {
        console.log("onSliderStart");
        console.log(event);
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
                    onHover={this.handleOnHover}
                    onUnhover={this.handleOnUnhover}

                    onButtonClicked={this.handleOnButtonClicked}
                    onClickAnnotation={this.handleOnClickAnotation}

                    onLegendClick={this.handleOnLegendClick}
                    onLegendDoubleClick={this.handleOnLegendDoubleClick}

                    onSelected={this.handleOnSelected}
                    onSelecting={this.handleOnSelecting}

                    onSliderChange={this.handleOnSliderChange}
                    onSliderStart={this.handleOnSliderStart}
                    onSliderEnd={this.handleOnSliderEnd}
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
            config: tree.read("config"),
            events: tree.read("events")
        };
    }
}
