//
//  KitchenClass.swift
//  NeighborCater
//
//  Created by Avinash Jain on 9/17/16.
//  Copyright © 2016 Avinash Jain. All rights reserved.
//

import Foundation
import Firebase

class Kitchen: NSObject {
    var kitchenName: String
    var kitchenAddress :String
    var foodName: String
    var foodDescription: String
    var latitude: Double
    var longitude: Double
    var user: String
    var distance: Double
    var price: String
    let ref: FIRDatabaseReference?
    
    init( kitchenName: String, kitchenAddress: String, foodName: String, foodDescription:String, latitude: Double, longitude: Double, user: String, price: String, ref: FIRDatabaseReference) {
        self.kitchenName = kitchenName
        self.kitchenAddress = kitchenAddress
        self.foodName = foodName
        self.foodDescription = foodDescription
        self.latitude = latitude
        self.longitude = longitude
        self.user = user
        self.price = price
        self.distance = 0.0
        self.ref = nil
    }
    
    init(snapshot: FIRDataSnapshot) {
        kitchenName = snapshot.value!["kitchenName"] as! String
        kitchenAddress = snapshot.value!["address"] as! String
        foodName = snapshot.value!["foodName"] as! String
        foodDescription = snapshot.value!["foodDescription"] as! String
        latitude = snapshot.value!["latitude"] as! Double
        longitude = snapshot.value!["longitude"] as! Double
        user = snapshot.value!["user"] as! String
        price = snapshot.value!["price"] as! String
        ref = snapshot.ref
        distance = 0.0
    }
    
    func addDistance(distance : Double) {
        self.distance = distance
    }
    
    convenience override init() {
        self.init()    }
}
