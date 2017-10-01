//
//  AIRMapTileOverlay.h
//  AirMaps
//
//  Created by Petri Louhelainen on 21/03/2019.
//  Copyright © 2019 Christopher. All rights reserved.
//

#ifndef AIRMapTileOverlay_h
#define AIRMapTileOverlay_h

#import <Foundation/Foundation.h>

@import MapKit;

@interface AIRMapTileOverlay : MKTileOverlay

@property(nonatomic, strong) NSString *urlTemplate;

-(id)initWithURLTemplate:(NSString *)URLTemplate;

@end

#endif /* AIRMapTileOverlay_h */
