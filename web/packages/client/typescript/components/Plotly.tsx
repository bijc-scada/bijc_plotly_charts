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
import { ClickAnnotationEvent, LegendClickEvent, PlotDatum, PlotMouseEvent, PlotSelectionEvent } from 'plotly.js';

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
    disableOnLegendClickDefault: boolean;
    disableOnLegendDoubleClickDefault: boolean;
}

interface PlotlyCompState {
    data: any;
    layout: any;
    config: any;
}

interface PlotlyMouseEvent {
    altKey: boolean;
    ctrlKey: boolean;
    shiftKey: boolean;
    metaKey: boolean;
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

    sanitiseMouseEvent = (mouseEvent: MouseEvent): PlotlyMouseEvent => {
        return {
            altKey: mouseEvent.altKey,
            ctrlKey: mouseEvent.ctrlKey,
            shiftKey: mouseEvent.shiftKey,
            metaKey: mouseEvent.metaKey
        } as PlotlyMouseEvent;
    }

    handleOnClick = (event: Readonly<PlotMouseEvent>) => {
        let mouseEvent = {} as PlotlyMouseEvent;

        if (event.event) {
            mouseEvent = this.sanitiseMouseEvent(event.event);
        }

        this.props.componentEvents.fireComponentEvent("onClick", { mouseEvent: mouseEvent, points: this.sanitisePoints(event.points) });
    }

    handleOnDoubleClick = () => {
        this.props.componentEvents.fireComponentEvent("onDoubleClick", {});
    }

    handleOnClickAnotation = (event: Readonly<ClickAnnotationEvent>) => {
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

        if (this.props.props.events.disableOnLegendClickDefault) {
            return false;
        } else {
            return true;
        }
    }
    handleOnLegendDoubleClick = (event: Readonly<LegendClickEvent>) => {
        const curveNumber = event.curveNumber;
        const curveData = event.data[event.curveNumber];

        this.props.componentEvents.fireComponentEvent("onLegendDoubleClick", { mouseEvent: this.sanitiseMouseEvent(event.event), curveNumber: curveNumber, curveData: curveData });

        if (this.props.props.events.disableOnLegendDoubleClickDefault) {
            return false;
        } else {
            return true;
        }
    }

    handleOnSelected = (event: Readonly<PlotSelectionEvent>) => {
        let eventProps = {
            points: this.sanitisePoints(event.points),
            range: {} as any,
            lassoPoints: {} as any
        };

        if (event.range) {
            eventProps.range = event.range;
        }

        if (event.lassoPoints) {
            eventProps.lassoPoints = event.lassoPoints;
        }

        this.props.componentEvents.fireComponentEvent("onSelected", eventProps);
    }
    handleOnSelecting = (event: Readonly<PlotSelectionEvent>) => {
        let eventProps = {
            points: this.sanitisePoints(event.points),
            range: {} as any,
            lassoPoints: {} as any
        };

        if (event.range) {
            eventProps.range = event.range;
        }

        if (event.lassoPoints) {
            eventProps.lassoPoints = event.lassoPoints;
        }

        this.props.componentEvents.fireComponentEvent("onSelecting", eventProps);
    }

    handleOnButtonClicked = (event: any) => {
        let eventProp = {
            active: event.active,
            button: event.button._input
        };

        this.props.componentEvents.fireComponentEvent("onButtonClicked", eventProp);
    }
    handleOnSliderChange = (event: any) => {
        const eventProps = {
            previousActive: event.previousActive,
            step: event.step._input
        };

        this.props.componentEvents.fireComponentEvent("onSliderChange", eventProps);
    }
    handleOnSliderEnd = (event: any) => {
        const eventProps = {
            step: event.step._input
        };

        this.props.componentEvents.fireComponentEvent("onSliderEnd", eventProps);
    }
    handleOnSliderStart = () => {
        this.props.componentEvents.fireComponentEvent("onSliderStart", {});
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
                    onDoubleClick={this.handleOnDoubleClick}
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
