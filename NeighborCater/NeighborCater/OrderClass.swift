//
//  OrderClass.swift
//  NeighborCater
//
//  Created by Avinash Jain on 9/18/16.
//  Copyright © 2016 Avinash Jain. All rights reserved.
//

import Foundation
import UIKit
import Firebase

class Order : NSObject {
    
    var userName : String
    var userEmail : String
    var userPhoneNumber : String
    var foodName : String
    
    init (snapshot:FIRDataSnapshot) {
        userName = snapshot.value!["name"] as! String
        userEmail = snapshot.value!["email"] as! String
        userPhoneNumber = snapshot.value!["number"] as! String
        foodName = snapshot.value!["foodName"] as! String
    }
}