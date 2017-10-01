import PropTypes from 'prop-types';
import React from 'react';

import {
  ViewPropTypes,
} from 'react-native';

import decorateMapComponent, {
  USES_DEFAULT_IMPLEMENTATION,
  SUPPORTED,
} from './decorateMapComponent';

const propTypes = {
  ...ViewPropTypes,

  /**
   * An array of coordinates with optional intensity to describe the heatmap
   */
  coordinates: PropTypes.arrayOf(PropTypes.shape({
    /**
     * Latitude/Longitude coordinates
     */
    latitude: PropTypes.number.isRequired,
    longitude: PropTypes.number.isRequired,
    intensity: PropTypes.number,
  })),

  /**
   * The order in which this tile overlay is drawn with respect to other overlays. An overlay
   * with a larger z-index is drawn over overlays with smaller z-indices. The order of overlays
   * with the same z-index is arbitrary. The default zIndex is -1.
   *
   * @platform android
   */
  zIndex: PropTypes.number,

  /**
   * The radius for which a single heatmap point affects.  Must be between 10 and 50.
   */
  radius: PropTypes.number,

  /**
   * Opacity for the heatmap tile layer
   */
  opacity: PropTypes.number,
};

class MapHeatMap extends React.Component {
  render() {
    const AIRMapHeatMap = this.getAirComponent();
    return (
      <AIRMapHeatMap
        {...this.props}
      />
    );
  }
}

MapHeatMap.propTypes = propTypes;

module.exports = decorateMapComponent(MapHeatMap, {
  componentType: 'HeatMap',
  providers: {
    google: {
      ios: SUPPORTED,
      android: USES_DEFAULT_IMPLEMENTATION,
    },
  },
});
