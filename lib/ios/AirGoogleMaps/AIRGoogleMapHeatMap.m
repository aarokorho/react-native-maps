//
//  AirGoogleMapHeatMap.m
//  Created by Petri Louhelainen on 02/10/2017.
//

#import "AIRGoogleMapHeatMap.h"
#import "AIRMapCoordinate.h"
#import "GMUWeightedLatLng.h"

@implementation AIRGoogleMapHeatMap

- (void)setZIndex:(int)zIndex
{
  _zIndex = zIndex;
  _tileLayer.zIndex = zIndex;
}

- (void)setOpacity:(float)opacity
{
  _opacity = opacity;
  _tileLayer.opacity = opacity;
}

- (void)setRadius:(int)radius
{
  _radius = radius;
  _tileLayer.radius = radius;
}

- (void)setCoordinates:(NSArray<AIRMapCoordinate *> *)coordinates
{
  _coordinates = coordinates;
  
  NSMutableArray<GMUWeightedLatLng *> * data = [NSMutableArray arrayWithCapacity:coordinates.count];
  for(int i = 0; i < coordinates.count; i++)
  {
    [data addObject:[[GMUWeightedLatLng alloc] initWithCoordinate:coordinates[i].coordinate intensity:1.0]];
  }
  
  if (_tileLayer == NULL) {
    _tileLayer = [[GMUHeatmapTileLayer alloc] init];
    _tileLayer.radius = _radius;
    _tileLayer.opacity = _opacity;
    _tileLayer.weightedData = data;
  } else {
    NSLog(@"Clearing tile data");
    _tileLayer.weightedData = data;
    [_tileLayer clearTileCache];
  }
}

@end
