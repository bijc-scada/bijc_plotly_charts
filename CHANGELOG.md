# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/)

## [1.1.2] - 2022-04-05

### Added

- The following events are now available:
  - onDoubleClick
  - onHover
  - onLegendClick - the default behaviour can be disabled via a property
  - onLegendDoubleClick - the default behaviour can be disabled via a property
  - onSelected
  - onSelecting
  - onUnHover
- 

## [1.1.1] - 2022-04-04

### Added

- Large number of variants for creating default charts
- Icon on the pallete entry
- onClick event added to component. Sanitises the data points before returning them to avoid cyclic data
- Property tree now has definitions for a number of properties to help with setting up the chart

## [1.1.0] - 2022-03-24

### Changed

- Removed BIJC references from non relevant areas

## [1.0.1] - 2022-03-22

### Added

- Extra entries for some variants

### Changed

- Renamed module from 3dCharts to Plotly as it has expanded beyond the original scope

## [1.0.0] - 2022-03-21

### Added

- Implemented the plotly library into the component