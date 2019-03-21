//
//  AIRMapTileOverlay.m
//  AirMaps
//
//  Created by Petri Louhelainen on 21/03/2019.
//  Copyright © 2019 Christopher. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "AIRMapTileOverlay.h"

@interface AIRMapTileOverlay ()

-(CLLocation *)calculateLatLongFrom:(long)x y:(long)y andZoom:(long)zoom;

@end

@implementation AIRMapTileOverlay

-(id)initWithURLTemplate:(NSString *)URLTemplate {
    self = [super initWithURLTemplate:nil];
    if (self == nil) { self = nil; return nil; }
    
    self.urlTemplate = URLTemplate;
    
    return self;
};


- (NSURL *)URLForTilePath:(MKTileOverlayPath)path {
    CLLocation *a = [self calculateLatLongFrom:(path.x + 1l) y:(path.y + 1l) andZoom:path.z];
    CLLocation *b = [self calculateLatLongFrom:path.x y:path.y andZoom:path.z];
    
    NSString *minLon = [self.urlTemplate stringByReplacingOccurrencesOfString:@"{minLon}" withString:[[NSNumber numberWithDouble:a.coordinate.longitude] stringValue]];
    NSString *minLat = [minLon stringByReplacingOccurrencesOfString:@"{minLat}" withString:[[NSNumber numberWithDouble:b.coordinate.latitude] stringValue]];
    NSString *maxLon = [minLat stringByReplacingOccurrencesOfString:@"{maxLon}" withString:[[NSNumber numberWithDouble:b.coordinate.longitude] stringValue]];
    NSString *url = [maxLon stringByReplacingOccurrencesOfString:@"{maxLat}" withString:[[NSNumber numberWithDouble:a.coordinate.latitude] stringValue]];
 
    return [NSURL URLWithString:url];
};

-(CLLocation *) calculateLatLongFrom:(long)x y:(long)y andZoom:(long)zoom {
    double n = pow(2, zoom);
    double lon_deg = x / n * 360 - 180;
    double lat_rad = atan(sinh(M_PI * (1 - 2 * y / n)));
    double lat_deg = lat_rad * 180 / M_PI;
    
    return [[CLLocation alloc] initWithLatitude:lat_deg longitude:lon_deg];
};

@end
