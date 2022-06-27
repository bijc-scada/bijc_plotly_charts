# Plotly Component Module

[Module Download Link](http://modules.bijc.co.uk/wp-content/uploads/2022/04/BIJC_plotly_Component_v1.1.2.modl)

An implementation of the plotly.js library as a perspective component

A single component provides support for all plotly chart types through a flexible set of properties. Seventeen variants are included as examples of creating some of the charts.

[Plotly.js Documentation](https://plotly.com/javascript/)

[Plotly.js Figure Reference Documentation](https://plotly.com/javascript/reference/)

## Property Model

The component has four properties:

| Property | Description                                                         |
| -------- | ------------------------------------------------------------------- |
| data     | Array of JSON objects describing the traces ti display on the chart |
| layout   | Attributes for the chart                                            |
| config   | Configutation for the chart                                         |
| events   | Provides the ability to turn off default behaviour for some events  |

### Data

Each object in the `data` array describes a trace to be displayed on the chart. The properties available for the trace depend on what the type property is set to. Traces are categorised on the chart by trace type.

The following is an example of the data property used to create a bar chart with two groups of data:

```
[
  {
    "x": ["giraffes", "orangutans", "monkeys"],
    "y": [20, 14, 23],
    "name": "SF Zoo",
    "type": "bar"
  },
  {
    "x": ["giraffes", "orangutans", "monkeys"],
    "y": [12, 18, 29],
    "name": "LA Zoo",
    "type": "bar"
  }
]
```

### Layout

The layout property defines the attributes of the chart including things like the title, axes and annotations.

The following is an example of the layout property being used for a waterfall chart:

```
{
  "title": {
    "text": "Profit and loss statement 2018"
  },
  "xaxis": {
    "type": "category"
  },
  "yaxis": {
    "type": "linear"
  },
  "showlegend": true
}
```

### Config

The config object is used for the configuration of certain properties on the chart.

The following show some of the properties that can be set using the config property:

```
{
  "displayModeBar": true,
  "modeBarButtonsToRemove": ["toImage", "select2d"],
  "locale": "fr",
  "displaylogo": false
}
```

## Event Handlers

A number of events are provided by the module that allow you to respond to various events such as clicking on the trace or making a selection on a trace.

* onClick
* onDoubleClick
* onHover
* onUnHover
* onLegendClick
* onLegendDoubleClick
* onSelecting
* onSelected

The default behaviour for the `onLegendClick` and `onLegendDoubleClick` events can be turned off using the properties inside the event property of the component.
