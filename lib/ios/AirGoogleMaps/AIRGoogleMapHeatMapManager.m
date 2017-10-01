//
//  AirGoogleMapHeatMapManager.m
//  kamuapp
//
//  Created by Petri Louhelainen on 02/10/2017.
//  Copyright © 2017 Facebook. All rights reserved.
//

#import "AIRGoogleMapHeatMapManager.h"
#import "AIRGoogleMapHeatMap.h"

@interface AIRGoogleMapHeatMapManager()

@end

@implementation AIRGoogleMapHeatMapManager

RCT_EXPORT_MODULE()

- (UIView *)view
{
  AIRGoogleMapHeatMap *tileLayer = [AIRGoogleMapHeatMap new];
  return tileLayer;
}

RCT_EXPORT_VIEW_PROPERTY(coordinates, AIRMapCoordinateArray)
RCT_EXPORT_VIEW_PROPERTY(zIndex, int)
RCT_EXPORT_VIEW_PROPERTY(opacity, float)
RCT_EXPORT_VIEW_PROPERTY(radius, int)

@end

